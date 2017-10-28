package Application.logic;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
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
	//private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
 
    public static void encrypt(String key, File inputFile, File outputFile)
            throws cryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }
 
    public static void decrypt(String key, File inputFile, File outputFile)
            throws cryptoException {
        doCrypto(Cipher.DECRYPT_MODE,key, inputFile, outputFile);
    }
 
    private static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws cryptoException {
    	// This generates the key, helpful if user enters key not in 16, 32, 64, or 128 bytes
    	SecretKey crytoKey = generateCrytoKey(key);
        try {
        	if(key == null) {
        		throw new cryptoException("Key conversion error");
        	}
        	//Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, crytoKey);
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
            throw new cryptoException("Error encrypting/decrypting file\nTry checking if the file you selected is encrypted or not\nAlso check if the key is correct when decrypting\n" + ex.toString(), ex);
        }
    }
    
    // generating cryto key using salt. This allows user to enter unique keys of any length, none zero of course
    private static SecretKey generateCrytoKey(String userKey) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}