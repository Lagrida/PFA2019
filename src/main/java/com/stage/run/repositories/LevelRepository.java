package com.stage.run.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stage.run.entities.Level;
import com.stage.run.entities.User;

public interface LevelRepository extends JpaRepository<Level, Long>{
	public Optional<Level> findByLevel(String titre);
}
