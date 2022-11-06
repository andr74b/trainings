package dev.profitsoft;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SecondTaskTest {
    private Map<String, Integer> expected;

    @BeforeEach
    void setUp() {
        expected = new LinkedHashMap<>();
        expected.put("#java", 4);
        expected.put("#world", 3);
        expected.put("#love", 2);
        expected.put("#fun", 1);
    }

    @Test
    void shouldReturnTop5UsedHashTags() {
        List<String> strings = new ArrayList<>();
        strings.add("Hello #java #world");
        strings.add("This is my new #java program");
        strings.add("This #java program makes me #fun ... really #fun");
        strings.add("And I #love #java #world");
        strings.add("#love will save the #world");

        assertEquals(expected, SecondTask.getTop5UsedHashTags(strings));
    }
}