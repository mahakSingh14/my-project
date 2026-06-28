package com.amanat.backend;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    // In real app, derive key from user password or Google ID
    // For now, use a fixed key for testing
    private static final String FIXED_KEY = "ThisIsASecretKey1234567890123456"; // 32 bytes for AES-256

    public String encrypt(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, "UTF-8");
    }
    // For demo: Hardcoded key (in real app, derive from user vault password)

    public SecretKey getDemoKey() {
        String keyString = "MySuperSecretAmanatKey123456789012"; // 32 characters = 256 bit
        byte[] keyBytes = keyString.getBytes();
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }
}
