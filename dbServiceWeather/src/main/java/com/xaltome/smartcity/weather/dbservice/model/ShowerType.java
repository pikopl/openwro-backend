/**
 * 
 */
package com.xaltome.smartcity.weather.dbservice.model;

/**
 * @author kopajczy
 *
 */
public enum ShowerType {
	
	UNKNOWN(-1),
	NO_SHOWER(69),
	OCCASIONAL_RAIN(70),
	CONSTANT_RAIN(71),
	HEAVY_RAIN(72);
	
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
