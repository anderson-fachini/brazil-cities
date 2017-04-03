package com.fachini.cities.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fachini.cities.entities.city.City;
import com.fachini.cities.entities.city.CityRepository;
import com.fachini.cities.entities.mesoregion.Mesoregion;
import com.fachini.cities.entities.mesoregion.MesoregionRepository;
import com.fachini.cities.entities.microregion.Microregion;
import com.fachini.cities.entities.microregion.MicroregionRepository;
import com.fachini.cities.entities.state.State;
import com.fachini.cities.entities.state.StateRepository;

@RestController
public class ReadFileController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReadFileController.class);

	public class ReadStatus {

		public String status;

		public ReadStatus(String status) {
			this.status = status;
		}
	}

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private MicroregionRepository microregionRepository;

	@Autowired
	private MesoregionRepository mesoregionRepository;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@RequestMapping("/read-file")
	public ReadStatus readFile(HttpServletResponse response) {
		List<City> cities = Collections.emptyList();
		try {
			cities = readCitiesFromCSV("cities.csv");
		} catch (Exception e) {
			LOGGER.error("Ocorreu um erro na leitura do arquivo de cidades", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return new ReadStatus("Ocorreu um erro na leitura do arquivo de cidades. Mais informações no log.");
		}

		try {
			persistCities(cities);
		} catch (Exception e) {
			LOGGER.error("Ocorreu um erro ao gravar as cidades na base de dados", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return new ReadStatus("Ocorreu um erro ao gravar as cidades na base de dados. Mais informações no log.");
		}

		return new ReadStatus("Arquivo lido com sucesso: " + cities.size() + " importadas.");
	}

	private void persistCities(List<City> cities) {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

		try {
			LOGGER.info("Saving states");
			cities.parallelStream().map(c -> c.getState()).distinct().forEach(s -> {
				stateRepository.save(s);
			});

			LOGGER.info("Saving microregions");
			cities.parallelStream().map(c -> c.getMicroregion()).distinct().forEach(m -> {
				microregionRepository.save(m);
			});

			LOGGER.info("Saving mesoregions");
			cities.parallelStream().map(c -> c.getMesoregion()).distinct().forEach(m -> {
				mesoregionRepository.save(m);
			});

			LOGGER.info("Saving cities");
			cities.forEach(city -> {
				city.setMicroregion(microregionRepository.findByName(city.getMicroregion().getName()));
				city.setMesoregion(mesoregionRepository.findByName(city.getMesoregion().getName()));
				cityRepository.save(city);
			});

			LOGGER.info("All saved");

			transactionManager.commit(transactionStatus);
		} catch (Exception e) {
			transactionManager.rollback(transactionStatus);
			throw e;
		}
	}

	private List<City> readCitiesFromCSV(String fileName) {
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(fileName);

		List<City> cities = new ArrayList<>();
		long readedLines = 0;

		try (Scanner scanner = new Scanner(resourceAsStream)) {
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				readedLines++;

				if (readedLines == 1)
					continue;
				cities.add(parseLine(line));
			}
		}

		return cities;
	}

	private City parseLine(String line) {
		String[] splitted = line.split(",");

		// ibge_id,uf,uf_name,name,capital,lon,lat,no_accents,alternative_names,microregion,mesoregion
		City city = new City();
		city.setIbgeId(Long.parseLong(splitted[0]));
		city.setState(new State(splitted[1], splitted[2]));
		city.setName(splitted[3]);
		city.setCapital(Boolean.valueOf(splitted[4]));
		city.setLongitude(Double.valueOf(splitted[5]));
		city.setLatitude(Double.valueOf(splitted[6]));
		city.setNoAccentsName(splitted[7]);
		city.setAlternativeNames(splitted[8].isEmpty() ? null : splitted[8]);
		city.setMicroregion(new Microregion(splitted[9]));
		city.setMesoregion(new Mesoregion(splitted[10]));

		return city;
	}

}
