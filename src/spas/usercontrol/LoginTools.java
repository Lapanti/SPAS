package spas.usercontrol;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Class for making and updating a user's login information from the
 * security-perspective. The XML-perspective is handled in
 * {@link UserLoginHandler}. Also contains a few methods that only call
 * handler's respective methods, because for security reasons, handler shouldn't
 * be accessed from outside.
 * 
 * Using code from <a href="https://www.owasp.org/index.php/Hashing_Java"
 * target="_bank">here</a> as a base.
 * 
 * @author Lauri Lavanti
 * @version 0.2
 * @since 0.1
 * 
 */
public class LoginTools {
	/**
	 * The count for how many times a password will be digested during
	 * encryption. Should be at least 1000.
	 */
	private final static int ITERATION_COUNT = 1200;
	/**
	 * Used for the XML-perspective
	 */
	private UserLoginHandler handler;

	/**
	 * Constructor for LoginTools. Only thing of relevance is the correctness of
	 * userpath.
	 * 
	 * @param userpath
	 *            The absolute path to user's file that contains login
	 *            information.
	 */
	public LoginTools(String userpath) {
		handler = new UserLoginHandler(userpath);
	}

	/**
	 * Authentication method, also contains some anti-hacking aspects.
	 * 
	 * @param username
	 *            The user's registered login name.
	 * @param pword
	 *            The user's registered password.
	 * @return <code>true</code>, if and only if, authentication was successful.
	 */
	public boolean authenticate(String username, String pword) {

		/*
		 * Returnable boolean value, if anything goes wrong, it is instantly set
		 * to false.
		 */
		boolean userExist = true;

		/*
		 * If either parameter is null, set boolean to false and null both
		 * parameters.
		 */
		if (username == null || pword == null) {
			userExist = false;
			username = "";
			pword = "";
		}

		// Get password and salt.
		String[] pwordandsalt = handler.getPassword(username);
		String digest, salt;

		// If user existed, length is 2 then get appropriate salt and password.
		if (pwordandsalt != null && pwordandsalt.length == 2) {
			digest = pwordandsalt[0];
			salt = pwordandsalt[1];
		} else {
			/*
			 * User doesn't exist. Set password and salt for making the
			 * computation time take as long as a valid one.
			 */
			digest = "000000000000000000000000000=";
			salt = "00000000000=";
			userExist = false;
		}

		// Get byte presentation of password and salt.
		byte[] bDigest = base64ToByte(digest);
		byte[] bSalt = base64ToByte(salt);

		// Encrypt given password with user's salt.
		byte[] proposedDigest = getHash(pword, bSalt);

		/*
		 * Check if given password matches the one on file, also return
		 * userExist in case there was something wrong with given parameters.
		 */
		return Arrays.equals(proposedDigest, bDigest) && userExist;
	}

	/**
	 * Creates a new file containing user's login information using
	 * file-handler. Encrypts user's password for security purposes.
	 * 
	 * @see UserLoginHandler#saveUser(String, String, String, String, String)
	 *      XML-perspective
	 * @param username
	 *            The login name for new user.
	 * @param pword
	 *            The user's password.
	 * @param email
	 *            User's email. Used in case user forgot password.
	 * @param modelpath
	 *            The absolute path to the file containing a user-file's model.
	 * @return <code>true</code>, if and only if, nothing went wrong with user
	 *         creation.
	 */
	public boolean createUser(String username, String pword, String email,
			String modelpath) {

		// If either username or password was null, do nothing.
		if (username != null && pword != null) {

			// Make sure a user with that username doesn't already exist.
			if (handler.userExists(username)) {
				return false;
			}

			// Create salt. See createSalt() for reasoning.
			byte[] bSalt = createSalt();

			// Encrypt password with salt. See getHash() for details.
			byte[] bDigest = getHash(pword, bSalt);

			// Get String representation of salt and password.
			String sDigest = byteToBase64(bDigest);
			String sSalt = byteToBase64(bSalt);

			// See handler's saveUser-method for details.
			return handler.saveUser(username, sDigest, sSalt, email, modelpath);
		}
		return false;
	}

	/**
	 * Removes user from database. Be careful not to call this unnecessarily,
	 * because this is final.
	 * 
	 * @see UserLoginHandler#deleteUser(String) XML-perspective.
	 * @param username
	 *            The loginname for the user to be deleted.
	 * @param pword
	 *            The password for the user to be deleted.
	 * @return <code>true</code>, if and only if, user was removed successfully.
	 */
	public boolean removeUser(String username, String pword) {

		/*
		 * First make sure user's password was correct to prevent outsiders from
		 * removing users.
		 */
		if (authenticate(username, pword)) {

			// See handler's deleteUser-method for details.
			return handler.deleteUser(username);
		}
		return false;
	}

