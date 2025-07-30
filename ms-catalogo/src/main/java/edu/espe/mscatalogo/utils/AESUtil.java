package edu.espe.mscatalogo.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class AESUtil {

    public static SecretKeySpec getAESKeyFromSHA256(String secret) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(secret.getBytes("UTF-8")); // 32 bytes
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static String encrypt(String data, String secret) throws Exception {
        SecretKeySpec key = getAESKeyFromSHA256(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encrypted, String secret) throws Exception {
        SecretKeySpec key = getAESKeyFromSHA256(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        return new String(original, "UTF-8");
    }
}

