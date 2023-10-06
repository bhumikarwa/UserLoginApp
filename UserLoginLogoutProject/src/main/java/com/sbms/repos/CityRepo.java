package com.sbms.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbms.entities.City;
import com.sbms.entities.State;

public interface CityRepo extends JpaRepository<City, Integer>{
	
	@Query(value="select * from city where state_id=:stateId",nativeQuery = true)
	public List<City> findByStateId(@Param("stateId") Integer stateId);
	
}
