package com.mitek.poker.evaluator;

public class Card implements Comparable<Card> {

    int data;

    /**
     * construct card from string
     * 
     * @param str - string in form of rank suit ( Ah / AH )
     */
    public Card(String str) {
        if (str == null || str.length() != 2) {
            this.data = 0;
            return;
        }
        str = str.toUpperCase();
        int r = Deck.ranks.indexOf(str.charAt(0));
        int s = Deck.suits.indexOf(str.charAt(1));
        if (r < 0 || s < 0) {

            this.data = 0;
            return;
        }
        this.data = 0 | 1 << (16 + r) | ((0x8000) >> s) | (r << 8) | PokerEvaluator.primes[r];

    }

    public Card(int c) {
        this.data = c;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        Card c = (Card) obj;
        return this.data == c.data;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Card o) {

        short v1 = (short) ((this.data & 0xF00) >> 8);
        short v2 = (short) ((o.data & 0xF00) >> 8);
        if (v1 < v2)
            return -1;
        else if (v1 > v2)
            return 1;
        else
            return 0;
    }

    public Card(Card other) {
        this.data = other.data;
    }

    @Override
    public String toString() {
        char rank = Deck.ranks.charAt((this.data & 0xF00) >> 8);
        // System.out.println(Utils.padLeftZeros(Integer.toBinaryString(s), 16));
        int s = (this.data & 0xF000) >> 12;
        char suit = ' ';
        switch (s) {
            case 1:
                suit = 'S';
                break;
            case 2:
                suit = 'H';
                break;
            case 4:
                suit = 'D';
                break;
            case 8:
                suit = 'C';
                break;
            default:

                break;
        }
        return "" + rank + "" + suit;
    }

}
