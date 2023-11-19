package com.playtech.betting;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player {
    private UUID id;
    private int balance;
    private List<Transaction> transactions;

    private int totalBets;
    private int wonBets;

    // Constructor to initialize a Player object with a unique ID
    public Player(UUID id) {
        this.id = id;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    // Getter method for accessing the player's ID
    public UUID getId() {
        return id;
    }

    // Getter method for accessing the player's balance
    public int getBalance() {
        return balance;
    }

    // Getter method for accessing the player's transaction history
    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Getter method for accessing the total number of bets made by the player
    public int getTotalBets() {
        return totalBets;
    }

    // Getter method for accessing the number of bets won by the player
    public int getWonBets() {
        return wonBets;
    }

    // Method to add a new transaction to the player's history and update balance
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);

        switch (transaction.getOperation()) {
            case "DEPOSIT":
                balance += transaction.getCoins();
                break;
            case "BET":
                totalBets++;
                if (transaction.getSide().equals(transaction.getMatchResult())) {
                    int betWins = (int) (transaction.getCoins() * transaction.getRate());
                    balance += betWins;
                    wonBets += betWins;
                } else {
                    balance -= transaction.getCoins();
                }
                break;
            case "WITHDRAW":
                balance -= transaction.getCoins();
                break;
        }
    }

    // Check if all transactions made by the player are legitimate
    public boolean isLegitimate() {
        return transactions.stream().allMatch(Transaction::isLegitimate);
    }

    // Get the first illegal operation made by the player
    public String getFirstIllegalOperation() {
        return transactions.stream()
                .filter(transaction -> !transaction.isLegitimate())
                .map(Transaction::toString)
                .findFirst()
                .orElse("");
    }

    // Calculate and return the win rate of the player
    public double getWinRate() {
        return totalBets > 0 ? (double) wonBets / totalBets : 0;
    }
}
