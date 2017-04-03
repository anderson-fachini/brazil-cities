package com.fachini.cities.entities.city;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {

	@Query("SELECT c FROM City c WHERE c.capital IS TRUE ORDER BY c.name")
	List<City> getCapitals();

	@Query(value = "SELECT COUNT(c.*) as amount, s.name as name FROM City c JOIN State s ON c.state_id = s.id GROUP BY s.name ORDER BY amount limit 1", nativeQuery = true)
	List<Object[]> getFewerCitiesState();
	
	@Query(value = "SELECT COUNT(c.*) as amount, s.name as name FROM City c JOIN State s ON c.state_id = s.id GROUP BY s.name ORDER BY amount desc limit 1", nativeQuery = true)
	List<Object[]> getManyCitiesState();
	
	@Query(value = "SELECT COUNT(c.*) as amount, s.name as name FROM City c JOIN State s ON c.state_id = s.id GROUP BY s.name ORDER BY name", nativeQuery = true)
	List<Object[]> getCitiesByState();
	
	City findByIbgeId(Long ibgeId);
	
	List<City> findByStateIdOrderByName(String stateId);

}
