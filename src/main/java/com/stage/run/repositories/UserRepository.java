package com.stage.run.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stage.run.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	@Query("select user from User user, IN (user.levels) alevel where alevel.level='RESPONSABLE'")
	public List<User> findAll();
	public Optional<User> findByUsername(String username);
	public Optional<User> findById(Long id);
	@Query("select Count(user.id) from User user, IN (user.levels) alevel where user.username=?1 and alevel.level='STAGIAIRE'")
	public int isStagiaire(String username);
}
