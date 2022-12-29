package com.mitek.poker.evaluator;

/** helper struct */
public class Player {

    public Card hole_cards[] = new Card[2];
    public boolean hold_Cards = false;

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