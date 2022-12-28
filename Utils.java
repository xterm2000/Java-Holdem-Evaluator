import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {

    /**
     * 
     * @param inputString - string to pad
     * @param length      - desired length
     * @return String padded with zeroes on the left
     */
    public static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);
        return sb.toString();
    }

    public static void writeToFile(String fileName, ArrayList<String> data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
        writer.flush();

        for (String s : data) {
            writer.write(s);
            writer.newLine();
        }

        writer.close();
    }

}
