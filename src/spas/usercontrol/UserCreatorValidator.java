package spas.usercontrol;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.catalina.tribes.util.Arrays;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * Using code from https://www.owasp.org/index.php/Hashing_Java as a base.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public class UserCreatorValidator {
	private final static int ITERATION_COUNT = 1001;
	private UserXMLHandler handler = new UserXMLHandler();

	@SuppressWarnings("finally")
	public boolean authenticate(String username, String pword) {
		try {
			boolean userExist = true;
			if (username == null || pword == null) {
				userExist = false;
				username = "";
				pword = "";
			}
			String[] pwordandsalt = handler.getPassword(username);
			String digest, salt;
			if (pwordandsalt.length == 2) {
				digest = pwordandsalt[0];
				salt = pwordandsalt[1];
			} else {
				/*
				 * User doesn't exist. For making the computation time take as
				 * long as a valid one.
				 */
				digest = "000000000000000000000000000=";
				salt = "00000000000=";
				userExist = false;
			}
			byte[] bDigest = base64ToByte(digest);
			byte[] bSalt = base64ToByte(salt);
			
			byte[] proposedDigest = getHash(pword, bSalt);
			
			return Arrays.equals(proposedDigest, bDigest) && userExist;
		} finally {
			return false;
		}
	}

	@SuppressWarnings("finally")
	public boolean createUser(String username, String pword) {
		try {
			if (username != null && pword != null) {
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
				byte[] bSalt = new byte[8];
				random.nextBytes(bSalt);
				byte[] bDigest = getHash(pword, bSalt);
				String sDigest = byteToBase64(bDigest);
				String sSalt = byteToBase64(bSalt);
				handler.saveUser(username, sDigest, sSalt);
				return true;
			}
		} finally {
			return false;
		}
	}

	@SuppressWarnings("static-method")
	public byte[] getHash(String pword, byte[] salt)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
		digest.reset();
		digest.update(salt);
		byte[] input = digest.digest(pword.getBytes("UTF-8"));
		for (int i = 0; i < UserCreatorValidator.ITERATION_COUNT; i++) {
			digest.reset();
			input = digest.digest(input);
		}
		return input;
	}

	/**
	 * From a base 64 representation, returns the corresponding byte[]
	 * 
	 * @param data
	 *            String The base64 representation
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] base64ToByte(String data) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		return decoder.decodeBuffer(data);
	}

	/**
	 * From a byte[] returns a base 64 representation
	 * 
	 * @param data
	 *            byte[]
	 * @return String
	 * @throws IOException
	 */
	public static String byteToBase64(byte[] data) throws IOException {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}
}
