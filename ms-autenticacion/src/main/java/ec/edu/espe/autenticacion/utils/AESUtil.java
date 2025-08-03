package ec.edu.espe.autenticacion.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

// Clase utilitaria para cifrado y descifrado AES
public class AESUtil {
    /**
     * Genera una clave AES a partir de un secreto usando SHA-256.
     * @param secret Clave secreta en texto plano
     * @return clave AES derivada
     */
    public static SecretKeySpec getAESKeyFromSHA256(String secret) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(secret.getBytes("UTF-8")); // 32 bytes
        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * Cifra un texto usando AES y una clave secreta.
     * @param data Texto a cifrar
     * @param secret Clave secreta
     * @return Texto cifrado en Base64
     */
    public static String encrypt(String data, String secret) throws Exception {
        SecretKeySpec key = getAESKeyFromSHA256(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * Descifra un texto cifrado en Base64 usando AES y una clave secreta.
     * @param encrypted Texto cifrado en Base64
     * @param secret Clave secreta
     * @return Texto descifrado en texto plano
     */
    public static String decrypt(String encrypted, String secret) throws Exception {
        SecretKeySpec key = getAESKeyFromSHA256(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        return new String(original, "UTF-8");
    }
}
