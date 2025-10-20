package org.example;
import java.util.*;

public class App {
    public static void main(String[] args) {
        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        analyzer.loadCheeses("cheese_data.csv");

        System.out.println("Loaded " + analyzer.getCheeses().size() + " cheeses.");
        System.out.println();

        Cheese highestFat = analyzer.findHighestFat();
        if (highestFat != null) {
            System.out.println("Cheese with the highest fat content: ");
            System.out.println(highestFat);
        }

        System.out.printf("\nAverage protein content: %.2f%%\n", analyzer.averageProtein());

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter a country to filter by: ");
        String country = scanner.nextLine();
        List<Cheese> filtered = analyzer.filterByCountry(country);

        System.out.println("\nCheeses from " + country + ":");
        for (Cheese c : filtered) {
            System.out.println(" - " + c);
        }

        scanner.close();

        System.out.println("Analysis complete.");
        analyzer.analyzeAndWriteResults("cheese_data.csv", "output.txt");
        System.out.println("Results written to output.txt");
    }
}