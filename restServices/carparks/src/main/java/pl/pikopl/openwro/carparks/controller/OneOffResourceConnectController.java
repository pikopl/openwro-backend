/**
 * 
 */
package pl.pikopl.openwro.carparks.controller;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.pikopl.openwro.core.dataconverter.CarParkDataConverter;
import pl.pikopl.openwro.resourceconnector.http.HttpConnector;
import pl.pikopl.openwro.resourceconnector.http.HttpRequestFailureException;

/**
 * @author kopajczy
 *
 */
@RestController
public class OneOffResourceConnectController {
	final private static String RESOURCE_URL = "http://www.wroclaw.pl/open-data/opendata/its/parkingi/parkingi.csv";

	/**
	 * Sends request to resource server and returns result code
	 * 
	 * Request example:
	 * GET http://localhost:8080/carparks/oneOffResourceConnect
	 * 
	 * @return request result code
	 */
	@RequestMapping(value = "/oneOffResourceConnect", method = RequestMethod.GET)
	public Long oneOffResourceConnect() {
		System.out.println("ENTER:> " + this.getClass() + ":oneOffResourceConnect");
		Long resultCode = -1L;
		try {
			final String result = HttpConnector.sendGET(RESOURCE_URL);
			System.out.println("ANY:> " + this.getClass() + ":oneOffResourceConnect:result:" + result);
			CarParkDataConverter.convertCsv(result);
			resultCode = 200L;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpRequestFailureException e) {
			resultCode = (long) e.getStatusCode();
			e.printStackTrace();
		}
		System.out.println("EXIT:> " + this.getClass() + ":oneOffResourceConnect:resultCode:" + resultCode);
		return resultCode;
	}
}
