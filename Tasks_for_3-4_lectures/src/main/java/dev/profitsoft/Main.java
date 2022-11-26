package dev.profitsoft;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        FirstTask firstTask = new FirstTask("Tasks_for_3-4_lectures/src/main/java/dev/profitsoft/person.xml",
//                "Tasks_for_3-4_lectures/src/main/java/dev/profitsoft/",
//                "result1");
//        firstTask.run();

        SecondTask secondTask = new SecondTask("Tasks_for_3-4_lectures/src/main/java/dev/profitsoft/folter",
                "Tasks_for_3-4_lectures/src/main/java/dev/profitsoft/",
                "result");
        secondTask.run();
    }
}
