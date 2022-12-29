package com.mitek.poker.evaluator;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.GCMParameterSpec;

@SuppressWarnings("unused")
class MainApp {
        public static Logger LOGGER = Logger.getLogger("poker.logger");

        public static void main(String args[]) {
                
                System.out.println("\n\n#####################\n");
                LOGGER.setLevel(Level.OFF);
                HoldemGame g;
                g = new HoldemGame(9);
                Utils.Timer.startTimer();
                g.play(1000000);
                Utils.Timer.printSecondsFromStart();
                LOGGER.log(Level.INFO, "Exiting.");

        }
}