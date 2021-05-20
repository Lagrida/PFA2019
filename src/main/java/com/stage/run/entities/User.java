package com.stage.run.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;


@Entity
@Table(name = "users")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private String username;
	private String password;
	@Transient
	private String insertedPassword;
	private String nom;
	private String prenom;
	private String image;
	private String description;
    
    /*@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "user_level", 
        joinColumns = { @JoinColumn(name = "user_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "level_id") }
    )*/
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Level> levels = new HashSet<Level>();

	public User() {
		
	}
	
	public String getInsertedPassword() {
		return insertedPassword;
	}

	public void setInsertedPassword(String insertedPassword) {
		this.insertedPassword = insertedPassword;
	}

	public Set<Level> getLevels() {
		return levels;
	}
	public void setLevels(Set<Level> levels) {
		this.levels = levels;
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public User(String username, String password, String nom, String prenom, String image, String description) {
		this.username = username;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
		this.image = image;
		this.description = description;
	}
	public User(User user) {
		this.id = user.getId();
		this.username= user.getUsername();
		this.insertedPassword = user.getPassword();
		this.nom = user.getNom();
		this.prenom = user.getPrenom();
		this.description = user.getDescription();
		this.image = user.getImage();
		this.insertedPassword = user.getInsertedPassword();
	}
	public User(long id, String username) {
		this.id = id;
		this.username = username;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
