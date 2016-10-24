/**
 * 
 */
package com.xaltome.smartcity.resourceconnector.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jboss.logging.Logger;

/**
 * @author kopajczy
 *
 */
public class HttpConnector {
	
	protected static final Logger LOGGER = Logger.getLogger(HttpConnector.class);
	
	/**
	 * This charset is used for encoding data on wroclaw.pl
	 */
	protected static final String STREAM_CHARSET = "windows-1250";

	public static String sendGET(final String url)
			throws ClientProtocolException, IOException,
			HttpRequestFailureException {
		LOGGER.infof("Entering sendGET(%s)", url);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		// httpGet.addHeader("User-Agent", USER_AGENT);
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		final int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			throw new HttpRequestFailureException(statusCode);
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				httpResponse.getEntity().getContent(), STREAM_CHARSET));

		String inputLine;
		StringBuffer response = new StringBuffer();
		final String lineSeparator = System.getProperty("line.separator");
		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
			response.append(lineSeparator);
		}
		reader.close();
		httpClient.close();
		
		String result = response.toString();
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving sendGET(): %s", result);
		} else {
			LOGGER.info("Leaving sendGET()");
		}
		return result;
	}

}
