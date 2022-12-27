@SuppressWarnings("unused")
public class PokerEvaluator {

    public final int CLUBS = 0x8000;
    public final int DIAMONDS = 0x4000;
    public final int HEARTS = 0x2000;
    public final int SPADES = 0x1000;

    public static int eval5(Card[] hand) {
        if (hand.length != 5)
            return -1;
        int c1 = hand[0].data;
        int c2 = hand[1].data;
        int c3 = hand[2].data;
        int c4 = hand[3].data;
        int c5 = hand[4].data;

        int flush = c1 & c1 & c3 & c4 & c5 & 0xF000;
        if (flush > 0) {
            System.out.println("flush");
        }

        int unique_5 = (c1 | c2 | c3 | c4 | c5) >> 16;

        

        System.out.println(unique_5);
        

        return 0;

    }
}