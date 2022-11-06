package dev.profitsoft;

import java.util.Arrays;
import java.util.Collections;

public class FirstTask {
    public static int[] removeNegativeAndSortDescending(int[] numbers) {
        return Arrays.stream(numbers).
                filter(number -> number >= 0).
                boxed().
                sorted(Collections.reverseOrder()).
                mapToInt(Integer::intValue).
                toArray();
    }
}
