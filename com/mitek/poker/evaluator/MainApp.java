package com.mitek.poker.evaluator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.GCMParameterSpec;

@SuppressWarnings("unused")
class MainApp {
        public static Logger LOGGER = Logger.getLogger("poker.logger");
       

        

       

        public static void main(String args[]) {

                System.out.println("\n\n\n\n#####################\n\n");
                LOGGER.setLevel(Level.OFF);
                // #region
                // Deck d = new Deck();
                // d.shuffle();

                // Card hand[] = {
                // d.getCard(),
                // d.getCard(),
                // d.getCard(),
                // d.getCard(),
                // d.getCard()
                // };
                // Card hand2[] = {
                // d.getCard(),
                // d.getCard(),
                // d.getCard(),
                // d.getCard(),
                // d.getCard()
                // };
                // Card hand3[] = {
                // new Card("6s"),
                // new Card("7h"),
                // new Card("9S"),
                // new Card("7s"),
                // new Card("QS")
                // };

                // PokerEvaluator p = new PokerEvaluator("Eval v1.0");
                // p.init();
                // int h1 = p.eval5(hand);
                // System.out.println("hand:\t" + p.hand_rank(h1) + "\nrank: " + h1);
                // h1 = p.eval5(hand3);
                // System.out.println("hand:\t" + p.hand_rank(h1) + "\nrank: " + h1);
                // #endregion

                Utils.checkMem();
                HoldemGame g = new HoldemGame(6);
                Utils.checkMem();
                long start = System.currentTimeMillis();

                g.playRound(1000000);
                Utils.checkMem();

                long timeElapsed = System.currentTimeMillis() - start;

                System.out.println(String.format("Time elapsed (%.2f) seconds.", ((float) timeElapsed / 1000)));

                LOGGER.log(Level.INFO, "Exiting.");

        }
}