package com.fachini.cities.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fachini.cities.entities.city.City;
import com.fachini.cities.entities.city.CityRepository;

@RestController
public class ListCities {

	@Autowired
	private CityRepository cityRepository;

	public class StateCities {
		public String stateName;
		public BigInteger cities;

		public StateCities(String stateName, BigInteger cities) {
			super();
			this.stateName = stateName;
			this.cities = cities;
		}

	}

	@RequestMapping("/list-states-many-fewer-cities")
	public List<StateCities> listStateManyFewerCities() {
		List<StateCities> stateCities = new ArrayList<>();

		Object[] stateLessCities = cityRepository.getFewerCitiesState().get(0);
		Object[] stateMoreCities = cityRepository.getManyCitiesState().get(0);

		stateCities.add(new StateCities((String) stateLessCities[1], (BigInteger) stateLessCities[0]));
		stateCities.add(new StateCities((String) stateMoreCities[1], (BigInteger) stateMoreCities[0]));

		return stateCities;
	}

	@RequestMapping("/list-amount-cities-by-state")
	public List<StateCities> listCitiesByState() {
		List<StateCities> stateCities = new ArrayList<>();

		cityRepository.getCitiesByState().forEach(result -> {
			stateCities.add(new StateCities((String) result[1], (BigInteger) result[0]));
		});

		return stateCities;
	}
	
	@RequestMapping("/get-city-by-ibge-id")
	public City getCityByIbgeId(@RequestParam(value="ibgeId") Long ibgeId) {
		return cityRepository.findByIbgeId(ibgeId);
	}
	
	@RequestMapping("/get-city-by-state-id")
	public List<City> getCityByStateId(@RequestParam(value="stateId") String stateId) {
		return cityRepository.findByStateIdOrderByName(stateId);
	}
}
