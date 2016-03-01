/**
 * 
 */
package pl.pikopl.openwro.core.scheduler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import pl.pikopl.openwro.dbservice.DatabaseService;
import pl.pikopl.openwro.dataconverter.CsvDataConverter;
import pl.pikopl.openwro.resourceconnector.http.HttpConnector;
import pl.pikopl.openwro.resourceconnector.http.HttpRequestFailureException;

import org.jboss.logging.Logger;

/**
 * @author kopajczy
 *
 */
@Service
public class SchedulerService {
	
	@Autowired
	private DatabaseService dbService;
	
	protected static final Logger LOGGER = Logger.getLogger(SchedulerService.class);
	
	final private static String RESOURCE_URL = "http://www.wroclaw.pl/open-data/opendata/its/parkingi/parkingi.csv";
	
	@Scheduled(cron="0 0 4 * * ?")
	public void importData(){
		LOGGER.info("Entering importData()");
		Long resultCode = -1L;
		try {
			final String result = HttpConnector.sendGET(RESOURCE_URL);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.tracef("After getting string result in importData(): %s", result);
			}
			List<Map<String, Object>> data = CsvDataConverter.convert(result);
			dbService.fillCarkParkData(data);
			resultCode = 200L;
		} catch (ClientProtocolException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		} catch (HttpRequestFailureException e) {
			LOGGER.error("HttpRequestFailureException occurs", e);
			resultCode = (long) e.getStatusCode();
		} catch (Exception e){
			LOGGER.error("Other exception occurs", e);
		}
		LOGGER.infof("Leaving importData(): %s", resultCode);
	}
}
