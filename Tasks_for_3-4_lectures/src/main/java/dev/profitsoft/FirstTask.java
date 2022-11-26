package dev.profitsoft;

import javax.annotation.processing.FilerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstTask {

    private static final String FILE_EXTENSION = ".xml";
    public static final String REGEX_NAME = "(\\Wname\\s?=\\s?\"[^\"]*\")";
    public static final String REGEX_SURNAME = "(\\Wsurname\\s?=\\s?\"[^\"]*\")";
    public static final Pattern patternName = Pattern.compile(REGEX_NAME);
    public static final Pattern patternSurname = Pattern.compile(REGEX_SURNAME);

    private String pathOfExistingFile;
    private String pathOfNewFile;
    private String nameOfNewFile;

    public FirstTask(String pathOfExistingFile, String pathOfNewFile, String nameOfNewFile) {
        this.pathOfExistingFile = pathOfExistingFile;
        this.pathOfNewFile = pathOfNewFile;
        this.nameOfNewFile = nameOfNewFile;
    }

    public void run() throws IOException {
        if (!createNewFile()) {
            throw new FilerException("File already exists!");
        }

        StringBuilder resultLine = new StringBuilder();

        try (FileInputStream fileInputStream = new FileInputStream(pathOfExistingFile);
            Scanner scanner = new Scanner(fileInputStream, StandardCharsets.UTF_8)) {

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                if (!currentLine.contains("/>") && scanner.hasNextLine()) {
                    resultLine.append(currentLine).append("\n");
                    continue;
                }

                currentLine = changeLine(resultLine + currentLine + "\n");
                appendLineToResult(currentLine);
                resultLine = new StringBuilder();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String changeLine(String line) {
        Matcher matcherName = patternName.matcher(line);
        Matcher matcherSurname = patternSurname.matcher(line);

        if (matcherName.find() && matcherSurname.find()) {
            String name = matcherName.group(0).trim().replaceAll(" ", "")
                    .replaceAll("\"", "")
                    .substring(5);
            String surname = matcherSurname.group(0).trim().replaceAll(" ", "")
                    .replaceAll("\"", "").substring(8);

            return line
                    .replaceAll(REGEX_NAME, " name = \"" + name + " " + surname + "\"")
                    .replaceAll(REGEX_SURNAME, "");
        }

        return line;
    }

    private boolean createNewFile() throws IOException {
        File file = new File(pathOfNewFile + nameOfNewFile + FILE_EXTENSION);
        return file.createNewFile();
    }

    private void appendLineToResult(String line) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(pathOfNewFile + nameOfNewFile + FILE_EXTENSION, true)) {
            fileOutputStream.write(line.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


