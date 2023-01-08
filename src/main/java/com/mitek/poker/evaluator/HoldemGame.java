package com.mitek.poker.evaluator;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;

public class HoldemGame {

    private static final boolean LOG = true;

    private String hand_names[] = {
            "STRAIGHT_FLUSH",
            "FOUR_OF_A_KIND",
            "FULL_HOUSE",
            "FLUSH     ",
            "STRAIGHT",
            "THREE_OF_A_KIND",
            "TWO_PAIR",
            "ONE_PAIR",
            "HIGH_CARD"
    };

    // lookup to choose 5 from 7
    private int comb5of7[][] = {
            { 0, 1, 2, 3, 4 },
            { 0, 1, 2, 3, 5 },
            { 0, 1, 2, 3, 6 },
            { 0, 1, 2, 4, 5 },
            { 0, 1, 2, 4, 6 },
            { 0, 1, 2, 5, 6 },
            { 0, 1, 3, 4, 5 },
            { 0, 1, 3, 4, 6 },
            { 0, 1, 3, 5, 6 },
            { 0, 1, 4, 5, 6 },
            { 0, 2, 3, 4, 5 },
            { 0, 2, 3, 4, 6 },
            { 0, 2, 3, 5, 6 },
            { 0, 2, 4, 5, 6 },
            { 0, 3, 4, 5, 6 },
            { 1, 2, 3, 4, 5 },
            { 1, 2, 3, 4, 6 },
            { 1, 2, 3, 5, 6 },
            { 1, 2, 4, 5, 6 },
            { 1, 3, 4, 5, 6 },
            { 2, 3, 4, 5, 6 } };

    /**
     * array to count hands
     */
    private int hand_count[] = new int[10];

    /** holds hand evaluation */
    private ArrayList<String> evals = new ArrayList<>();

    /** track of the winning hands */
    private ArrayList<Hand> winner_hand_hist = new ArrayList<>();

    /** PLayer with the held cards */
    private final int HERO_POSITION = 0;

    /* data structures */
    private Deck deck = new Deck();
    private Player[] players = null;
    private Card[] board = new Card[5];
    private PokerEvaluator p = new PokerEvaluator("Game Evaluator v1.1.");

    private static void log(String msg) {
        if (LOG)
            System.out.println(msg);
    }

    public HoldemGame(int num_players) {
        MainApp.LOGGER.log(Level.INFO, "Game init.");

        // init players
        players = new Player[num_players];
        for (int i = 0; i < num_players; ++i)
            players[i] = new Player();
    }

    public void play(int num_of_rounds) {

        int batch = Math.max((num_of_rounds / 10), 1);

        for (int i = 0; i < num_of_rounds; ++i) {

            playRound();
            if (i % batch == 0) {
                log("Progress:" + Integer.toString(10 * i / batch) + "%");
            }
        }
        log("100%\n\n");
        printHandsTotals();
        printHandHistory(false);

    }

    public void playRound() {
        init();
        deal();
        flop();
        turn();
        river();
        evaluateRound();
        // printBoard();
        // printEvals();
    }

    private void dealPlayer(int pos, String c1, String c2) {

        players[pos].hole_cards[0] = deck.getCard(c1);
        players[pos].hole_cards[1] = deck.getCard(c2);
        players[pos].hold_Cards = true;
    }

    private void init() {
        deck.reset();
        dealPlayer(HERO_POSITION, "Ad", "Kd");
         dealPlayer(1, "Ah", "Kh");
        deck.shuffle();
    }

    private void deal() {
        for (int i = 0; i < players.length; ++i) {

            if (players[i].hold_Cards)
                continue;

            Card c1 = deck.getTopCard(), c2 = deck.getTopCard();
            players[i].setCards(c1, c2);
        }
    }

    private void flop() {
        board[0] = deck.getTopCard();
        board[1] = deck.getTopCard();
        board[2] = deck.getTopCard();
    }

    private void turn() {
        board[3] = deck.getTopCard();
    }

    private void river() {
        board[4] = deck.getTopCard();

    }

