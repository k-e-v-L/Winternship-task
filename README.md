# Winternship-task
Playtech Winternship: test assignment 2024

I also uploaded it to google drive if it should throw any type of errors here in github: https://drive.google.com/drive/u/5/folders/11gBSNMDpYc_mEu5TjHWPm1XyGClfKwSI

The classes are under src/com/playtech/betting and I also tried writing Junit tests but sadly I should have spent more time on it and I didn't make it.
Casino end balance is: 0

Introduction
Your assignment is to write java program that processes betting data.
Description
    • All operations in this assignment are instant. No need to worry about delays or latency.
    • There will be more than one player and match.
    • Player operations and match results are ordered by time, older events come first.
    • Players can perform 3 and only 3 types of operation – Bet, Deposit, Withdraw.
    • Matches in this assignment will always have 2 sides A and B.
    • Each match will have 1 out of 3 results:
        ◦ A: A side won
        ◦ B: B side won
        ◦ Draw: Match ended in a draw
    • Each match also has a rate value for each of the two sides A and B.
    • If a player bet on the winning side, they gain coins calculated by bet size * corresponding side rate.
    • If a player bet on losing side, they lose the coins.
    • If the match is a draw, the coins are returned to players.
    • One player can only place one bet on one match and only on one side of it.
    • Assume that all players start with 0 coins in their balance.
    • Operation is considered “illegal” if a player is trying to bet or withdraw more coins than they have on their account.
    • Each player and match have their own unique Id’s.
    • All calculated coin results are integers and always rounded down.
Assignment rules
    • Your program will need to read input from two files inside your projects resource folder: “player_data.txt” and “match_data.txt”.
    • Player_data.txt file contains info about player actions.
    • Match_data.txt has data about matches between A and B sides.
    • The program will produce and write results into a single text file, “result.txt” in the same location with the Main class.
    • Coin numbers in transactions are int type, account balance values are long.
Input data
    • Input data is provided in text file.
    • One line contains info about only one action.
    • Data in input file is grouped by player UID.
    • Each group of player actions is ordered by time, with older events first.
    • Example player_data.txt contents:
163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,DEPOSIT,,4000,
163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,BET,abae2255-4255-4304-8589-737cdff61640,500,A
163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,WITHDRAW,,200,
Explanation:
        ◦ First value is player ID – A random UUID.
        ◦ Second value is player operation – One of 3 operations: DEPOSIT, BET, WITHDRAW.
        ◦ Third value (if exist) is a match Id – A random UUID.
        ◦ Fourth value is the coin number player use for that operation.
        ◦ Fifth value (if exist) is the side of the match the player places the bet on.
    • Each match in the match data has unique Id and has only one result.
    • Example of match_data.txt contents:
abae2255-4255-4304-8589-737cdff61640,1.45,0.75,A
Explanation:
        ◦ First value is the match Id – A random UUID.
        ◦ Second value is the return rate for A side.
        ◦ Third value is the return rate for B side.
        ◦ Fourth value is the result of the match.
Output data
In the results.txt file there are 3 expected result groups separated by empty line between each group. Results.txt file should be written into the same directory as your Main class: 
    • List of all legitimate player IDs followed with their final balance and their betting win rate (Win rate is calculated by number of won game / number of placed bets) 
        ◦ Example: 163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4 4321 0,80 
        ◦ player ID, balance number, win rate are separated by space.
        ◦ win rate is big decimal number with 2 decimal characters.
        ◦ The list is ordered by player ID. 
        ◦ Player is legitimate if they did not attempt any illegal action.
    • List of all illegitimate players represented by their first illegal operation 
        ◦ Example: 163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4 BET abae2255-4255-4304-8589-737cdff61640 5000 A 
        ◦ The list is ordered by player ID. 
        ◦ In case of WITHDRAW operation, empty values should be presented as “null”, e.g 4925ac98-833b-454b-9342-13ed3dfd3ccf WITHDRAW null 8093 null 
    • Coin changes in casino host balance.
        ◦ Only players BET operations impact casino balance, e.g Player1 won 500 coins while Player2 lose 2000 coins from betting, so host balance will get extra 1500 coins.
        ◦ Illegal actions don’t impact casino balance.
In case the first or second part of the results are empty lists, an empty line should be written into the output file for corresponding section. 
