package com.mitek.poker.evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

class MainApp {
        public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        public static void main(String args[]) {

                // System.out.println("\n\n#####################\n");
                // LOGGER.setLevel(Level.OFF);
                // HoldemGame g;
                // g = new HoldemGame(2);
                // Utils.Timer.startTimer();
                // g.play(10000);
                // Utils.Timer.printSecondsFromStart();
                // PokerEvaluator p = new PokerEvaluator("babajan");
                // int r1 = 0, r2 = 0;
                // Card[] h1 = new Card[] { new Card("ah"),
                // new Card("as"),
                // new Card("kh"),
                // new Card("th"),
                // new Card("2d") };
                // Card[] h2 = new Card[] { new Card("ah"),
                // new Card("as"),
                // new Card("Kh"),
                // new Card("th"),
                // new Card("2d") };

                // r1 = p.eval5card(h1);
                // r2 = p.eval5card(h2);
                // System.out.println(String.format("\nHand 1: %s\t\tHand 2: %s",
                // PokerEvaluator.hand_rank(r1),
                // PokerEvaluator.hand_rank(r2)));

                // Hand hn1 = new Hand(h1, 0, r1);
                // Hand hn2 = new Hand(h2, 1, r2);

                // SortedSet<Hand> hand_stack = new TreeSet<>();
                // hand_stack.add(hn1);
                // hand_stack.add(hn2);

                // List<Hand> hs2 = new ArrayList<>();
                // hs2.add(hn1);
                // hs2.add(hn2);

                // long c = Utils.binomi(51, 2);
                // System.out.println(c);

               List<int[]> t = SubHandGenerator.generate(5, 5);
               System.out.println(t);
                
                // LOGGER.log(Level.INFO, "Exiting.");

        }
}