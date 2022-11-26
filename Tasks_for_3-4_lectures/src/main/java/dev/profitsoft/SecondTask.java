package dev.profitsoft;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SecondTask {

    public static final String FILE_EXTENSION = ".xml";

    private final String folderPath;
    private final String pathOfNewFile;
    private final String nameOfNewFile;
    private final Map<String, Double> resultMap;

    public SecondTask(String folderPath, String pathOfNewFile, String nameOfNewFile) {
        this.folderPath = folderPath;
        this.pathOfNewFile = pathOfNewFile;
        this.nameOfNewFile = nameOfNewFile;
        this.resultMap = new HashMap<>();
    }

    public void run() throws IOException {
        List<Violation> violations = getViolations(folderPath);

        for (var violation : violations) {
            if (resultMap.containsKey(violation.getType())) {
                resultMap.put(violation.getType(), resultMap.get(violation.getType()) + violation.getFineAmount());
            } else {
                resultMap.put(violation.getType(), violation.getFineAmount());
            }
        }

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.writeValue(new File(pathOfNewFile + nameOfNewFile + FILE_EXTENSION), sortMap());
    }

    private LinkedHashMap<String, Double> sortMap() {
        return resultMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


    private List<Violation> getViolations(String folderPath) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<Violation> violationList = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            List<Path> listOfFiles = paths.filter(Files::isRegularFile).collect(Collectors.toList());

            for (var file : listOfFiles) {
                violationList.addAll(objectMapper.readValue(file.toFile(), new TypeReference<>(){}));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return violationList;
    }
}
