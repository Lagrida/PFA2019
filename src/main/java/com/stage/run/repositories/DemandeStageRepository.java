package com.stage.run.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stage.run.entities.DemandeStage;

public interface DemandeStageRepository extends JpaRepository<DemandeStage, Long>{
	@Query("select ds from DemandeStage ds where ds.closed=false")
    List<DemandeStage> findAll();
	@Query("select ds from DemandeStage ds where ds.departement.admin.id=?1 and ds.closed=false")
    List<DemandeStage> finAllByAdmin(long admin_id);
	@Query("select COUNT(ds.id) from DemandeStage ds where ds.departement.admin.id=:id and ds.closed=false")
	int demandesCount(@Param("id") long admin_id);
	@Query("select COUNT(ds.id) from DemandeStage ds where ds.closed=false")
	int demandesAllCount();
}