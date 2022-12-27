import java.util.logging.Level;
import java.util.logging.Logger;

public class HoldemGame {
    public static Logger LOGGER = Logger.getLogger(null);
    Deck d = new Deck();

    Player[] table = new Player[5];
    

    protected class Player {

        private int hole_cards[] = new int[2];

        public void setCards(int c1, int c2) {
            hole_cards[0] = c1;
            hole_cards[1] = c2;
        }

        public int[] getCards() {
            int[] retVal = new int[2];
            retVal[0] = hole_cards[0];
            retVal[1] = hole_cards[1];
            return retVal;
        }

    };

   
    public HoldemGame() {
        LOGGER.log(Level.INFO, "Game init.");

    }

    public void deal() {
    }

    public void flop() {
    }

    public void turn() {
    }

    public void river() {
    }
}
