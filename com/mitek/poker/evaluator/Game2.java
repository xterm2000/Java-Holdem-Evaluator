package com.mitek.poker.evaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Game2 {
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static class GameState {
        public static final byte START = 0;
        public static final byte PREFLOP = 1;
        public static final byte FLOP = 2;
        public static final byte TURN = 3;
        public static final byte RIVER = 4;

        public static String stateStr(byte s) {
            String res;
            switch (s) {
                case GameState.START:
                    res = "Start";
                    break;
                case GameState.PREFLOP:
                    res = "Preflop";
                    break;
                case GameState.FLOP:
                    res = "Flop";
                    break;
                case GameState.TURN:
                    res = "Turn";
                    break;
                case GameState.RIVER:
                    res = "River";
                    break;
                default:
                    res = "Game Over";
                    break;

            }
            return res;

        }
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

    private PokerEvaluator p = new PokerEvaluator("Game Evaluator (v0.1).");

    private Deck deck = new Deck();

    private ArrayList<Player> players = new ArrayList<>();

    // private Card[] board = new Card[5];
    private ArrayList<Card> board = new ArrayList<>();

    private byte gameState;

    public byte getGameState() {
        return gameState;
    }

    public String getGameStateStr() {
        return GameState.stateStr(gameState);
    }

    private static void log(String msg) {
        if (LOG)
            System.out.println(msg);
    }

    public Game2() {
        init();
    }

    /* deal the cards to nonamed players */
    private void dealCards() {

        for (int i = 0; i < players.size(); ++i) {

            if (players.get(i).hold_Cards)
                continue;

            Card c1 = deck.getTopCard(), c2 = deck.getTopCard();
            players.get(i).setCards(c1, c2);
        }
    }

    /** inits the game */
    public void init() {
        deck.reset();
        gameState = GameState.START;
    }

    /**
     * add named player to the game. if (name == NoName) - cards wont be held
     * 
     * @param n       - name of the player
     * @param c1
     * @param c2      cards
     * @param p_stack - stack size
     */
    public void addPlayer(String n, String c1, String c2, int p_stack) {
        Player p = new Player(n);
        p.setCards(deck.getCard(c1), deck.getCard(c2));
        p.stack = p_stack;
        players.add(p);
        LOGGER.info("Player " + p.name + " added.");
    }

    /** evaluates the current state of the table */
    public void evaluateCurrentState() {
        LOGGER.info("Evaluating.");
        ArrayList<Hand> hands = new ArrayList<>();

        // nothing to evaluate pre flop
        if (gameState < GameState.FLOP)
            return;

        // loop over players
        for (int i = 0; i < players.size(); ++i) {
            Hand h = evalPlayer(i);
            hands.add(h);
        }
        
        Collections.sort(hands);
        for (Hand h : hands)
            System.out.println(h);
    }

    /** assummes (round > 0) */
    private Hand evalPlayer(int pl) {

        assert (gameState > 0);

        ArrayList<Card> all_cards = new ArrayList<>();

        all_cards.add(players.get(pl).hole_cards[0]);
        all_cards.add(players.get(pl).hole_cards[1]);
        all_cards.addAll(board);

        Hand h;
        SortedSet<Hand> ranks = new TreeSet<>();
        List<int[]> combinations = SubHandGenerator.generate((all_cards.size()), 5);

        for (int[] cur : combinations) {

            Card[] h5 = new Card[5];

            for (int i = 0; i < HAND_SIZE; ++i)
                h5[i] = all_cards.get(cur[i]);

            h = new Hand(h5, pl, 0);

            h.setHoleCards(
                    players.get(pl).hole_cards[0],
                    players.get(pl).hole_cards[1]);

            /** HAND EVALUATION */
            h.rank = p.eval5card(h);
            ranks.add(h);

        }

        // get the best hand for player for a round
        h = ranks.first();

        return (h);
    }

    /** advances to the next round of the game */
    public void nextRound() {

        switch (gameState) {
            case GameState.START:
                deck.shuffle();
                board.clear();
                dealCards();
                printGame();
                break;
            case GameState.PREFLOP:
                break;
            case GameState.FLOP:
                board.add(deck.getTopCard());
                board.add(deck.getTopCard());
                board.add(deck.getTopCard());
                break;
            case GameState.TURN:
            case GameState.RIVER:
                board.add(deck.getTopCard());
                break;
            default:
                System.out.println("game over");
                break;

        }

        evaluateCurrentState();
        gameState++;

    }

    private void printGame() {
    }

}
