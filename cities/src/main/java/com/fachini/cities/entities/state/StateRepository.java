package com.fachini.cities.entities.state;

import org.springframework.data.repository.CrudRepository;

public interface StateRepository extends CrudRepository<State, String> {

	State findById(String id);
}
