package com.stage.run.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stage.run.entities.Departement;

public interface DepartementRepository extends JpaRepository<Departement, Long>{
	
}