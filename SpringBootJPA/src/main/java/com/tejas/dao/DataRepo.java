package com.tejas.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tejas.model.Data;

public interface DataRepo extends JpaRepository<Data, Integer>{
	
	

}
