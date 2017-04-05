package com.fachini.cities.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fachini.cities.entities.city.City;
import com.fachini.cities.entities.city.CityRepository;

@RestController
public class CityCrudController {
	
	@Autowired
	private CityRepository cityRepository;
	
	private boolean isValidLong(Long value) {
		return value != null && value > 0;
	}
	
	private boolean isValidString(String value) {
		return value != null && !value.trim().isEmpty();
	}
	
	@RequestMapping(path = "/city", method = RequestMethod.POST)
	public ReturnStatus createCity(HttpServletResponse response, @RequestBody City city) {
		if (isValidLong(city.getIbgeId()) && isValidString(city.getName()) && isValidString(city.getState().getId())) {
			cityRepository.save(city);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return new ReturnStatus("The fields ibgeId, name and state are required.");
		}
		
		return new ReturnStatus("City created with success.");
	}
	
}
