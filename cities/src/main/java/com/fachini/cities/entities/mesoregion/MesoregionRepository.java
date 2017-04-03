package com.fachini.cities.entities.mesoregion;

import org.springframework.data.repository.CrudRepository;

public interface MesoregionRepository extends CrudRepository<Mesoregion, Long> {

	Mesoregion findByName(String name);
}
