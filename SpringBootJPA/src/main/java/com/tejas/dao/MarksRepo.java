package com.tejas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tejas.model.Marks;

public interface MarksRepo extends JpaRepository<Marks, Integer>{
	
	@Query("select new com.tejas.model.Marks(a.english, a.math) from Marks a order by a.aid")
	List<Marks> findMarksByAid();

}
