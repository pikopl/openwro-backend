package com.xaltome.smartcity.carparks.dbservice.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

@Entity
public class CarPark {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long carParkid;
	
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
	private Long capacity;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="carPark")
	private Collection<CarParkLoad> carParksLoads;

	public long getCarParkid() {
		return carParkid;
	}

	public void setCarParkid(long carParkid) {
		this.carParkid = carParkid;
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

	public Long getCapacity() {
		return capacity;
	}

	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CarPark [carParkid=");
		builder.append(carParkid);
		builder.append(", name=");
		builder.append(name);
		builder.append(", street=");
		builder.append(street);
		builder.append(", city=");
		builder.append(city);
		builder.append(", country=");
		builder.append(country);
		builder.append(", capacity=");
		builder.append(capacity);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (carParkid ^ (carParkid >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		CarPark other = (CarPark) obj;
		if (carParkid != other.carParkid)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
