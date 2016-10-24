/**
 * 
 */
package com.xaltome.smartcity.weather.dbservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * @author kopajczy
 *
 */
@Entity
public class WeatherStation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long weatherStationId;
	
	@Size(min=1, max=100, message="Name is 1-100 chars")
	@Column(unique = true, nullable=false)
	private String name;
	
	@Column(nullable=true)
	@Size(min=2,max=40, message="Street is 2-40 chars")
	private String street;
	
	@Column(nullable=true)
	@Size(min=2,max=40, message="City is 2-40 chars")
	private String city;
	
	@Column(nullable=true)
	@Size(min=2,max=55, message="Country is 2-55 chars")
	private String country;
	
	@Column(nullable=true)
	private Double gpsXCoordinate;
	
	@Column(nullable=true)
	private Double gpsYCoordinate;

	public long getWeatherStationId() {
		return weatherStationId;
	}

	public void setWeatherStationId(long weatherStationId) {
		this.weatherStationId = weatherStationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getGpsXCoordinate() {
		return gpsXCoordinate;
	}

	public void setGpsXCoordinate(Double gpsXCoordinate) {
		this.gpsXCoordinate = gpsXCoordinate;
	}

	public Double getGpsYCoordinate() {
		return gpsYCoordinate;
	}

	public void setGpsYCoordinate(Double gpsYCoordinate) {
		this.gpsYCoordinate = gpsYCoordinate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WeatherStation [weatherStationId=");
		builder.append(weatherStationId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", street=");
		builder.append(street);
		builder.append(", city=");
		builder.append(city);
		builder.append(", country=");
		builder.append(country);
		builder.append(", gpsXCoordinate=");
		builder.append(gpsXCoordinate);
		builder.append(", gpsYCoordinate=");
		builder.append(gpsYCoordinate);
		builder.append("]");
		return builder.toString();
	}
	
}
