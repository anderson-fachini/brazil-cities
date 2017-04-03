package com.fachini.cities.entities.city;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {

	@Query("SELECT c FROM City c WHERE c.capital IS TRUE ORDER BY c.name")
	List<City> getCapitals();

	@Query("SELECT count(c), c.state FROM City c GROUP BY c.state ORDER BY 1")
	List<Object[]> getCitiesPerState();
}
