package com.stage.run.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "departements")
public class Departement {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	@Size(min=2, max=255)
	private String titre;
	private String description;
	private int nbrPlaces;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id")
	private User admin;
	
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Stage> stages = new HashSet<Stage>();
    
    
	public Set<Stage> getStages() {
		return stages;
	}
	public void setStages(Set<Stage> stages) {
		this.stages = stages;
	}
	public User getAdmin() {
		return admin;
	}
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	public Departement() {
		
	}
	public Departement(String titre, String description, int nbrPlaces) {
		this.titre = titre;
		this.description = description;
		this.nbrPlaces = nbrPlaces;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getNbrPlaces() {
		return nbrPlaces;
	}
	public void setNbrPlaces(int nbrPlaces) {
		this.nbrPlaces = nbrPlaces;
	}
}
