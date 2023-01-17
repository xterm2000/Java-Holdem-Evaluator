package com.mitek.poker.evaluator;

/** helper struct */
public class Player {

    public String name = "Noname";
    public Card hole_cards[] = new Card[2];
    public boolean hold_Cards = false;
    public int stack;

    public Player(String n) {
        if (n == null)
            this.name = "NoName";
        else
            this.name = n;

    }

    public void setCards(String c1, String c2) {
        this.setCards(new Card(c1), new Card(c2));
    }

    /** set the player's cards */
    public void setCards(Card c1, Card c2) {
        this.hole_cards[0] = c1;
        this.hole_cards[1] = c2;

        if (!this.name.equals("NoName"))
            this.hold_Cards = true;
    }

    public Card[] getCards() {
        Card[] retVal = new Card[2];
        retVal[0] = this.hole_cards[0];
        retVal[1] = this.hole_cards[1];
        return retVal;
    }

};