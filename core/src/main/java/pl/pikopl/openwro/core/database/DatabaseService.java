/**
 * 
 */
package pl.pikopl.openwro.core.database;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pikopl.openwro.dbservice.model.CarPark;
import pl.pikopl.openwro.dbservice.repository.CarParkLoadRepository;
import pl.pikopl.openwro.dbservice.repository.CarParkRepository;

/**
 * @author kopajczy
 *
 */
@Service
public class DatabaseService {
	
	@Autowired
	private CarParkLoadRepository carParkLoadRepo;
	
	@Autowired
	private CarParkRepository carParkRepo;
	
	public void fillCarkParkLoadTable(final List<Map<String, Object>> data){
		System.out.println("ENTER:> DatabaseFiller.fillCarkParkLoadTable");
		fillCarParkTable();

		System.out.println("EXIT:> DatabaseFiller.fillCarkParkLoadTable");
	}
	
	/**
	 * Fills CarPark table with hardcoded values by now
	 */
	protected void fillCarParkTable(){
		System.out.println("ENTER:> DatabaseFiller.fillCarParkTable");
		
		if (carParkRepo.findByName("Renoma") == null) {
			CarPark carPark1 = new CarPark();
			carPark1.setName("Renoma");
			carPark1.setCapacity(630L);
			carParkRepo.save(carPark1);
		}

		if (carParkRepo.findByName("ul. sw. Antoniego") == null) {
			CarPark carPark2 = new CarPark();
			carPark2.setName("ul. sw. Antoniego");
			carPark2.setCapacity(140L);
			carParkRepo.save(carPark2);
		}

		if (carParkRepo.findByName("Nowy Targ") == null) {
			CarPark carPark3 = new CarPark();
			carPark3.setName("Nowy Targ");
			carPark3.setCapacity(334L);
			carParkRepo.save(carPark3);
		}

		System.out.println("EXIT:> DatabaseFiller.fillCarParkTable");
	}
}
