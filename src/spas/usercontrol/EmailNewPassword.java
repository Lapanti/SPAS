package spas.usercontrol;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Class for making and sending a new password to user's email.
 * 
 * @author Lauri Lavanti
 * @version 1.2.1
 * @since 0.2
 * @see UserLoginHandler
 * 
 */
public class EmailNewPassword {
	/**
	 * The email, from which to send new passwords.
	 */
	private final String from = "do.not.reply.SPAS@gmail.com";
	/**
	 * The above email's password.
	 */
	private final String pword = "";
	/**
	 * UserLoginHandler used in EmailNewPassword
	 */
	private UserLoginHandler handler;

	/**
	 * Creates a new EmailNewPassword-object. Needs UserLoginHandler for
	 * checking userfiles.
	 * 
	 * @param uh
	 *            Handler for checking userfiles.
	 * @see UserLoginHandler For checking user's.
	 */
	public EmailNewPassword(UserLoginHandler uh) {
		handler = uh;
	}

	/**
	 * Main (and only) functionality in this class. Creates and sends a new
	 * password to given email, if and only if, there is a user registered with
	 * that email.
	 * 
	 * @param email
	 *            The user's email.
	 * @param userdb
	 *            The absolute path to the folder containing all the user files.
	 * @return <code>true</code>, if nothing went wrong and user existed, false
	 *         otherwise.
	 * @see UserLoginHandler#getEmail(String) Checks user's with this.
	 * @see UserLoginHandler#changePassword(String, String, String) Changes
	 *      password with this.
	 */
	public boolean createNewPassword(String email, String userdb) {

		// Get the user files and loop through them.
		File[] userfiles = new File(userdb).listFiles();
		for (File f : userfiles) {

			/*
			 * Check that the file being looped through is really a file and not
			 * a folder.
			 */
			if (f.isFile()) {

				// Create the needed user information login handler and the
				// username from file.
				handler.setUserfile(f.getAbsolutePath());
				String username = f.getName().substring(0,
						f.getName().lastIndexOf(".xml"));

				// Check if user has the same email as given.
				if (handler.getEmail(username).equals(email)) {

					// Create the new username.
					String newpword = "";
					SecureRandom rand = new SecureRandom();
					for (int i = 0; i < 5; i++) {
						// Creates a two charactes long random String.
						newpword += new BigInteger(10, rand).toString(32);
					}

					// Change the old password to the new password.
					handler.changePassword(username, newpword,
							UserHandler.byteToBase64(UserHandler.createSalt()));

					// Create message.
					String message = "Hei,\n\nKäyttäjänimesi: " + username
							+ "\nUusi salasanasi: " + newpword
							+ "\n\nSuosittelen vaihtamaan salasanan "
							+ "heti käyttäjätilin hallinnoinnista.";

					// Try to send the email.
					return sendEmail(email, "Uusi salasanasi palveluun: SPAS",
							message);
				}
			}
		} // end of for-loop.

		// Return false in case no match was found.
		return false;
	}

	/**
	 * Sends email to given recipient.
	 * 
	 * @param to Address to send email to.
	 * @param subject Subject for email to be sent.
	 * @param content The message itself.
	 * @return <code>true</code>, if nothing went wrong.
	 */
	private boolean sendEmail(String to, String subject, String content) {
		// Set email properties. (At the moment for gmail.)
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(from, pword);
					}
				});

		// Create message.
		MimeMessage message = new MimeMessage(session);

		try {
			// Set sender and receiver.
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// Set subject and message.
			message.setSubject(subject);
			message.setText(content);

			// Send message.
			Transport.send(message);

			return true;

		} catch (MessagingException me) {
			// This should not happen as long as email server
			// settings are set correctly.
		}
		return true;
	}
}
