package dev.profitsoft.Task2.utility;

import dev.profitsoft.Task2.annotation.Property;
import dev.profitsoft.Task2.exception.CustomFileReaderException;
import dev.profitsoft.Task2.exception.CustomInvocationException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Objects;
import java.util.Properties;

public class SecondTask {

    private static final Properties properties = new Properties();
    public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";

    public static <T> T createClassFromProperties(Class<T> cls, Path propertiesPath) {

        if (Objects.isNull(cls) || Objects.isNull(propertiesPath)) {
            throw new IllegalArgumentException("Your provided wrong data!");
        }

        populateProperties(propertiesPath);

        T instance = createInstance(cls);

        return getFilledInstance(cls, instance);
    }

    private static <T> T getFilledInstance(Class<T> cls, T instance){
        Field[] declaredFields = cls.getDeclaredFields();

        for (Field field : declaredFields) {
            String fieldName = field.getName();
            String fieldSetterName = String.format("set%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
            Object parsedValue = getValue(field, fieldName);
            Method fieldSetter = null;
            try {
                fieldSetter = cls.getDeclaredMethod(fieldSetterName, field.getType());
                fieldSetter.invoke(instance, parsedValue);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new CustomInvocationException(e.getMessage());
            }
        }

        return instance;
    }

    private static Object getValue(Field field, String fieldName) {
        if (field.isAnnotationPresent(Property.class)) {
            Property annotation = field.getAnnotation(Property.class);
            return getParsedValue(field.getType(), (String) properties.get(annotation.name().isEmpty() ? fieldName : annotation.name()), annotation.format().isEmpty() ? DATE_FORMAT : annotation.format());
        } else {
            return getParsedValue(field.getType(), (String) properties.get(fieldName), DATE_FORMAT);
        }
    }

    private static <T> Object getParsedValue(Class<T> fieldType, String propertiesValue, String dateFormat) {

        Object parsedValue = null;

        if (fieldType.isAssignableFrom(String.class)) {
            parsedValue = propertiesValue;
        } else if (fieldType.isAssignableFrom(Integer.class) || fieldType.isAssignableFrom(int.class)) {
            parsedValue = Integer.parseInt(propertiesValue);
        } else if (fieldType.isAssignableFrom(Instant.class)) {
            try {
                parsedValue = new SimpleDateFormat(dateFormat).parse(propertiesValue).toInstant();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        return parsedValue;
    }

    private static <T> T createInstance(Class<T> classOfInstance) {
        try {
            return classOfInstance.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new CustomInvocationException(e.getMessage());
        }
    }

    private static void populateProperties(Path pathToPropertiesFile) {
        try (InputStream input = new FileInputStream(pathToPropertiesFile.toFile())) {
            properties.load(input);
        } catch (IOException e) {
            throw new CustomFileReaderException(e.getMessage());
        }
    }
}
