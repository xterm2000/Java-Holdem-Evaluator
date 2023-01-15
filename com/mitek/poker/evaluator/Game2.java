package com.mitek.poker.evaluator;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Game2 {

    public static class GameState {
        public static final int PREFLOP = 0;
        public static final int FLOP = 1;
        public static final int TURN = 2;
        public static final int RIVER = 3;
    };

    private static final boolean LOG = false;
    private static final int HAND_SIZE = 5;

    /** array to count hands */
    private int hand_count[] = new int[10];

    /** holds hand evaluation */
    private ArrayList<String> evals = new ArrayList<>();

    /** track of the winning hands */
    private ArrayList<Hand> winner_hand_hist = new ArrayList<>();

    /* data structures */
    private Deck deck = new Deck();
    private ArrayList<Player> players = null;
    private Card[] board = new Card[5];
    private PokerEvaluator p = new PokerEvaluator("Game Evaluator v1.1.");
    int state;

    private static void log(String msg) {
        if (LOG)
            System.out.println(msg);
    }

    public Game2() {
        init();
    }

    /** inits the game */
    public void init() {
        state = GameState.PREFLOP;

    }

    public void addPlayer() {

    }

    public void initDeck() {
    }

    /** evaluates the current state of the table */
    public void evaluateCurrentState() {

        // nothing to evaluate pre flop
        if (state == 0) {
            return;
        }

        int[] hands = new int[players.size()];
        // loop over players
        for (int i = 0; i < players.size(); ++i) {

            int rank = evalPlayer(i);

        }
    }

    private int evalPlayer(int pl) {
        List<int[]> combinations = SubHandGenerator.generate((state + 4), 5);
        Card[] sub_hand = new Card[HAND_SIZE];

        for (int[] cur : combinations) {
            for (int i = 0; i < HAND_SIZE; ++i) {

            }
        }

        return (-2);
    }

    /** advances to the next round of the game */
    public void nextRound() {
        if (state == GameState.RIVER)
            return;

        state++;

    }

    /** */

}
