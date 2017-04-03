package com.fachini.cities.entities.microregion;

import org.springframework.data.repository.CrudRepository;

public interface MicroregionRepository extends CrudRepository<Microregion, Long> {

	Microregion findByName(String name);
}
