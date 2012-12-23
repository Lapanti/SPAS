package spas.reading;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.xml.sax.InputSource;

/**
 * Connection class is made for easier connecting to the Noppa API, by calling
 * the Noppa API with given parameters in the appropriate form.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public class Connection {
	/**
	 * The first part of the URL required to connect to Noppa API.
	 * 
	 */
	private final String URLBase = "http://noppa-api-dev.aalto.fi/api/v1/";
	/**
	 * The development key required to access content from the Noppa API.
	 * 
	 */
	private final String key = "?key=cdda4ae4833c0114005de5b5c4371bb8";

	/**
	 * The method for getting InputSource of the appropriate XML-file from the
	 * Noppa API.
	 * 
	 * @param URL
	 *            The appropriate part of Noppa API you want to connect to. For
	 *            example "course".
	 * @return The InputSource for the asked XML-file.
	 * @throws ConnectionException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public InputSource openConnection(String URL) throws ConnectionException {
		return openConnection(URL, null, null, null);
	}

	/**
	 * The method for getting InputSource of the appropriate XML-file from the
	 * Noppa API.
	 * 
	 * @param URL
	 *            The appropriate part of Noppa API you want to connect to. For
	 *            example "course".
	 * @param parameter1
	 *            The parameter for when searching departments from inside an
	 *            organization.
	 * @return The InputSource for the asked XML-file.
	 * @throws ConnectionException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public InputSource openConnection(String URL, String parameter1)
			throws ConnectionException {
		return openConnection(URL, parameter1, null, null);
	}

	/**
	 * The method for getting InputSource of the appropriate XML-file from the
	 * Noppa API.
	 * 
	 * @param URL
	 *            The appropriate part of Noppa API you want to connect to. For
	 *            example "course".
	 * @param parameter1
	 *            Search parameters for when you are searching for courses. At
	 *            least one must be given for the search to succeed.
	 * @param parameter2
	 *            Search parameters for when you are searching for courses. At
	 *            least one must be given for the search to succeed.
	 * @param parameter3
	 *            Search parameters for when you are searching for courses. At
	 *            least one must be given for the search to succeed.
	 * @return The InputSource for the asked XML-file.
	 * @throws ConnectionException
	 *             When something goes wrong with connecting to Noppa API.
	 */
	public InputSource openConnection(String URL, String parameter1,
			String parameter2, String parameter3) throws ConnectionException {
		try {
			URL connection;
			if (parameter1 == null && parameter2 == null && parameter3 == null) {
				connection = new URL(URLBase + URL + ".xml" + key);
			} else {
				String urltoconnect = URLBase + URL + ".xml" + key;
				if (parameter1 != null) {
					urltoconnect += parameter1;
				}
				if (parameter2 != null) {
					urltoconnect += parameter2;
				}
				if (parameter3 != null) {
					urltoconnect += parameter3;
				}
				connection = new URL(urltoconnect);
			}
			return new InputSource(connection.openStream());
		} catch (MalformedURLException e) {
			throw new ConnectionException("Error with URL.");
		} catch (IOException e) {
			throw new ConnectionException("Error reading from URL.");
		}
	}
}
