package com.mitek.poker.evaluator;

import java.util.logging.Level;
import java.util.logging.Logger;

class MainApp {
        public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        public static void main(String args[]) {

                System.out.println("\n\n#####################\n");
                // LOGGER.setLevel(Level.OFF);
                // HoldemGame g;
                // g = new HoldemGame(2);
                // Utils.Timer.startTimer();
                // g.play(10000);
                // Utils.Timer.printSecondsFromStart();

                Game2 g = new Game2();
                g.addPlayer("Negreanu", "Ah", "As", 1000);
                g.addPlayer("Ivey", "7h", "7s", 1000);
                g.addPlayer(null, "", "", 1000);
                g.addPlayer(null, "", "", 1000);

                do {
                        System.out.println(g.getGameStateStr());

                        g.nextRound();
                        System.out.println("Paused...");
                        String ch = System.console().readLine();

                } while (g.getGameState() <= Game2.GameState.RIVER);

                LOGGER.log(Level.INFO, "Exiting.");

        }
}