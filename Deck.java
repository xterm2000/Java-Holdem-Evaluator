import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    /* CONSTANTS */
    public static final int DECK_SIZE = 52;
    public static final int HAND_SIZE = 5;
    public static final String ranks = "23456789TJQKA";
    public static final String suits = "CDHS";

    /** deck as Cards */
    public ArrayList<Card> deck_data = new ArrayList<Card>();

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
        init();
    }

    /** (re)construct the deck */
    private void init() {
        deck_data.clear();
        for (int i = 0; i < ranks.length(); ++i) {
            for (int j = 0; j < suits.length(); ++j) {
                deck_data.add(new Card("" + ranks.charAt(i) + suits.charAt(j)));
            }
        }
    }

    /** shuffle using Collections.shuffle() */
    public void shuffle() {
        Collections.shuffle(deck_data);
    }

    /**
     * returns the top card from the deck while removing it
     * 
     * @return top card in the deck
     */
    public Card getCard() {
        Card c = deck_data.get(0);
        deck_data.remove(0);
        return c;
    }

    /**
     * peeks at the top card from the deck
     * 
     * @return top card in the deck
     */
    public Card peekCard() {
        Card c = deck_data.get(0);
        return c;
    }

    public void reset() {
        init();

    }

}
