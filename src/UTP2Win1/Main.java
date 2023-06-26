package UTP2Win1;


import java.util.*;

public class Main {

    static List<String> getPricesInPLN(List<String> destinations, double xrate) {
        return ListCreator.collectFrom(destinations)
                .when(e -> e.startsWith("WAW") && e.startsWith(" ", 3))
                .mapEvery(e -> {
                    String[] split = e.split(" ");
                    String destination = split[1];
                    int price = (int) (Integer.parseInt(split[2]) * xrate);

                    return String.format("to %s - price in PLN:	%d", destination, price);
                });
    }

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
        List<String> result = getPricesInPLN(dest, ratePLNvsEUR);
        for (String r : result) System.out.println(r);
    }
}

