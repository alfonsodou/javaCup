/**
 * 
 */
package org.javahispano.javacup.applet;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author adou
 * 
 */

public class HttpMessage {

	URL servlet = null;
	String args = null;

	public HttpMessage(URL servlet) {
		this.servlet = servlet;
	}

	// Performs a GET request to the previously given servlet
	// with no query string.
	public InputStream sendGetMessage() throws IOException {
		return sendGetMessage(null);
	}

	// Performs a GET request to the previously given servlet.
	// Builds a query string from the supplied Properties list.
	public InputStream sendGetMessage(Properties args) throws IOException {
		String argString = ""; // default

		if (args != null) {
			argString = "?" + toEncodedString(args);
		}
		URL url = new URL(servlet.toExternalForm() + argString);

		// Turn off caching
		URLConnection con = url.openConnection();
		con.setUseCaches(false);

		return con.getInputStream();
	}

	// Performs a POST request to the previously given servlet
	// with no query string.
	public InputStream sendPostMessage() throws IOException {
		return sendPostMessage(null);
	}

	// Performs a POST request to the previously given servlet.
	// Builds post data from the supplied Properties list.
	public InputStream sendPostMessage(Properties args) throws IOException {
		String argString = ""; // default
		if (args != null) {
			argString = toEncodedString(args); // notice no "?"
		}

		URLConnection con = servlet.openConnection();

		// Prepare for both input and output
		con.setDoInput(true);
		con.setDoOutput(true);

		// Turn off caching
		con.setUseCaches(false);

		// Work around a Netscape bug
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		// Write the arguments as post data
		DataOutputStream out = new DataOutputStream(con.getOutputStream());
		out.writeBytes(argString);
		out.flush();
		out.close();

		return con.getInputStream();
	}

	// Converts a Properties list to a URL-encoded query string
	private String toEncodedString(Properties args) {
		StringBuffer buf = new StringBuffer();
		Enumeration names = args.propertyNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = args.getProperty(name);
			buf.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value));
			if (names.hasMoreElements())
				buf.append("&");
		}
		return buf.toString();
	}
}
