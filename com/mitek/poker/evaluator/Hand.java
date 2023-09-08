package com.mitek.poker.evaluator;

import java.util.Arrays;

public class Hand implements Comparable<Hand> {
    public int rank;
    public String handStr;
    public Card cards[] = null;
    public int player_number;
    private Card[] hole_cards;

    public Hand(Card[] h, int pl, int rnk) {

        this.cards = new Card[5];
        this.hole_cards = new Card[2];

        handStr = "";
        Arrays.sort(h);
        for (int i = 0; i < cards.length; ++i) {
            cards[i] = new Card(h[i]);
            handStr += h[i].toString() + " ";
        }
        this.player_number = pl;
        
        
        this.rank = rnk;
        
    }

    public void setHoleCards(Card c1, Card c2) {

        this.hole_cards[0] = c1;
        this.hole_cards[1] = c2;
    }

    @Override
    public String toString() {
        String ret = String.format("Player %d \thole cards: [%s,%s] \trank: %d \t Hand: %s \t Cards: %s\t %s",
                player_number,
                hole_cards[0],
                hole_cards[1],
                rank,
                PokerEvaluator.hand_rank(rank),
                handStr,
                PokerEvaluator.evalPocketPair(hole_cards[0], hole_cards[1]));
        if (this.player_number == 0)
            ret += " *";
        else if (this.player_number == 1)
            ret += " #";
        return ret;
    }

    /**
     * @return - Class of the hand - [0-10]
     */
    public int getHandClass() {
        return PokerEvaluator.hand_rank(this.rank).ordinal();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Hand))
            return false;
        Hand c = (Hand) obj;
        return c.rank == this.rank;
    }

    @Override
    public int compareTo(Hand o) {
        if (o.rank > this.rank)
            return -1;
        else if (o.rank < this.rank)
            return 1;
        else
            return 0;
    }

}