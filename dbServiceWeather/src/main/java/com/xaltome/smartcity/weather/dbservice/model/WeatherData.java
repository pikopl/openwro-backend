/**
 * 
 */
package com.xaltome.smartcity.weather.dbservice.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;


/**
 * @author kopajczy
 *
 */
@Entity
@Table(name = "weatherdata", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"timestamp", "weatherstation_weatherStationId"})
	})
public class WeatherData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long weatherDataId;
	
	@Column(nullable=false)
	private Timestamp timestamp;
	
	@Column(nullable=true)
	private Double windSpeed;
	
	@Column(nullable=true)
	private Double windDirection;
	
	@Column(nullable=true)
	private Double humidity;
	
	@Column(nullable=true)
	private Double airTemperature;
	
	@Column(nullable=true)
	private Double groundTemperature;
	
	@Transient
    private transient ShowerType showerType; //actual enum; not stored in db
	@Column(nullable=true)
	private int showerTypeCode; // enum code gets stored in db
	
	@PrePersist
	void populateDBFields(){
		showerTypeCode = showerType.getCode();
	}
	
	@PostLoad
	void populateTransientFields(){
		showerType = ShowerType.valueOf(showerTypeCode);
	}
	
	@ManyToOne
	@PrimaryKeyJoinColumn
	private WeatherStation weatherStation;

	public long getWeatherDataId() {
		return weatherDataId;
	}

	public void setWeatherDataId(long weatherDataId) {
		this.weatherDataId = weatherDataId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(Double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public Double getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(Double windDirection) {
		this.windDirection = windDirection;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Double getAirTemperature() {
		return airTemperature;
	}

	public void setAirTemperature(Double airTemperature) {
		this.airTemperature = airTemperature;
	}

	public Double getGroundTemperature() {
		return groundTemperature;
	}

	public void setGroundTemperature(Double groundTemperature) {
		this.groundTemperature = groundTemperature;
	}

	public ShowerType getShowerType() {
		return showerType;
	}

	public void setShowerType(ShowerType showerType) {
		this.showerType = showerType;
	}

	public WeatherStation getWeatherStation() {
		return weatherStation;
	}

	public void setWeatherStation(WeatherStation weatherStation) {
		this.weatherStation = weatherStation;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WeatherData [weatherDataId=");
		builder.append(weatherDataId);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append(", windSpeed=");
		builder.append(windSpeed);
		builder.append(", windDirection=");
		builder.append(windDirection);
		builder.append(", humidity=");
		builder.append(humidity);
		builder.append(", airTemperature=");
		builder.append(airTemperature);
		builder.append(", groundTemperature=");
		builder.append(groundTemperature);
		builder.append(", showerTypeCode=");
		builder.append(showerTypeCode);
		builder.append(", weatherStation=");
		builder.append(weatherStation);
		builder.append("]");
		return builder.toString();
	}
	
	
}
