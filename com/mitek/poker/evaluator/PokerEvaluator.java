package com.mitek.poker.evaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

enum HandRank {
    STRAIGHT_FLUSH,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    FLUSH,
    STRAIGHT,
    THREE_OF_A_KIND,
    TWO_PAIR,
    ONE_PAIR,
    HIGH_CARD,
    NONE;

};

@SuppressWarnings("unused")
public class PokerEvaluator {
    private static final Logger LOGGER = Logger.getGlobal();

    public static int[] primes = {
            2, 3, 5,
            7, 11, 13,
            17, 19, 23,
            29, 31, 37,
            41 };
    public Map<Integer, Integer> flushes = new HashMap<>();
    public Map<Integer, Integer> lookup = new HashMap<>();

    private ArrayList<String> messages = new ArrayList<>();
    String label;

    public PokerEvaluator(String s) {

        this.label = s;
        LOGGER.log(Level.INFO, "Starting evaluator.\t" + this.label);
        init();
    }

    public int eval5(Card[] hand) {
        if (hand.length != 5)
            return -1;
        int card01 = hand[0].data,
                card02 = hand[1].data,
                card03 = hand[2].data,
                card04 = hand[3].data,
                card05 = hand[4].data;

        // check for flush
        int flush = card01 & card02 & card03 & card04 & card05 & 0xF000;
        int unique_5 = (card01 | card02 | card03 | card04 | card05) >> 16;

        if (flush > 0) {
            return flushes.get(unique_5);
        }
        int q = (card01 & 0xFF) *
                (card02 & 0xFF) *
                (card03 & 0xFF) *
                (card04 & 0xFF) *
                (card05 & 0xFF);
        if (q != 0 && lookup.containsKey(q))
            return lookup.get(q);
        return -1;
    }

    public HandRank hand_rank(int val) {
        if (val > 6185)
            return HandRank.HIGH_CARD; // 1277 high card
        if (val > 3325)
            return HandRank.ONE_PAIR; // 2860 one pair
        if (val > 2467)
            return HandRank.TWO_PAIR; // 858 two pair
        if (val > 1609)
            return HandRank.THREE_OF_A_KIND; // 858 three-kind
        if (val > 1599)
            return HandRank.STRAIGHT; // 10 straights
        if (val > 322)
            return HandRank.FLUSH; // 1277 flushes
        if (val > 166)
            return HandRank.FULL_HOUSE; // 156 full house
        if (val > 10)
            return HandRank.FOUR_OF_A_KIND; // 156 four-kind
        if (val >= 0)
            return HandRank.STRAIGHT_FLUSH; // 10 straight-flushes
        return HandRank.NONE;
    }

    /**
     * init function
     * initializes the lookup arrays
     */
    public void init() {
        LOGGER.log(Level.INFO, "Evaluator initializing...");

        long start = System.currentTimeMillis();

        initFlushes();
        initFourOfAKind();
        initFullHouse();
        initStraght();
        initThreeOfAKind();
        initTwoPair();
        initOnePair();
        initHighCard();

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        String msg = String.format("Finished init in %d(ms)", timeElapsed);
        LOGGER.log(Level.INFO, msg);

        System.out.println(String.format("Lookup takes %d KB", Utils.getMemKBytes(lookup)));

    }

    private void initFlushes() {
        flushes.clear();
        int idx = 0;
        int a[] = { 21, 2, 3, 4, 5 };
        int temp = 0;
        flushes.put(7936, 1);
        flushes.put(3968, 2);
        flushes.put(1984, 3);
        flushes.put(992, 4);
        flushes.put(496, 5);
        flushes.put(248, 6);
        flushes.put(124, 7);
        flushes.put(62, 8);
        flushes.put(31, 9);
        flushes.put(4111, 10);
        // non straight flush starting rank
        int hand_rank = 323;

        for (int i = 0; i < 13; ++i) {
            for (int j = i + 1; j < 13; ++j) {
                for (int k = j + 1; k < 13; ++k) {
                    for (int l = k + 1; l < 13; ++l) {
                        for (int m = l + 1; m < 13; ++m) {
                            ++idx;
                            temp = 1 << (12 - i) |
                                    1 << (12 - j) |
                                    1 << (12 - k) |
                                    1 << (12 - l) |
                                    1 << (12 - m);

                            // skip for straight flush
                            if (temp == 4111 ||
                                    temp == 31 ||
                                    temp == 62 ||
                                    temp == 124 ||
                                    temp == 248 ||
                                    temp == 496 ||
                                    temp == 992 ||
                                    temp == 1984 ||
                                    temp == 3968 ||
                                    temp == 7936) {
                                continue;
                            }

                            flushes.put(temp, hand_rank++);
                        }
                    }
                }

            }

        } // end loop

    }

