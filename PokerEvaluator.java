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

        int q = (c1 | c2 | c3 | c4 | c5) >> 16;
        if (q < 0) {
            // System.out.println(q);
            System.out.println(String.format("%d %d %d %d %d", c1, c2, c3, c4, c5));
            System.out.println(Utils.padLeftZeros(Integer.toBinaryString(c1), 32));
            System.out.println(Utils.padLeftZeros(Integer.toBinaryString(c2), 32));
            System.out.println(Utils.padLeftZeros(Integer.toBinaryString(c3), 32));
            System.out.println(Utils.padLeftZeros(Integer.toBinaryString(c4), 32));
            System.out.println(Utils.padLeftZeros(Integer.toBinaryString(c5), 32));
            System.out.println(hand[0].str_data + " | " + hand[1].str_data + " | " + hand[2].str_data + " | "
                    + hand[3].str_data + " | " + hand[4].str_data);
        }
        System.out.println(Utils.padLeftZeros(Integer.toBinaryString(q), 16));

        return 0;

    }
}