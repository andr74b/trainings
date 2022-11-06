package dev.profitsoft;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FirstTaskTest {

    @Test
    void shouldRemoveNegativeAndSortDescending() {
        int[] expected = new int[] {3, 2, 1, 0};

        int[] array = new int[] {-3, -2, -1, 0, 1, 2, 3};
        assertArrayEquals(expected, FirstTask.removeNegativeAndSortDescending(array));
    }
}