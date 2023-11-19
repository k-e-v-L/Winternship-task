package com.playtech.betting;

import java.util.UUID;

public class Transaction {
    private String operation;
    private UUID matchId;
    private int coins;
    private String side;

    // Additional fields
    private String matchResult;
    private double rate;

    // Constructor to initialize a Transaction object
    public Transaction(String operation, UUID matchId, int coins, String side) {
        this.operation = operation;
        this.matchId = matchId;
        this.coins = coins;
        this.side = side;
    }

    // Getter methods for accessing Transaction fields
    public String getOperation() {
        return operation;
    }

    public UUID getMatchId() {
        return matchId;
    }

    public int getCoins() {
        return coins;
    }

    public String getSide() {
        return side;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public double getRate() {
        return rate;
    }

    // Setter methods for setting additional fields
    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    // Check if the transaction is legitimate based on its operation
    public boolean isLegitimate() {
        switch (operation) {
            case "BET":
                return coins >= 0 && (side.equals("A") || side.equals("B"));
            case "WITHDRAW":
                return coins >= 0;
            default:
                return true;
        }
    }

    // String representation of the Transaction object
    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", operation, matchId, coins, side, matchResult);
    }
}
