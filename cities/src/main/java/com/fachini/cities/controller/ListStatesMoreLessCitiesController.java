package com.fachini.cities.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fachini.cities.entities.city.CityRepository;

@RestController
public class ListStatesMoreLessCitiesController {

	@Autowired
	private CityRepository cityRepository;

	public class StateCities {
		public String stateName;
		public Long cities;
	}

	@RequestMapping("/list-states-more-less-cities")
	public List<StateCities> listCapitals() {
		List<StateCities> stateCities = new ArrayList<>();

		List<Object[]> citiesPerState = cityRepository.getCitiesPerState();

		return stateCities;
	}
}
