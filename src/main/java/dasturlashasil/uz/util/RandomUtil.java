package dasturlashasil.uz.util;

import java.util.Random;

public class RandomUtil {

    private static Random random = new Random();
    public static int fiveDigit() {
        return 10000 + random.nextInt(90000); // 10000 dan 99999 gacha
    }
}
