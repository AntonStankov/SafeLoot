import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class PasswordDecryptor {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String ENCRYPTION_KEY = "eb283445026f14a97f3a83a4a4090f2f";

    public static String decryptPassword(String encryptedPassword) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            Key secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedPasswordBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decryptedPasswordBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password", e);
        }


    }
    public static void main(String[] args) {

        System.out.println(decryptPassword("3CXcsOSNqlmxJiQ3OabyzW/tSDas5u3iXQN6C7sB6eM="));

    }
}
