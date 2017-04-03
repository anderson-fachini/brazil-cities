package com.fachini.cities.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fachini.cities.entities.city.City;
import com.fachini.cities.entities.city.CityRepository;

@RestController
public class ListCapitalsController {

	@Autowired
	private CityRepository cityRepository;

	@RequestMapping("/list-capitals")
	public List<City> listCapitals() {
		return cityRepository.getCapitals();
	}
}
