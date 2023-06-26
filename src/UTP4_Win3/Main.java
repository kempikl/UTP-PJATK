package UTP4_Win3;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://wiki.puzzlers.org/pub/wordlists/unixdict.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            Map<String, List<String>> anagramGroups = reader.lines()
                    .collect(Collectors.groupingBy(Main::sortedChars));

            List<List<String>> anagramsWithMaxCount = anagramGroups.entrySet().stream()
                    .filter(entry -> entry.getValue().size() > 1)
                    .sorted(Comparator.comparingInt((Map.Entry<String, List<String>> entry) -> entry.getValue().size()).reversed())
                    .collect(Collectors.groupingBy(entry -> entry.getValue().size(), LinkedHashMap::new, Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                    .values().stream().findFirst().orElse(Collections.emptyList());

            for (List<String> anagrams : anagramsWithMaxCount) {
                System.out.println(String.join(" ", anagrams));
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sortedChars(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}

