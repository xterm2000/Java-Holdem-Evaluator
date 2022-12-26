public class TestClass {

    public int suit = 0x8000;
    public int[] cards = new int[52];
    public int[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41 };

    public void init_deck() {
        int idx = 0;
        for (int i = 0; i < 4; ++i, suit >>= 1) {
            for (int j = 0; j < 13; ++j, ++idx) {
                cards[idx] = primes[j] | (j << 8) | suit | (1 << (16 + j));

            }
        }

        for (int i = 0; i < cards.length; ++i) {
            System.out.println("" + i + "   " + Utils.padLeftZeros(Integer.toBinaryString(cards[i]), 32));
        }
    }

}
