package com.fachini.cities.entities.city;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fachini.cities.entities.mesoregion.Mesoregion;
import com.fachini.cities.entities.microregion.Microregion;
import com.fachini.cities.entities.state.State;

@Entity
public class City {

	@Id
	private Long ibgeId;

	@ManyToOne
	private State state;

	private String name;

	private boolean capital;

	private double latitude;

	private double longitude;

	private String noAccentsName;

	private String alternativeNames;

	@ManyToOne
	@JoinColumn(columnDefinition = "bigint", name="microregion_id")
	private Microregion microregion;

	@ManyToOne
	@JoinColumn(columnDefinition = "bigint", name="mesoregion_id")
	private Mesoregion mesoregion;

	public Long getIbgeId() {
		return ibgeId;
	}

	public void setIbgeId(Long ibgeId) {
		this.ibgeId = ibgeId;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCapital() {
		return capital;
	}

	public void setCapital(boolean capital) {
		this.capital = capital;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getNoAccentsName() {
		return noAccentsName;
	}

	public void setNoAccentsName(String noAccentsName) {
		this.noAccentsName = noAccentsName;
	}

	public String getAlternativeNames() {
		return alternativeNames;
	}

	public void setAlternativeNames(String alternativeNames) {
		this.alternativeNames = alternativeNames;
	}

	public Microregion getMicroregion() {
		return microregion;
	}

	public void setMicroregion(Microregion microregion) {
		this.microregion = microregion;
	}

	public Mesoregion getMesoregion() {
		return mesoregion;
	}

	public void setMesoregion(Mesoregion mesoregion) {
		this.mesoregion = mesoregion;
	}

	@Override
	public String toString() {
		return "City [state=" + state + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", microregion=" + microregion + ", mesoregion=" + mesoregion + "]";
	}

}
