package com.xaltome.smartcity.carparks.dbservice.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "carparkload", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"timestamp", "carpark_carparkid"})
	})
public class CarParkLoad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long carParkLoadId;
	
	@Column(nullable=false)
	private Timestamp timestamp;

	@Column(nullable=false)
	private Long freePlaceAmount;

	@Column(nullable=false)
	private Long carInAmount;
	
	@Column(nullable=false)
	private Long carOutAmount;
	
	@ManyToOne
	@PrimaryKeyJoinColumn
	private CarPark carPark;

	public long getCarParkLoadId() {
		return carParkLoadId;
	}

	public void setCarParkLoadId(long carParkLoadId) {
		this.carParkLoadId = carParkLoadId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Long getFreePlaceAmount() {
		return freePlaceAmount;
	}

	public void setFreePlaceAmount(Long freePlaceAmount) {
		this.freePlaceAmount = freePlaceAmount;
	}

	public Long getCarInAmount() {
		return carInAmount;
	}

	public void setCarInAmount(Long carInAmount) {
		this.carInAmount = carInAmount;
	}

	public Long getCarOutAmount() {
		return carOutAmount;
	}

	public void setCarOutAmount(Long carOutAmount) {
		this.carOutAmount = carOutAmount;
	}

	public CarPark getCarPark() {
		return carPark;
	}

	public void setCarPark(CarPark carPark) {
		this.carPark = carPark;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CarParkLoad [carParkLoadId=");
		builder.append(carParkLoadId);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append(", freePlaceAmount=");
		builder.append(freePlaceAmount);
		builder.append(", carInAmount=");
		builder.append(carInAmount);
		builder.append(", carOutAmount=");
		builder.append(carOutAmount);
		builder.append(", carPark=");
		builder.append(carPark);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carPark == null) ? 0 : carPark.hashCode());
		result = prime * result
				+ (int) (carParkLoadId ^ (carParkLoadId >>> 32));
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarParkLoad other = (CarParkLoad) obj;
		if (carPark == null) {
			if (other.carPark != null)
				return false;
		} else if (!carPark.equals(other.carPark))
			return false;
		if (carParkLoadId != other.carParkLoadId)
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
	
	
}
