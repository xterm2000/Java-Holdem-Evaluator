package com.mitek.poker.evaluator;
import java.util.ArrayList;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class HoldemGame {

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
    private int comb7[][] = {
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

    private Deck deck = new Deck();
    private Player[] players = null;
    private Card[] board = new Card[5];
    PokerEvaluator p = new PokerEvaluator("chuck chuck");

    int hand_count[] = new int[10];

    ArrayList<String> evals = new ArrayList<>();

    protected class Player {
        private Card hole_cards[] = new Card[2];

        public void setCards(Card c1, Card c2) {
            hole_cards[0] = c1;
            hole_cards[1] = c2;
        }

        public Card[] getCards() {
            Card[] retVal = new Card[2];
            retVal[0] = hole_cards[0];
            retVal[1] = hole_cards[1];
            return retVal;
        }

    };

    public HoldemGame(int num_players) {
        MainApp.LOGGER.log(Level.INFO, "Game init.");
        players = new Player[num_players];
        for (int i = 0; i < num_players; ++i)
            players[i] = new Player();
    }

    public void playRound(int num_of_rounds) {

        int batch = num_of_rounds / 10;

        for (int i = 0; i < num_of_rounds; ++i) {

            // System.out.println("ROUND #" + i + 1);
            deck.reset();
            deck.shuffle();
            deal();
            flop();
            turn();
            river();
            evaluateCards();
            // printBoard();
            // printEvals();

            if (i % batch == 0) {
                System.out.println((10 * i / batch) + "%");
            }
        }
        System.out.println("100%\n\n");

        int sum = 0;
        for (int i = 0; i < 9; ++i)
            sum += hand_count[i];
        System.out.println("Total hands: " + sum);
        for (int i = 0; i < 9; ++i) {
            String msg = String.format("%s\t\t%d\t%.3f %%",
                    hand_names[i],
                    hand_count[i],
                    (double) hand_count[i] / (double) sum * 100);
                    System.out.println(msg);

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
        System.out.println(msg);
    }

    private void deal() {
        for (int i = 0; i < players.length; ++i) {
            Card c1 = deck.getCard(), c2 = deck.getCard();
            players[i].setCards(c1, c2);
            String msg = String.format(
                    "Player #%d : [ %s %s ]",
                    i,
                    c1.toString(),
                    c2.toString());
            // System.out.println(msg);
        }
    }

    private void flop() {
        board[0] = deck.getCard();
        board[1] = deck.getCard();
        board[2] = deck.getCard();

    }

    private void turn() {
        board[3] = deck.getCard();

    }

    private void river() {
        board[4] = deck.getCard();

    }

    public void evaluateCards() {

        Card cards[] = new Card[7];
        for (int i = 0; i < players.length; ++i) {
            int min_rank = 9999;
            int rank = 0;
            String msg = new String();

            cards[0] = board[0];
            cards[1] = board[1];
            cards[2] = board[2];
            cards[3] = board[3];
            cards[4] = board[4];
            cards[5] = players[i].hole_cards[0];
            cards[6] = players[i].hole_cards[1];

            Card sub_hand[] = new Card[5];

            // go over 5 card hands
            for (int j = 0; j < comb7.length; ++j) {
                for (int k = 0; k < 5; ++k) {
                    sub_hand[k] = cards[comb7[j][k]];
                }
                // rank each hand
                rank = p.eval5(sub_hand);

                // update rank if found less - higher poker hand)
                if (rank < min_rank) {
                    String hand = new String();
                    for (Card c : sub_hand)
                        hand += c + " ";
                    msg = String.format("Hand: %s\t\trank: %d\tcards: %s",
                            p.hand_rank(rank),
                            rank,
                            hand);
                    min_rank = rank;

                }

            } // end subhand loops

            // after calculating subhands, we add the best hand to the list
            hand_count[hand_category(rank)]++;
            evals.add(msg);

        } // end player loop

    }

    public void printEvals() {
        for (int i = 0; i < players.length; ++i) {

            System.out.println(String.format("Player #%d", i));
            System.out.println(evals.get(i));

        }
    }

    private int hand_category(int rank) {
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
}
