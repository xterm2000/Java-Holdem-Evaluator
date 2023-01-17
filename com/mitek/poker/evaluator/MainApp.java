package com.mitek.poker.evaluator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

class MainApp {
        public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        public static void main(String args[]) {

                FLogger.init("baba.txt");
                FLogger.logln("start----");
             
                System.out.println("\n\n#####################\n");
                LOGGER.setLevel(Level.OFF);

                Game2 g = new Game2();

                g.addPlayer("Negreanu", "ks", "qs", 1000);
                g.addPlayer("Ivey", "7h", "7d", 1000);
                g.addPlayer(null, "", "", 1000);
                // g.addPlayer(null, "", "", 1000);
                // g.addPlayer(null, "", "", 1000);
                // g.addPlayer(null, "", "", 1000);
                // g.addPlayer(null, "", "", 1000);
                // g.addPlayer(null, "", "", 1000);
                // g.addPlayer(null, "", "", 1000);

                do {
                        g.nextRound();
                        // if (g.getGameState() >= GameState.FLOP) {
                        // System.out.println("Paused...");
                        // String ch = System.console().readLine();
                        // }

                } while (g.getGameState() <= Game2.GameState.RIVER + 1 && g.game_count < 10000);

              
               FLogger.shutdown();


                LOGGER.info("Exiting.");

        }
}