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

    public Player(UUID id) {
        this.id = id;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public int getTotalBets() {
        return totalBets;
    }

    public int getWonBets() {
        return wonBets;
    }

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

    public boolean isLegitimate() {
        return transactions.stream().allMatch(Transaction::isLegitimate);
    }

    public String getFirstIllegalOperation() {
        return transactions.stream()
                .filter(transaction -> !transaction.isLegitimate())
                .map(Transaction::toString)
                .findFirst()
                .orElse("");
    }

    public double getWinRate() {
        return totalBets > 0 ? (double) wonBets / totalBets : 0;
    }
}
