/**
 * 
 */
package pl.pikopl.openwro.weather.dbservice.model;

import javax.persistence.Entity;

/**
 * @author kopajczy
 *
 */
public enum ShowerType {
	
	NO_SHOWER(69),
	RAIN(70),
	SNOW(71),
	RAIN_AND_SNOW(72);
	
	private int code;

	/**
	 * @param value
	 */
	private ShowerType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
    public static ShowerType valueOf(int i){
    	for (ShowerType showerType : values()){
    		if (showerType.code == i){
    			return showerType;
    		}
    	}
    	throw new IllegalArgumentException("No matching constant for " + i);
    }
}
