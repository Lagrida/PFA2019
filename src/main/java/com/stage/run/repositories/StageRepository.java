package com.stage.run.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stage.run.entities.Stage;

public interface StageRepository extends JpaRepository<Stage, Long>{
	@Query("select s from Stage s where s.closed=false")
	Page<Stage> finAllStages(Pageable pageable);
	@Query("select s from Stage s where s.demandeStage.departement.admin.username=?1 and s.closed=false")
	Page<Stage> finAllByAdmin(String username, Pageable pageable);
}