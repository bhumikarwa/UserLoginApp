package com.sbms.runner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.sbms.entities.City;
import com.sbms.entities.Country;
import com.sbms.entities.State;
import com.sbms.repos.CityRepo;
import com.sbms.repos.CountryRepo;
import com.sbms.repos.StateRepo;

@Component
public class Runner implements ApplicationRunner{

	@Autowired
	private CountryRepo countryRepo;
	
	  @Autowired 
	  private CityRepo cityRepo;
	  
	  @Autowired 
	  private StateRepo stateRepo;
	 
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		
		cityRepo.deleteAll(); 
		stateRepo.deleteAll();
		countryRepo.deleteAll();
		
		Country country1 = new Country();
		country1.setName("India");
		country1.setCode("+91");
		//country1.setId(1);
		
		State state1 = new State();
		state1.setName("Gujarat");
		state1.setCountry(country1);
		//state1.setId(1);
		
		State state2 = new State();
		state2.setName("MP");
		state2.setCountry(country1);
		//state2.setId(2);
		
		City city1 = new City();
		city1.setName("Ahmedabad");
		city1.setState(state1);
		//city1.setId(1);
		
		City city2 = new City();
		city2.setName("Surat");
		city2.setState(state1);
		//city2.setId(2);
		
		City city3 = new City();
		city3.setName("Mumbai");
		city3.setState(state2);
		//city3.setId(3);
		
		City city4 = new City();
		city4.setName("Bombay");
		city4.setState(state2);
		//city4.setId(4);
		
		Set<City> cities1 = new HashSet<>();
		cities1.addAll(Arrays.asList(city1,city2));
		
		Set<City> cities2 = new HashSet<>();
		cities2.addAll(Arrays.asList(city3,city4));
		
		Set<State> states1 = new HashSet<>();
		states1.addAll(Arrays.asList(state1,state2));
		
		state1.setCity(cities1);
		state2.setCity(cities2);
		country1.setStates(states1);
		
		countryRepo.save(country1);
		
		
		
	}

}