    private void initFourOfAKind() {
        int hand_val = 11;
        int res = 0;
        for (int i = 12; i >= 0; --i) {
            for (int j = 12; j >= 0; --j) {
                if (j == i)
                    continue;
                int idxs[] = { i, i, i, i, j };
                res = getPrimesMult(idxs);
                lookup.put(res, hand_val++);
            }
        }

    }

    private void initFullHouse() {
        int hand_val = 167;
        int res = 0;
        for (int i = 12; i >= 0; --i) {
            for (int j = 12; j >= 0; --j) {
                if (j == i)
                    continue;

                int idxs[] = { i, i, i, j, j };
                res = getPrimesMult(idxs);
                lookup.put(res, hand_val++);

                String msg = String.format(
                        "Full house: %c full of %c",
                        Deck.ranks.charAt(i),
                        Deck.ranks.charAt(j));
            }
        }
    }

    private void initStraght() {
        int hand_val = 1600;
        int temp = 0;

        for (int i = 8; i >= 0; --i) {
            int idxs[] = { i, i + 1, i + 2, i + 3, i + 4 };
            temp = getPrimesMult(idxs);
            lookup.put(temp, hand_val++);
        }

        // add bicycle straight
        int idxs[] = { 0, 1, 2, 3, 12 };
        temp = getPrimesMult(idxs);
        lookup.put(temp, hand_val);

    }

    private void initThreeOfAKind() {
        int hand_val = 1610;
        int temp = 0;
        for (int i = 12; i >= 0; --i) {
            for (int j = 12; j >= 1; --j) {
                for (int k = j - 1; k >= 0; --k) {

                    if (i == j || i == k)
                        continue;
                    int idxs[] = { i, i, i, j, k };
                    temp = getPrimesMult(idxs);
                    lookup.put(temp, hand_val++);
                    String msg = String.format("Three of %c and %c %c kickers",
                            Deck.ranks.charAt(i),
                            Deck.ranks.charAt(j),
                            Deck.ranks.charAt(k));
                }
            }
        }

    }

    private void initTwoPair() {

        int hand_val = 2468;
        int temp = 0;
        messages.clear();

        for (int i = 12; i >= 0; --i) {
            for (int j = i - 1; j >= 0; --j) {
                for (int k = 12; k >= 0; --k) {

                    // skip full house
                    if (k == i || k == j)
                        continue;

                    int idxs[] = { i, i, j, j, k };
                    temp = getPrimesMult(idxs);
                    lookup.put(temp, hand_val++);

                    String msg = String.format("Two pair: %c, %c and %c kicker",
                            Deck.ranks.charAt(i),
                            Deck.ranks.charAt(j),
                            Deck.ranks.charAt(k));
                    messages.add(msg);

                }
            }
        }

    }

    private void initOnePair() {
        int temp = 0;
        int hand_rank = 3326;

        for (int i = 12; i >= 0; --i) {
            for (int j = 12; j >= 0; --j) {
                if (j == i)
                    continue;
                for (int k = j - 1; k >= 0; --k) {
                    if (k == i)
                        continue;
                    for (int l = k - 1; l >= 0; --l) {
                        if (l == i)
                            continue;
                        String msg = String.format("One pair: %c, and %c, %c and %c kickers",
                                Deck.ranks.charAt(i),
                                Deck.ranks.charAt(j),
                                Deck.ranks.charAt(k),
                                Deck.ranks.charAt(l));

                        int idxs[] = { i, i, j, k, l };
                        temp = getPrimesMult(idxs);
                        lookup.put(temp, hand_rank++);

                    }
                }
            }
        }

    }

    private void initHighCard() {

        int temp = 0;
        int hand_rank = 6186;

        for (int i = 12; i >= 0; --i) {
            for (int j = i - 1; j >= 0; --j) {
                for (int k = j - 1; k >= 0; --k) {
                    for (int l = k - 1; l >= 0; --l) {
                        for (int m = l - 1; m >= 0; --m) {
                            int idxs[] = { i, j, k, l, m };
                            temp = getPrimesMult(idxs);
                            // skip straight
                            if (lookup.containsKey(temp)) {
                                continue;
                            }
                            lookup.put(temp, hand_rank++);

                            String msg = String.format(
                                    "High card hand : %c %c %c %c %c",
                                    Deck.ranks.charAt(i),
                                    Deck.ranks.charAt(j),
                                    Deck.ranks.charAt(k),
                                    Deck.ranks.charAt(l),
                                    Deck.ranks.charAt(m));

                        }
                    }
                }
            }
        } // end loop

    }

    /**
     * multiplies primes assigned to cards using integer array for indexes
     * 
     * @param idx - array of indices
     * @return product of the primes
     */
    private int getPrimesMult(int[] idx) {

        int product = 1;
        for (int i = 0; i < idx.length; ++i) {
            product *= primes[idx[i]];
        }
        return product;

    }

    private void dumpToFile(String filename) {
        try {
            Utils.writeToFile(filename, messages);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}