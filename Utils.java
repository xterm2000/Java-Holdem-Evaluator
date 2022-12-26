public class Utils {
    public static int[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41 };
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
