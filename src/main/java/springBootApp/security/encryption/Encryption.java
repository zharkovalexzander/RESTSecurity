package springBootApp.security.encryption;

import java.util.Random;

public class Encryption {
    public static String oneStepEncryption() {
        StringBuilder code = new StringBuilder();
        long time = System.currentTimeMillis();
        final Random random = new Random();
        char chosen, randomChar;
        while (time > 1) {
            chosen = chooseLastChar(time);
            code.append(chosen);
            randomChar = randomize();
            code.append(randomChar);
            time = (time >> 1) + random.nextInt(2);
        }
        return code.toString();
    }

    private static char randomize() {
        final Random random = new Random();
        double value = random.nextDouble();
        if (value < 0.45) {
            return randomChar(48, 57);
        } else if (value < 0.75) {
            return randomChar(65, 90);
        }
        return randomChar(97, 122);
    }

    private static char randomChar(int low, int hi) {
        final Random random = new Random();
        return (char) (low + random.nextInt(hi - low + 1));
    }

    private static char chooseLastChar(long seed) {
        return (char) (122 - (seed % 10));
    }
}
