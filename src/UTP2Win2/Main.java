package UTP2Win2;


/*<-- niezbędne importy */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        // Lista destynacji: port_wylotu port_przylotu cena_EUR
        List<String> dest = Arrays.asList(
                "bleble bleble 2000",
                "WAW HAV 1200",
                "xxx yyy 789",
                "WAW DPS 2000",
                "WAW HKT 1000"
        );
        double ratePLNvsEUR = 4.30;
        List<String> result = dest.stream()
                .filter(e -> e.startsWith("WAW") && e.startsWith(" ", 3))
                .map(e -> {
                    String[] split = e.split(" ");
                    String destination = split[1];
                    int price = (int) (Integer.parseInt(split[2]) * ratePLNvsEUR);

                    return String.format("to %s - price in PLN:	%d", destination, price);
                })
                .collect(Collectors.toList());

        for (String r : result) System.out.println(r);
    }
}

