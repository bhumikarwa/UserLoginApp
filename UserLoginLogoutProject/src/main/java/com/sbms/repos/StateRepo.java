package com.sbms.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbms.entities.State;

public interface StateRepo extends JpaRepository<State, Integer>{
	
	@Query(value="select * from state where country_id=:countryId",nativeQuery = true)
	public List<State> findByCountryId(@Param("countryId") Integer countryId);
}
