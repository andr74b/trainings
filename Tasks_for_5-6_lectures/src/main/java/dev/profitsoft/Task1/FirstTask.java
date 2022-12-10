package dev.profitsoft.Task1;

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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FirstTask {

    public static final String FILE_EXTENSION = ".xml";

    private Map<String, Double> resultMap;
    private final ExecutorService executorsService;
    private final String folderPath;
    private final String pathOfNewFile;
    private final String nameOfNewFile;

    public FirstTask(int threadsAmount, String folderPath, String pathOfNewFile, String nameOfNewFile) {
        this.resultMap = new HashMap<>();
        this.executorsService = Executors.newFixedThreadPool(threadsAmount);
        this.folderPath = folderPath;
        this.pathOfNewFile = pathOfNewFile;
        this.nameOfNewFile = nameOfNewFile;
    }

    public void run() throws IOException {
        getViolations(folderPath);

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.writeValue(new File(pathOfNewFile + nameOfNewFile + FILE_EXTENSION), sortMap());
    }

    private Map<String, Double> populateMap(List<Violation> violations) {
        Map<String, Double> result = new HashMap<>();

        for (var violation : violations) {
            if (result.containsKey(violation.getType())) {
                result.put(violation.getType(), result.get(violation.getType()) + violation.getFineAmount());
            } else {
                result.put(violation.getType(), violation.getFineAmount());
            }
        }

        return result;
    }

    private void getViolations(String folderPath) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths
                    .filter(file -> !Files.isDirectory(file))
                    .parallel()
                    .forEach(file -> CompletableFuture.runAsync(() -> {
                        try {
                            mergeMap(populateMap(objectMapper.readValue(file.toFile(), new TypeReference<>(){})));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }, executorsService));

            executorsService.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LinkedHashMap<String, Double> sortMap() {
        return resultMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private void mergeMap(Map<String, Double> dataMap) {
        dataMap.forEach(
                ((violationType, fineAmount) -> resultMap.merge(
                        violationType, fineAmount, (oldValue, newValue) -> oldValue += newValue)));
    }
}
