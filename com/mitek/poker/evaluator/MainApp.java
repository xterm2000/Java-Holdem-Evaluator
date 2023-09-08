package com.mitek.poker.evaluator;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mitek.poker.evaluatorgui.EvaluatorUI;

class MainApp {
        public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        public static void main(String args[]) {

                FLogger.init("baba.txt");
                FLogger.logln("start----");

                System.out.println("\n\n ##################### \n");
                LOGGER.setLevel(Level.OFF);

                Game2 g = new Game2();

                g.addPlayer("Negreanu", "as", "ah", 1000);
                g.addPlayer("Mokujan", "ad", "kh", 1000);
                g.addPlayer(null, "", "", 1000);
                g.addPlayer(null, "", "", 1000);
                g.addPlayer(null, "", "", 1000);
                g.addPlayer(null, "", "", 1000);
                g.addPlayer(null, "", "", 1000);
                
                // g.addPlayer("Negreanu", "as", "ah", 1000);
                // g.addPlayer(null, "", "", 1000);
                // g.addPlayer(null, "", "", 1000);

                do {
                        g.nextRound();
                        g.printDeck();
                        
                        // if (g.getGameState() >= GameState.FLOP) {
                        // System.out.println("Paused...");
                        // String ch = System.console().readLine();
                        // }

                } while (g.getGameState() <= Game2.GameState.RIVER + 1 && g.game_count < 2000);

                FLogger.shutdown();

                System.out.println("\n\n"+g.historyController.getHandsSummary());

                
                EvaluatorUI u = new EvaluatorUI("Poker Evaluator");
                u.setVisible(true);

                LOGGER.info("Exiting.");
                
        }
}