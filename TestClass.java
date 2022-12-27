import java.util.HashSet;
import java.util.Set;

public class TestClass {

    public static void test() {
        System.out.println("------");
        int idx = 0;
        int temp = 0;
        Set<Integer> s = new HashSet<Integer>();

        for (int i = 0; i < 13; ++i) {
            for (int j = i + 1; j < 13; ++j) {
                for (int k = j + 1; k < 13; ++k) {
                    for (int l = k + 1; l < 13; ++l) {
                        for (int m = l + 1; m < 13; ++m) {
                            ++idx;
                            temp = 1 << (12 - i) | 1 << (12 - j) | 1 << (12 - k) | 1 << (12 - l) | 1 << (12 - m);
                            if (temp == 31 || temp == 62 || temp == 124 || temp == 248 || temp == 496 || temp == 992
                                    || temp == 1984 || temp == 3968 || temp == 7936) {
                                System.out.println(Utils.padLeftZeros(Integer.toBinaryString(temp), 16));
                                System.out.println(temp);
                            }

                            s.add(temp);
                        }
                    }
                }

            }

        }
        System.out.println(idx);
        System.out.println(s.size());
    }

}
