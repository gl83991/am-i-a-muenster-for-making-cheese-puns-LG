package org.example;

public class Cheese {
    private String name;
    private String country;
    private double fatContent;
    private double proteinContent;

    public Cheese(String name, String country, double fatContent, double proteinContent) {
        this.name = name;
        this.country = country;
        this.fatContent = fatContent;
        this.proteinContent = proteinContent;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public double getFatContent() {
        return fatContent;
    }

    public double getProteinContent() {
        return proteinContent;
    }

    @Override
    public String toString() {
        return name + " from " + country + " (Fat: " + fatContent + "%, Protein: " + proteinContent + "%)";
    }
    
}
