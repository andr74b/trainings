package dev.profitsoft;

import java.util.*;
import java.util.stream.Collectors;

public class SecondTask {
    public static Map<String, Integer> getTop5UsedHashTags(List<String> list) {

        Map<String, Integer> resultMap = new HashMap<>();

        for (String line : list) {
            String[] words = line.split(" ");

            List<String> hashTags = getHashTagsFromString(words);

            Map<String, Integer> localMap = new HashMap<>();
            for (String hashTag : hashTags) {
                localMap.put(hashTag, 1);
            }

            addLocalMapToResultMap(resultMap, localMap);
        }

        return resultMap.
                entrySet().
                stream().
                sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                limit(5).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private static void addLocalMapToResultMap(Map<String, Integer> resultMap, Map<String, Integer> localMap) {
        localMap.forEach((key, value) -> {
            if (resultMap.containsKey(key)) {
                resultMap.put(key, resultMap.get(key) + 1);
            } else {
                resultMap.put(key, 1);
            }
        });
    }

    private static List<String> getHashTagsFromString(String[] words) {
        List<String> hashTags = new ArrayList<>();

        for (String word : words) {
            if (word.startsWith("#")) {
                hashTags.add(word);
            }
        }

        return hashTags;
    }
}
