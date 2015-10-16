/**
 * 
 */
package pl.pikopl.openwro.resourceconnector.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author kopajczy
 *
 */
public class HttpConnector {

	public static String sendGET(final String url)
			throws ClientProtocolException, IOException,
			HttpRequestFailureException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		// httpGet.addHeader("User-Agent", USER_AGENT);
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		final int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			throw new HttpRequestFailureException(statusCode);
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				httpResponse.getEntity().getContent()));

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
		// print result
		//System.out.println(result);
		return result;
	}

}
