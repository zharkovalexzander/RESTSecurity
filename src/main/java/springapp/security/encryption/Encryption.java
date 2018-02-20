package springapp.security.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public final class Encryption {
    public static final Encryption INSTANCE = new Encryption();
    private String[] salts;

    private Encryption() {
        salts = new String[100];
        for(int i = 0; i < 100; ++i) {
            salts[i] = oneStepEncryption();
        }
        System.out.println("Encryption methods are set up");
    }

    public String oneStepEncryption() {
        StringBuilder code = new StringBuilder();
        long time = System.currentTimeMillis();
        final Random random = new Random();
        char chosen;
        char randomChar;
        while (time > 1) {
            chosen = chooseLastChar(time);
            code.append(chosen);
            randomChar = randomize();
            code.append(randomChar);
            time = (time >> 1) + random.nextInt(2);
        }
        return code.toString();
    }

    public String twoStepEncryption() {
        return md5(oneStepEncryption());
    }

    public String twoStepEncryptionWithSalt() {
        return md5(getSalt(), oneStepEncryption());
    }

    public String threeStepEncryption() {
        return sha256(twoStepEncryption());
    }

    public String threeStepEncryptionWithSalt() {
        return sha256(twoStepEncryptionWithSalt());
    }

    private String getSalt() {
        int pos = new Random().nextInt(salts.length);
        String salt = salts[pos];
        salts[pos] = oneStepEncryption();
        return salt;
    }

    private char randomize() {
        final Random random = new Random();
        double value = random.nextDouble();
        if (value < 0.45) {
            return randomChar(48, 57);
        } else if (value < 0.75) {
            return randomChar(65, 90);
        }
        return randomChar(97, 122);
    }

    private char randomChar(int low, int hi) {
        final Random random = new Random();
        return (char) (low + random.nextInt(hi - low + 1));
    }

    private char chooseLastChar(long seed) {
        return (char) (122 - (seed % 10));
    }

    private String md5(String plainText) {
        return md5(null, plainText);
    }

    private String md5(String salt, String plainText) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return plainText;
        }

        if (salt != null) {
            md.update(salt.getBytes());
        }
        md.update(plainText.getBytes());

        byte byteData[] = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte aByteData : byteData) {
            sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return sb.toString();
    }

    private String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            ex.printStackTrace();
            return this.twoStepEncryption();
        }
    }
}