    public void evaluateRound() {

        Card cards[] = new Card[7];
        SortedSet<Hand> hand_stack = new TreeSet<>();
        
        hand_stack.clear();
        int min_rank = 9999;
        int rank = 0;
        // board cards
        cards[0] = board[0];
        cards[1] = board[1];
        cards[2] = board[2];
        cards[3] = board[3];
        cards[4] = board[4];

        // loop over players to make the best hand
        for (int pl_idx = 0; pl_idx < players.length; ++pl_idx) {

            cards[5] = players[pl_idx].hole_cards[0];
            cards[6] = players[pl_idx].hole_cards[1];

            Card sub_hand[] = new Card[5];
            Hand h = null;

            // go over 5 card hands
            for (int j = 0; j < comb5of7.length; ++j) {

                // take the j-th permutation
                for (int k = 0; k < 5; ++k) {
                    sub_hand[k] = cards[comb5of7[j][k]];
                }
                // rank each hand
                rank = p.eval5(sub_hand);

                // update rank if found less - higher poker hand)
                if (rank < min_rank) {
                    h = new Hand(sub_hand, pl_idx, rank);
                    hand_stack.add(h); // add srongest hand for each player
                    min_rank = rank;
                }

            } // end subhand loops

        } // end player loop

        // winning hand of the round
        winner_hand_hist.add(hand_stack.first());
        // after calculating subhands, we add the best hand to the list
        hand_count[getHandCategory(hand_stack.first().rank)]++;

    }

    public void printEvals() {
        for (int i = 0; i < players.length; ++i) {
            log(String.format("Player #%d", i));
            log(evals.get(i));

        }
    }

    private int getHandCategory(int rank) {
        if (rank > 6185)
            return 8; // 1277 high card
        if (rank > 3325)
            return 7; // 2860 one pair
        if (rank > 2467)
            return 6; // 858 two pair
        if (rank > 1609)
            return 5; // 858 three-kind
        if (rank > 1599)
            return 4; // 10 straights
        if (rank > 322)
            return 3; // 1277 flushes
        if (rank > 166)
            return 2; // 156 full house
        if (rank > 10)
            return 1; // 156 four-kind
        if (rank >= 0)
            return 0; // 10 straight-flushes
        return -1;
    }

    public void printHandHistory(boolean b) {

        int win_counts[] = new int[players.length];

        log("\nWinning HAND_HISTORY:");

        for (int i = 0; i < winner_hand_hist.size(); ++i) {

            Hand h = winner_hand_hist.get(i);

            win_counts[h.player_number]++;
            if (b) {
                String msg = String.format("Player: %d\trank:%d\t%s\tcards:[ %s ]",
                        h.player_number,
                        h.rank,
                        PokerEvaluator.hand_rank(h.rank),
                        h.handStr);

                log(msg);
            }
        }

        int total = winner_hand_hist.size();

        System.out.println("\n");
        for (int i = 0; i < win_counts.length; ++i) {
            String msg = String.format("Player #%d\t%d hands\t%.2f %%", i, win_counts[i],
                    (double) win_counts[i] / (double) total * 100);
            log(msg);
        }

    }

    private void printBoard() {
        String msg = String.format(
                "Flop:\t[ %s %s %s ]\t",
                board[0],
                board[1],
                board[2]);
        msg += String.format("Turn: [ %s ]\t", board[3]);
        msg += String.format("River: [ %s ]", board[4]);
        msg += String.format("\nBoard:\t[ %s %s %s %s %s] ",
                board[0],
                board[1],
                board[2],
                board[3],
                board[4]);
        log(msg);
    }

    public void printHandsTotals() {
        int sum = 0;
        for (int i = 0; i < 9; ++i)
            sum += hand_count[i];

        log("Total hands: " + sum);

        for (int i = 0; i < 9; ++i) {

            String msg = String.format("%s\t\t%d\t\t%.3f %%",
                    hand_names[i],
                    hand_count[i],
                    (double) hand_count[i] / (double) sum * 100);
            log(msg);
        }
    }

}
