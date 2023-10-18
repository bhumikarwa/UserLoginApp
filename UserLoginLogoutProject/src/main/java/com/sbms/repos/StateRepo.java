package com.sbms.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbms.entities.State;
import com.sbms.entities.StateEntity;

public interface StateRepo extends JpaRepository<State, Integer>{
	
	@Query("select new com.sbms.entities.StateEntity(id, name) from State where country.id = :countryId")
	public List<StateEntity> findByCountry(@Param("countryId") Integer countryId);
}
