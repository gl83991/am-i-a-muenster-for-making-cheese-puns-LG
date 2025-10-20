package org.example;

import java.util.*;
import java.io.*;

public class CheeseAnalyzer {
    private List<Cheese> cheeses = new ArrayList<>();

    public void loadCheeses(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 4) continue;
                String name = data[0].trim();
                String country = data[1].trim();
                double fatContent = 0;
                double proteinContent = 0;
                try {
                    fatContent = Double.parseDouble(data[2].trim());
                    proteinContent = Double.parseDouble(data[3].trim());
                } catch (Exception ignored) {}

                cheeses.add(new Cheese(name, country, fatContent, proteinContent));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in data.");
        }
    }

    public List<Cheese> getCheeses() {
        return cheeses;
    }

    public Cheese findHighestFat() {
        if (cheeses.isEmpty()) return null;
        Cheese highest = cheeses.get(0);
        for (Cheese c : cheeses) {
            if (c.getFatContent() > highest.getFatContent()) {
                highest = c;
            }
        }
        return highest;
    }

    public double averageProtein() {
        if (cheeses.isEmpty()) return 0.0;
        double total = 0.0;
        for (Cheese c : cheeses) {
            total += c.getProteinContent();
        }
        return total / cheeses.size();
    }

    public List<Cheese> filterByCountry(String country) {
        List<Cheese> filtered = new ArrayList<>();
        for (Cheese c : cheeses) {
            if (c.getCountry().equalsIgnoreCase(country)) {
                filtered.add(c);
            }
        }
        return filtered;
    }

    public void analyzeAndWriteResults(String inputFile, String outputFile) {
        int pasteurizedCount = 0;
        int rawCount = 0;
        int organicHighMoisture = 0;
        Map<String, Integer> milkTypeCount = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length < 6) continue;

                try {
                    String milkType = data[2].trim();
                    String milkTreatment = data[3].trim();
                    String organicStr = data[4].trim();
                    String moistureStr = data[5].trim();

                    if (milkTreatment.equalsIgnoreCase("Pasteurized")) pasteurizedCount++;
                    else if (milkTreatment.equalsIgnoreCase("Raw")) rawCount++;

                    if (!organicStr.isEmpty() && !moistureStr.isEmpty()) {
                        int organic = Integer.parseInt(organicStr);
                        double moisture = Double.parseDouble(moistureStr);
                        if (organic == 1 && moisture > 41.0) organicHighMoisture++;
                    }

                    if (!milkType.isEmpty()) {
                        milkTypeCount.put(milkType, milkTypeCount.getOrDefault(milkType, 0) + 1);
                    }

                } catch (Exception e) {
                    // skip invalid or missing data
                }
            }

            String mostCommonType = "";
            int maxCount = 0;
            for (String type : milkTypeCount.keySet()) {
                int count = milkTypeCount.get(type);
                if (count > maxCount) {
                    maxCount = count;
                    mostCommonType = type;
                }
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                writer.println("Cheese Analysis Results");
                writer.println("------------------------");
                writer.println("Pasteurized milk cheeses: " + pasteurizedCount);
                writer.println("Raw milk cheeses: " + rawCount);
                writer.println("Organic cheeses with moisture > 41%: " + organicHighMoisture);
                writer.println("Most common milk type: " + mostCommonType);
            }

            System.out.println("Results written to " + outputFile);

        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
        }
    }
}
