package com.fachini.cities.entities.city;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends CrudRepository<City, Long> {

	@Query("SELECT c FROM City c WHERE c.capital IS TRUE ORDER BY c.name")
	List<City> getCapitals();

	/**
	 * Get the amount of cities per state
	 * 
	 * @param indexOrder
	 *            1 to order by amount of cities OR 2 to order by the name of
	 *            state
	 * @return
	 */
	@Query(value = "SELECT COUNT(c.*) as amount, s.name as name FROM City c JOIN State s ON c.state_id = s.id GROUP BY s.name ORDER BY :columnOrder", nativeQuery = true)
	List<Object[]> getCitiesPerState(@Param("columnOrder") String columnOrder);

}