	/**
	 * Changes user's password to new one.
	 * 
	 * @see UserLoginHandler#changePassword(String, String, String)
	 *      XML-perspective.
	 * @param username
	 *            User's registered login name.
	 * @param oldpword
	 *            User's registered password.
	 * @param newpword
	 *            New password.
	 * @return <code>true</code>, if and only if, change was successful.
	 */
	public boolean changePassword(String username, String oldpword,
			String newpword) {

		// If given username of new password was null, do nothing.
		if (username != null && newpword != null) {

			// If authentication was unsuccessful, do nothing.
			if (authenticate(username, oldpword)) {

				// Create salt for new password.
				byte[] bSalt = createSalt();

				// Encrypt password. See getHash() for details.
				byte[] bDigest = getHash(newpword, bSalt);

				// Get String representations of encrypted password and salt.
				String sDigest = byteToBase64(bDigest);
				String sSalt = byteToBase64(bSalt);

				// See handler's changePassword-method for details.
				return handler.changePassword(username, sDigest, sSalt);
			}
		}
		return false;
	}

	/**
	 * Changes user's email to new one.
	 * 
	 * @see UserLoginHandler#changeEmail(String, String) XML-perspective.
	 * @param username
	 *            User's registered login name.
	 * @param pword
	 *            User's registeres password.
	 * @param newmail
	 *            New email.
	 * @return <code>true</code>, if and only if, everything went well.
	 */
	public boolean changeEmail(String username, String pword, String newmail) {

		// If given parameters were null, do nothing.
		if (username != null && pword != null) {

			// If authentication didn't succeed, do nothing.
			if (authenticate(username, pword)) {

				// See handler's changeEmail-method for details.
				return handler.changeEmail(username, newmail);
			}
		}
		return false;
	}

	/**
	 * Method for getting user's email.
	 * 
	 * @see UserLoginHandler#getEmail(String) XML-perspective.
	 * @param username
	 *            User's registered login name.
	 * @return User's email.
	 */
	public String getEmail(String username) {

		// See handler's getEmail()-method for details.
		return handler.getEmail(username);
	}

	/**
	 * Check to see if given user exists.
	 * 
	 * @see UserLoginHandler#userExists(String) XML-perspective
	 * @param username
	 *            User's registered login name.
	 * @return <code>true</code>, if and only if, user exists.
	 */
	public boolean userExists(String username) {
		
		// See hanlder's userExists()-method for details.
		return handler.userExists(username);
	}

	/**
	 * Encrypts given password using salt. Uses algorithm that makes password
	 * non-decryptable, because no-one should ever be able to see passwords.
	 * Salt is used to make passwords secure against precomputed hashes (<a
	 * href="http://en.wikipedia.org/wiki/Rainbow_table" target="_blank">see
	 * here</a>). The reason for all this is that mostly all algorithms used for
	 * encryption are open source.
	 * 
	 * @param pword
	 *            The password to be encrypted.
	 * @param salt
	 *            The salt to use in encryption. It should be a randomly
	 *            generated number, and preferrably of fixed length.
	 * @return Encrypted password.
	 */
	private static byte[] getHash(String pword, byte[] salt) {
		byte[] input = null;
		try {

			/*
			 * Get digester and update it with given salt. Using SHA-1
			 * algorithm. See:
			 * http://docs.oracle.com/javase/7/docs/technotes/guides
			 * /security/StandardNames.html#MessageDigest for supported
			 * algorithms.
			 */
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(salt);

			/*
			 * Digest password according to ITERATION_COUNT. See:
			 * http://docs.oracle
			 * .com/javase/7/docs/api/java/nio/charset/Charset.html for
			 * supported charsets.
			 */
			input = digest.digest(pword.getBytes("UTF-8"));
			for (int i = 0; i < LoginTools.ITERATION_COUNT; i++) {
				digest.reset();
				input = digest.digest(input);
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			/*
			 * These should never happen, as chosen algorithm and encoding are
			 * both legit. NOTE: Be careful if you change them.
			 */
		}
		return input;
	}

	/**
	 * Creates a randomly generated salt. Salt is used in password encryption to
	 * counter against precomputed hashes and the <a
	 * href="http://en.wikipedia.org/wiki/Birthday_paradox"
	 * target="_blank">birthday paradox</a>.
	 * 
	 * @return The created salt.
	 */
	static byte[] createSalt() {

		// Create the fixed length salt.
		byte[] bSalt = new byte[8];
		try {

			/*
			 * Random the numbers in salt using SecureRandom with
			 * SHA1PRNG-algorithm. See:
			 * http://docs.oracle.com/javase/7/docs/technotes
			 * /guides/security/StandardNames.html#SecureRandom for supported
			 * algorithms.
			 */
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.nextBytes(bSalt);
		} catch (NoSuchAlgorithmException e) {
			// Should never happen because SHA1PRNG is a legit algorithm.
		}
		return bSalt;
	}

	/**
	 * From a base 64 representation, returns the corresponding byte[].
	 * 
	 * @param data
	 *            The base 64 representation.
	 * @return The byte[] representation of given String.
	 */
	static byte[] base64ToByte(String data) {
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			return decoder.decodeBuffer(data);
		} catch (IOException e) {
			// Shuold never happen.
			return null;
		}
	}

	/**
	 * From a byte[] returns a base 64 representation.
	 * 
	 * @param data
	 *            The byte[] to encode.
	 * @return Base 64 representation of given byte[].
	 */
	static String byteToBase64(byte[] data) {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}
}
