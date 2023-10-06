package com.sbms.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbms.entities.Country;

public interface CountryRepo extends JpaRepository<Country, Integer>{
	
}
