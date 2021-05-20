package com.stage.run.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stage.run.entities.AppFile;

public interface AppFileRepository extends JpaRepository<AppFile, Long>{
	@Query("select file from AppFile file where file.user.username=?1 and deleted=false and file.description like %?2% order by file.id desc")
    Page<AppFile> finAllFilesByUser(String username, String recherche, Pageable pageable);
}