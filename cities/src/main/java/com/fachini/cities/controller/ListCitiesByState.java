package com.fachini.cities.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fachini.cities.entities.city.CityRepository;

@RestController
public class ListCitiesByState {

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

		String columnOrder = "amount";

		List<Object[]> citiesPerState = cityRepository.getCitiesPerState(columnOrder);
		Object[] stateLessCities = citiesPerState.get(0);
		Object[] stateMoreCities = citiesPerState.get(citiesPerState.size() - 1);

		stateCities.add(new StateCities((String) stateLessCities[1], (BigInteger) stateLessCities[0]));
		stateCities.add(new StateCities((String) stateMoreCities[1], (BigInteger) stateMoreCities[0]));

		return stateCities;
	}

	@RequestMapping("/list-amount-cities-by-state")
	public List<StateCities> listCitiesByState() {
		List<StateCities> stateCities = new ArrayList<>();

		String columnOrder = "name";

		cityRepository.getCitiesPerState(columnOrder).forEach(result -> {
			stateCities.add(new StateCities((String) result[1], (BigInteger) result[0]));
		});

		return stateCities;
	}
}
