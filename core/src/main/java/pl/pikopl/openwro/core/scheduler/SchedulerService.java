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

import pl.pikopl.openwro.core.database.DatabaseService;
import pl.pikopl.openwro.core.dataconverter.CarParkDataConverter;
import pl.pikopl.openwro.resourceconnector.http.HttpConnector;
import pl.pikopl.openwro.resourceconnector.http.HttpRequestFailureException;

/**
 * @author kopajczy
 *
 */
@Service
public class SchedulerService {
	
	@Autowired
	private DatabaseService dbService;
	
	final private static String RESOURCE_URL = "http://www.wroclaw.pl/open-data/opendata/its/parkingi/parkingi.csv";
	
	@Scheduled(cron="0 0 4 * * ?")
	public void importData(){
		System.out.println("ENTER:> " + this.getClass() + ":importData");
		Long resultCode = -1L;
		try {
			final String result = HttpConnector.sendGET(RESOURCE_URL);
			System.out.println("ANY:> " + this.getClass() + ":importData:result:" + result);
			List<Map<String, Object>> data = CarParkDataConverter.convertCsv(result);
			dbService.fillCarkParkData(data);
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
		System.out.println("EXIT:> " + this.getClass() + ":importData:resultCode:" + resultCode);
	}
}
