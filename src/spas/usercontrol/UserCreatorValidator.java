package spas.usercontrol;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

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
	private final static int ITERATION_COUNT = 1000;
	private UserXMLHandler handler = new UserXMLHandler();

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
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean createUser(String username, String pword, String email) {
		try {
			if (username != null && pword != null) {
				if (handler.userExists(username)) {
					return false;
				}
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
				byte[] bSalt = new byte[8];
				random.nextBytes(bSalt);
				byte[] bDigest = getHash(pword, bSalt);
				String sDigest = byteToBase64(bDigest);
				String sSalt = byteToBase64(bSalt);
				return handler.saveUser(username, sDigest, sSalt, email);
			}
		} catch (Exception ex) {
		}
		return false;
	}

	public boolean removeUser(String username, String pword) {
		if (authenticate(username, pword)) {
			return handler.deleteUser(username);
		}
		return false;
	}

	public boolean changePassword(String username, String newpword) {
		try {
			if (username != null && newpword != null) {
				if (!handler.userExists(username)) {
					return false;
				}
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
				byte[] bSalt = new byte[8];
				random.nextBytes(bSalt);
				byte[] bDigest = getHash(newpword, bSalt);
				String sDigest = byteToBase64(bDigest);
				String sSalt = byteToBase64(bSalt);
				return handler.changePassword(username, sDigest, sSalt);
			}
		} catch (Exception ex) {
		}
		return false;
	}

	public boolean changeEmail(String username, String pword, String newmail) {
		if (username != null && pword != null) {
			if (!handler.userExists(username)) {
				return false;
			}
			if (authenticate(username, pword)) {
				return handler.changeEmail(username, newmail);
			}
		}
		return false;
	}

	@SuppressWarnings("static-method")
	public byte[] getHash(String pword, byte[] salt)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
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

	public static void main(String[] args) {
		UserCreatorValidator ucv = new UserCreatorValidator();
		Scanner sc = new Scanner(System.in);

		System.out.println("Username?");
		String name = sc.nextLine();

		System.out.println("Password?");
		String pword = sc.nextLine();

		System.out.println("Email??");
		String email = sc.nextLine();

		System.out.println("Creation? " + ucv.createUser(name, pword, email));

		System.out.println("Username login?");
		name = sc.nextLine();

		System.out.println("Password login?");
		pword = sc.nextLine();

		System.out.println("Login success: " + ucv.authenticate(name, pword));
		sc.close();
	}
}
