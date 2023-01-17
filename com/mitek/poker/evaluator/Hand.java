package com.mitek.poker.evaluator;

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

        for (int i = 0; i < cards.length; ++i) {
            cards[i] = new Card(h[i]);
            handStr += h[i].toString() + " ";
        }
        this.player_number = pl;
        this.cards = h;
        this.rank = rnk;
    }

    public void setHoleCards(Card c1, Card c2) {

        this.hole_cards[0] = c1;
        this.hole_cards[1] = c1;
    }

    @Override
    public String toString() {
        String ret = String.format("Player %d \thole cards: [%s,%s] \trank: %d \t Hand: %s \t Cards: %s",
                player_number,
                hole_cards[0],
                hole_cards[1],
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