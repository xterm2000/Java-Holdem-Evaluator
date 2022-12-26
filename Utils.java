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

}
