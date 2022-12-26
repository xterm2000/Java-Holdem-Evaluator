import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    public static final int DECK_SIZE = 52;
    public static final int HAND_SIZE = 5;

    public static final String ranks = "23456789TJQKA";
    public static final String suits = "cdhs";
    public ArrayList<Card> deck_data = new ArrayList<Card>();

    public int[] cards = new int[Deck.DECK_SIZE];

    @Override
    public String toString() {
        String retVal = "";
        for (Card c : deck_data) {
            retVal += c.toString();
        }
        retVal += "\n";
        return (retVal);
    }

    public Deck() {

        for (int i = 0; i < ranks.length(); ++i) {
            for (int j = 0; j < suits.length(); ++j) {
                deck_data.add(new Card("" + ranks.charAt(i) + suits.charAt(j)));
            }
        }

    }

    public void shuffle() {
        if (deck_data.size() < 52) {
            System.err.println("cannot shuffle incomplete deck.");
            return;
        }
        Collections.shuffle(deck_data);

    }

    public Card getCard() {
        Card c = deck_data.get(0);
        deck_data.remove(0);
        return c;
    }

}
