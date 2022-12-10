package dev.profitsoft.Task2.utility;

import dev.profitsoft.Task2.exception.CustomFileReaderException;
import dev.profitsoft.Task2.exception.CustomInvocationException;
import dev.profitsoft.Task2.model.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class SecondTaskTest {

    private static final Path pathToProperties = Path.of("src/test/resources/props.properties");

    @Test
    public void shouldHaveNotAnnotatedTest() {
        NotAnnotatedClass notAnnotatedClass = SecondTask.createClassFromProperties(NotAnnotatedClass.class, pathToProperties);

        assertEquals("value1", notAnnotatedClass.getStringProperty());
        assertEquals(10, notAnnotatedClass.getNumberProperty());
        assertEquals(Instant.parse("2022-11-29T16:30:00Z"), notAnnotatedClass.getTimeProperty());
    }

    @Test
    public void shouldHaveAnnotatedTest() {
        AnnotatedClass annotatedClass = SecondTask.createClassFromProperties(AnnotatedClass.class, pathToProperties);

        assertEquals("value1", annotatedClass.getStringProperty());
        assertEquals(10, annotatedClass.getNumber());
        assertEquals(Instant.parse("2022-11-29T16:30:00Z"), annotatedClass.getTimeProperty());
    }

    @Test
    public void shouldHaveComplexPropertyTest() {
        ComplexClass complexClass = SecondTask.createClassFromProperties(ComplexClass.class, pathToProperties);

        assertEquals(10, complexClass.getProperty());
        assertEquals(Integer.valueOf(10), complexClass.getInteger());
        assertEquals("parse", complexClass.getString());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenWrongClass() {
        assertThrows(IllegalArgumentException.class, () -> SecondTask.createClassFromProperties(null, pathToProperties));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenWrongProperties() {
        assertThrows(IllegalArgumentException.class, () -> SecondTask.createClassFromProperties(PrivateConstructorClass.class, null));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenPathIsWrong() {
        assertThrows(CustomFileReaderException.class, () -> SecondTask.createClassFromProperties(PrivateConstructorClass.class, Path.of("ErrorPath")));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenNoSetter() {
        assertThrows(CustomInvocationException.class, () -> SecondTask.createClassFromProperties(NoSetterClass.class, pathToProperties));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenBadValue() {
        assertThrows(NumberFormatException.class, () -> SecondTask.createClassFromProperties(BadValueClass.class, pathToProperties));
    }
}