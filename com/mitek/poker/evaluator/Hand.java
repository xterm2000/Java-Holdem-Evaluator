package com.mitek.poker.evaluator;

public class Hand implements Comparable<Hand> {
    public int rank;
    public String handStr;
    public Card cards[] = null;
    public int player_number;

    public Hand(Card[] h, int pl, int rnk) {

        this.cards = new Card[5];
        handStr = "";

        for (int i = 0; i < cards.length; ++i) {
            cards[i] = new Card(h[i]);
            handStr += h[i].toString() + " ";
        }
        this.player_number = pl;
        this.cards = h;
        this.rank = rnk;
    }

    @Override
    public String toString() {
        String ret = String.format("Player %d \t rank: %d \t Hand: %s \t Cards: %s",
                player_number,
                rank,
                PokerEvaluator.hand_rank(rank),
                handStr);
        return ret;
    }

    @Override
    public boolean equals(Object obj) {
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