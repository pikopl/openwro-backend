/**
 * 
 */
package com.xaltome.smartcity.carparks.rest.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xaltome.smartcity.carparks.dbservice.DatabaseService;
import com.xaltome.smartcity.dataconverter.CsvDataConverter;
import com.xaltome.smartcity.resourceconnector.http.HttpConnector;
import com.xaltome.smartcity.resourceconnector.http.HttpRequestFailureException;

/**
 * @author kopajczy
 *
 */
@RestController
public class OneOffResourceConnectController {
	
	@Autowired
	private DatabaseService sbService;
	
	protected static final Logger LOGGER = Logger.getLogger(OneOffResourceConnectController.class);
	
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
		LOGGER.info("Entering oneOffResourceConnect()");
		Long resultCode = -1L;
		try {
			final String result = HttpConnector.sendGET(RESOURCE_URL);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.tracef("After getting string result in oneOffResourceConnect(): %s", result);
			}
			List<Map<String, Object>> data = CsvDataConverter.convert(result);
			sbService.fillCarkParkData(data);
			resultCode = 200L;
		} catch (ClientProtocolException e) {
			LOGGER.error("ClientProtocolException occurs", e);
		} catch (IOException e) {
			LOGGER.error("IOException occurs", e);
		} catch (HttpRequestFailureException e) {
			resultCode = (long) e.getStatusCode();
			LOGGER.error("HttpRequestFailureException occurs", e);
		} catch (Exception e){
			LOGGER.error("Other exception occurs", e);
		}
		LOGGER.infof("Leaving oneOffResourceConnect(): %s", resultCode);
		return resultCode;
	}
}
