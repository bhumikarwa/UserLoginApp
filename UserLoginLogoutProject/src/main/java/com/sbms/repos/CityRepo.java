package com.sbms.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbms.entities.City;
import com.sbms.entities.CityEntity;
import com.sbms.entities.State;

public interface CityRepo extends JpaRepository<City, Integer>{
	
	@Query("select new com.sbms.entities.CityEntity(id, name) from City where state.id = :stateId")
	public List<CityEntity> findByState(@Param("stateId") Integer stateId);
	
}
