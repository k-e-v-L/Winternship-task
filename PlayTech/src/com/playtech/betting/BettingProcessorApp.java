package com.playtech.betting;

import java.io.*;
import java.util.*;

public class BettingProcessorApp {

    public static void main(String[] args) {
        try {
            // Step 1: Read player data
            Map<UUID, Player> players = readPlayerData();

            // Step 2: Process player data
            Map<UUID, String> illegalPlayers = processPlayerData(players);

            // Step 3: Process match data
            Casino casino = processMatchData(players);

            // Step 4: Write results
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"))) {
                writeResults(writer, players, illegalPlayers, casino);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<UUID, Player> readPlayerData() throws IOException {
        Map<UUID, Player> players = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("player_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                UUID playerId = UUID.fromString(parts[0]);
                String operation = parts[1];
                UUID matchId = parts.length > 2 && !parts[2].isEmpty() ? UUID.fromString(parts[2]) : null;
                int coins = Integer.parseInt(parts[3]);
                String side = parts.length > 4 ? parts[4] : null;

                players.computeIfAbsent(playerId, Player::new)
                        .addTransaction(new Transaction(operation, matchId, coins, side));
            }
        }

        return players;
    }

    private static Map<UUID, String> processPlayerData(Map<UUID, Player> players) {
        Map<UUID, String> illegalPlayers = new HashMap<>();

        for (Player player : players.values()) {
            if (!player.isLegitimate()) {
                illegalPlayers.put(player.getId(), player.getFirstIllegalOperation());
            }
        }

        return illegalPlayers;
    }

    private static Casino processMatchData(Map<UUID, Player> players) throws IOException {
        Casino casino = new Casino();

        try (BufferedReader reader = new BufferedReader(new FileReader("match_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                UUID matchId = UUID.fromString(parts[0]);
                double rateA = Double.parseDouble(parts[1]);
                double rateB = Double.parseDouble(parts[2]);
                String result = parts[3];

                Match match = new Match(matchId, rateA, rateB, result);
                casino.processMatch(match, players);
            }
        }

        return casino;
    }

    private static void writeResults(BufferedWriter writer, Map<UUID, Player> players,
                                     Map<UUID, String> illegalPlayers, Casino casino) throws IOException {
        // Write legitimate player results
        List<Player> legitimatePlayers = new ArrayList<>(players.values());
        legitimatePlayers.sort(Comparator.comparing(p -> p.getId().toString()));

        // Track if any player has made an illegal action
        boolean anyIllegalAction = illegalPlayers.size() > 0;

        // If there is at least one illegal action, don't update casino balance
        if (!anyIllegalAction) {
            for (Player player : legitimatePlayers) {
                writer.write(player.getId() + " " + player.getBalance() + " " +
                        String.format(Locale.US, "%.2f", player.getWinRate()));
                writer.newLine();
            }
            if (!legitimatePlayers.isEmpty()) {
                writer.newLine();
            }

            // Write casino balance only if there are no illegal actions
            int totalBetWins = calculateTotalBetWins(players);
            int casinoBalanceChange = totalBetWins - casino.getBalance();
            if (totalBetWins > 0) {
                writer.write("Casino " + casinoBalanceChange);
            } else {
                writer.write("Casino " + 0);
            }
        }
    }

    private static int calculateTotalBetWins(Map<UUID, Player> players) {
        return (int) players.values().stream()
                .filter(player -> player.getTotalBets() > 0)
                .mapToDouble(player -> player.getWinRate() * player.getTotalBets())
                .sum();
    }
}
