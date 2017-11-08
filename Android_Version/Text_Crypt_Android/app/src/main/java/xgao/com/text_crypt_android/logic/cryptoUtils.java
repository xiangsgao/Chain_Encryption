package xgao.com.text_crypt_android.logic;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
 
/**
 * A utility class that encrypts or decrypts a file.
 * @author www.codejava.net
 * Only the generateCrytoKey method is my own. I had modified this class for my needs
 * All the original codes belong to the original author
 */
public class cryptoUtils {
    private static final String TRANSFORMATION = "AES";
    private static final String ALGORITHM = "AES";
 
    public static void encrypt(String key, File inputFile, File outputFile)
            throws cryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }
 
    public static void decrypt(String key, File inputFile, File outputFile)
            throws cryptoException {
        doCrypto(Cipher.DECRYPT_MODE,key, inputFile, outputFile);
    }
 
    private static void doCrypto(int cipherMode, String key, File inputFile,
            File outputFile) throws cryptoException {
    	// This generates the key, helpful if user enters key not in 16, 32, 64, or 128 bytes
            SecretKeySpec secretKey = convertKeyTo128bits(key);
        try{
            // This is the default and supports only 128 bits or 256 bits key length
            // Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
             
            byte[] outputBytes = cipher.doFinal(inputBytes);
             
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
             
            inputStream.close();
            outputStream.close();
             
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new cryptoException("Oops, your key seems wrong or this file is not encrypted");
        }
    }
    

    // generating cryto key using salt. This allows user to enter unique keys of any length, none zero of course
    // This has bugs on Android java JRE so can't use or have to modified
    private static SecretKey generateCrytoKey(String userKey) throws cryptoException{
        char[] charKey = {userKey.charAt(0)};
        byte[] salt = new String (charKey).getBytes();
        charKey = userKey.toCharArray();
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(charKey, salt, 65536, 128);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            return secret;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
          throw new cryptoException(e.getMessage());
        }

    }
        // custom made for android
    private static SecretKeySpec convertKeyTo128bits(String userKey) throws cryptoException{
        byte[] key = userKey.getBytes();
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new cryptoException("key conversion error");
        }
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // use only first 128 bit

        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;

    }
}