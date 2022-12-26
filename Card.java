import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Card {
    final Logger LOGGER = Logger.getLogger("Logger");

    


    protected int data;
    protected String str_data;

    @Override
    public String toString() {

        return this.str_data;
    }

   
    public Card(String str) {

        int temp = 0;

        int r = Deck.ranks.indexOf(str.charAt(0));
        int s = Deck.suits.indexOf(str.charAt(1));
        
        if (r < 0 || s < 0) {
            LOGGER.log(Level.SEVERE, "Card not exists: " + str);
            return;

        }
        // System.out.println("Rank:" + r + " Suit:" + s);

        temp += Math.pow(2, r);
        temp <<= 4;
        // System.out.println(Utils.padLeftZeros(Integer.toBinaryString(temp), 32));

        temp += Math.pow(2, s);
        temp <<= 4;

        temp += r;
        temp <<= 8;

        temp += Utils.primes[r];
        // System.out.println(Utils.padLeftZeros(Integer.toBinaryString(temp), 32));
        this.data = temp;
        this.str_data = str;

    }

}
