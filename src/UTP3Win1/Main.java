package UTP3Win1;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/*<--
 *  niezbędne importy
 */
public class Main {
    public static void main(String[] args) {

        Function<String, List<String>> flines = fname -> {
            try {
                return Files.readAllLines(Paths.get(fname));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };

        Function<List<String>, String> join = lines -> String.join("", lines);

        Function<String, List<Integer>> collectInts = txt -> Arrays.stream(txt.split("\\D+"))
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Function<List<Integer>, Integer> sum = ints -> ints.stream().mapToInt(Integer::intValue).sum();

        /*<--
         *  definicja operacji w postaci lambda-wyrażeń:
         *  - flines - zwraca listę wierszy z pliku tekstowego
         *  - join - łączy napisy z listy (zwraca napis połączonych ze sobą elementów listy napisów)
         *  - collectInts - zwraca listę liczb całkowitych zawartych w napisie
         *  - sum - zwraca sumę elmentów listy liczb całkowitych
         */

        String fname = System.getProperty("user.home") + "/LamComFile.txt";
        InputConverter<String> fileConv = new InputConverter<>(fname);
        List<String> lines = fileConv.convertBy(flines);
        String text = fileConv.convertBy(flines, join);
        List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
        Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

        System.out.println(lines);
        System.out.println(text);
        System.out.println(ints);
        System.out.println(sumints);

        List<String> arglist = Arrays.asList(args);
        InputConverter<List<String>> slistConv = new InputConverter<>(arglist);
        sumints = slistConv.convertBy(join, collectInts, sum);
        System.out.println(sumints);

    }
}
