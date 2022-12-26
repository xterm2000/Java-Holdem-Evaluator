import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    /* CONSTANTS */
    public static final int DECK_SIZE = 52;
    public static final int HAND_SIZE = 5;
    public static final String ranks = "23456789TJQKA";
    public static final String suits = "cdhs";

    /** deck as Cards */
    public ArrayList<Card> deck_data = new ArrayList<Card>();

    /** deck as integers */
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

        int suit = 0x8000;
        int idx = 0;
        // init the deck
        for (int i = 0; i < 4; ++i, suit >>= 1) {
            for (int j = 0; j < 13; ++j, ++idx) {
                cards[idx] = Utils.primes[j] | (j << 8) | suit | (1 << (16 + j));

            }
        }

        for (int i = 0; i < cards.length; ++i) {
            System.out.println("" + i + "   " + Utils.padLeftZeros(Integer.toBinaryString(cards[i]), 32));
        }
    }
    

    public void shuffle() {
        if (deck_data.size() < 52) {
            System.err.println("cannot shuffle incomplete deck.");
            return;
        }
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

}
