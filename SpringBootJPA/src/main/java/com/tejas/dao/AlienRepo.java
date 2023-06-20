package com.tejas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tejas.model.Alien;

public interface AlienRepo extends JpaRepository<Alien, Integer>{
	
	List<Alien> findByTech(String tech);
	List<Alien> findByAidGreaterThan(int aid);
	
	@Query("select new com.tejas.model.Alien(a.aid, a.aname, a.tech) from Alien a order by a.aid")
	List<Alien> findAlienByAid();
	
}
