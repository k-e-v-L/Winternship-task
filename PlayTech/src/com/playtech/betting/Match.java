package com.playtech.betting;

import java.util.UUID;

public class Match {
    private UUID id; // Unique identifier for the match
    private double rateA; // Rate for side A
    private double rateB; // Rate for side B
    private String result; // Result of the match (A, B, or DRAW)

    // Constructor to initialize Match object with given parameters
    public Match(UUID id, double rateA, double rateB, String result) {
        this.id = id;
        this.rateA = rateA;
        this.rateB = rateB;
        this.result = result;
    }

    // Getter method to retrieve the match ID
    public UUID getId() {
        return id;
    }

    // Getter method to retrieve the rate for side A
    public double getRateA() {
        return rateA;
    }

    // Getter method to retrieve the rate for side B
    public double getRateB() {
        return rateB;
    }

    // Getter method to retrieve the result of the match
    public String getResult() {
        return result;
    }

    // Override toString method to provide a formatted string representation of the Match
    @Override
    public String toString() {
        return String.format("%s %.2f %.2f %s", id, rateA, rateB, result);
    }
}
