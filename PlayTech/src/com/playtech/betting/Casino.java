package com.playtech.betting;

import java.util.Map;
import java.util.UUID;

public class Casino {
    private int balance; // Casino's balance

    // Constructor initializes balance to 0
    public Casino() {
        this.balance = 0;
    }

    // Method to process a match and update casino balance
    public void processMatch(Match match, Map<UUID, Player> players) {
        // Update casino balance based on match results
        switch (match.getResult()) {
            case "A":
                balance += calculateMatchWins(match.getRateA(), players, "A");
                break;
            case "B":
                balance += calculateMatchWins(match.getRateB(), players, "B");
                break;
            case "DRAW":
                // Handle "Draw" case (coins are returned to players)
                break;
        }
    }

    // Method to calculate match wins and update casino balance
    private int calculateMatchWins(double rate, Map<UUID, Player> players, String side) {
        return (int) players.values().stream()
                .filter(player -> player.getTransactions().stream()
                        .anyMatch(transaction -> "BET".equals(transaction.getOperation()) &&
                                side.equals(transaction.getSide()) &&
                                side.equals(transaction.getMatchResult())))
                .mapToDouble(player -> player.getTransactions().stream()
                        .filter(transaction -> "BET".equals(transaction.getOperation()) &&
                                side.equals(transaction.getSide()) &&
                                side.equals(transaction.getMatchResult()))
                        .mapToDouble(transaction -> transaction.getCoins() * rate)
                        .sum())
                .sum();
    }

    // Getter method to retrieve casino balance
    public int getBalance() {
        return balance;
    }
}
