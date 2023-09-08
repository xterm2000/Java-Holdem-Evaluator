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

    /** enum class */
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

    /** generates subhands of 5 from 6,7 cards */
    class SubHandGenerator {

        /**
         * generates subsets of 0,1,2,3,...,n of size r
         * 
         * @param n - size of the big set
         * @param r - size of the subsets
         * @return - List of int arrays (subsets)
         */
        public static List<int[]> generate(int n, int r) {

            List<int[]> combinations = new ArrayList<>();
            helper(combinations, new int[r], 0, n - 1, 0);
            return combinations;
        }

        /** recursive helper */
        private static void helper(
                List<int[]> combinations,
                int data[],
                int start,
                int end,
                int index) {

            if (index == data.length) {

                int[] combination = data.clone();
                combinations.add(combination);

            } else if (start <= end) {

                data[index] = start;
                helper(combinations, data, start + 1, end, index + 1);
                helper(combinations, data, start + 1, end, index);
            }
        }

    }

    private static final boolean LOG = false;
    private static final int HAND_SIZE = 5;

    int game_count = 0;

    public GameHistory historyController = new GameHistory(0);

    public ArrayList<Hand> getRound_hands() {
        return round_hands;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Card> getBoard() {
        return board;
    }

    /** saves hands of all players in a round */
    ArrayList<Hand> round_hands = new ArrayList<>();

    /** array to count hands */
    private int hand_count[] = new int[10];

    /** holds hand evaluation */
    private ArrayList<String> evals = new ArrayList<>();

    /** track of the winning hands */
    private ArrayList<Hand> winner_hand_hist = new ArrayList<>();

    /* data structures */
    /** the evaluator engine */
    private PokerEvaluator evaluator = new PokerEvaluator("Game Evaluator (v0.1).");
    /** playing deck */
    private Deck deck = new Deck();
    /** structure that holds players data */
    private ArrayList<Player> players = new ArrayList<>();

    /** board */
    private ArrayList<Card> board = new ArrayList<>();

    /** keeps the current state of the game (flop, turn etc.) */
    private byte gameState;

    public byte getGameState() {
        return gameState;
    }

    /** returns game state as string */
    public String getGameStateStr() {
        return GameState.stateStr(gameState);
    }

    private static void log(String msg) {
        if (LOG)
            System.out.println(msg);
    }

    public Game2() {
        init();
        evaluator.p2g = this;
    }

    /**
     * deal the cards to nonamed players
     */
    private void dealCards() {

        for (int i = 0; i < players.size(); ++i) {
            Card c1, c2;
            if (players.get(i).hold_Cards) {
                c1 = deck.getCard(players.get(i).hole_cards[0].toString());
                c2 = deck.getCard(players.get(i).hole_cards[1].toString());
            } else {
                c1 = deck.getTopCard();
                c2 = deck.getTopCard();
            }
            players.get(i).setCards(c1, c2);
        }
    }

    /** inits the game */
    public void init() {

        deck.reset();
        gameState = GameState.START;
        game_count++;
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
        if (c1 == null || c2 == null) {
            p.setCards(deck.getTopCard(), deck.getTopCard());
        } else
            p.setCards(deck.getCard(c1), deck.getCard(c2));

        p.stack = p_stack;
        players.add(p);
        LOGGER.info("Player " + p.name + " added.");
    }

    /**
     * evaluates the current state of the table
     * 
     * @return - the list of hands sorted by rank
     */
    public ArrayList<Hand> evaluateCurrentState() {
        ArrayList<Hand> hands = new ArrayList<>();

        // nothing to evaluate pre flop
        if (gameState < GameState.FLOP)
            return null;

        // loop over players
        for (int i = 0; i < players.size(); ++i) {
            Hand h = evalPlayer(i);
            h.setHoleCards(
                    players.get(i).hole_cards[0],
                    players.get(i).hole_cards[1]);
            hands.add(h);
        }

        Collections.sort(hands);
        return (hands);
    }

    /**
     * evaluates given player
     * 
     * @param pl - player number
     */
    private Hand evalPlayer(int pl) {

        assert (gameState > 0);

        ArrayList<Card> all_cards = new ArrayList<>();

        all_cards.add(players.get(pl).hole_cards[0]);
        all_cards.add(players.get(pl).hole_cards[1]);
        all_cards.addAll(board);

        Hand hnd;
        SortedSet<Hand> ranks = new TreeSet<>();
        List<int[]> combinations = SubHandGenerator.generate((all_cards.size()), 5);

        for (int[] cur : combinations) {

            Card[] h5 = new Card[5];

            for (int i = 0; i < HAND_SIZE; ++i)
                h5[i] = all_cards.get(cur[i]);

            hnd = new Hand(h5, pl, 0);

            hnd.setHoleCards(
                    players.get(pl).hole_cards[0],
                    players.get(pl).hole_cards[1]);

            /** HAND EVALUATION */
            hnd.rank = evaluator.eval5card(hnd);
            ranks.add(hnd);

        }

        // get the best hand for player for a round
        hnd = ranks.first();

        return (hnd);
    }

    /** advances to the next round of the game */
    public void nextRound() {

        LOGGER.info(GameState.stateStr(gameState));

        switch (gameState) {
            case GameState.START:
                board.clear();
                deck.shuffle();
                dealCards();
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
                restart();
                return;
        }

        if (gameState == GameState.RIVER)
            historyController.printBoard(board);

        if (gameState >= GameState.FLOP) {
            round_hands = evaluateCurrentState();
            historyController.saveGameState(gameState, players, board, round_hands);
        }
        gameState++;
    }

    /**
     * restarts the game
     */
    private void restart() {

        LOGGER.info("Restarting...");
        init();
    }

    /**
     * prints the board
     */

    /** for debugging: prints the deck */
    public void printDeck() {
        int idx = 0;
        for (Card c : deck.deck_data) {
            log(c + "\t");

            if (++idx % 5 == 0)
                log("");
        }

    }

    public void saveGameState() {

    }

}
