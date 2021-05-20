package com.stage.run.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "demande_stages")
public class DemandeStage {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String nom;
	private String prenom;
	@Column(unique = true)
	private String email;
	private boolean sexe;
	private String telephone;
	private String etablissement;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id")
    private Departement departement;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeStage_id")
    private TypeStage typeStage;
	private float duree;
	private Date date_debut;
	private boolean closed = false;
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
	private AppFile cv;
	public DemandeStage() {
		
	}
	public DemandeStage(String nom, String prenom, String email, boolean sexe, String telephone, String etablissement,
			Departement departement, TypeStage typeStage, float duree, Date date_debut, boolean closed) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.sexe = sexe;
		this.telephone = telephone;
		this.etablissement = etablissement;
		this.departement = departement;
		this.typeStage = typeStage;
		this.duree = duree;
		this.date_debut = date_debut;
		this.closed = closed;
	}
	
	public AppFile getCv() {
		return cv;
	}
	public void setCv(AppFile cv) {
		this.cv = cv;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isSexe() {
		return sexe;
	}
	public void setSexe(boolean sexe) {
		this.sexe = sexe;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEtablissement() {
		return etablissement;
	}
	public void setEtablissement(String etablissement) {
		this.etablissement = etablissement;
	}
	public Departement getDepartement() {
		return departement;
	}
	public void setDepartement(Departement departement) {
		this.departement = departement;
	}
	public TypeStage getTypeStage() {
		return typeStage;
	}
	public void setTypeStage(TypeStage typeStage) {
		this.typeStage = typeStage;
	}
	public float getDuree() {
		return duree;
	}
	public void setDuree(float duree) {
		this.duree = duree;
	}
	public Date getDate_debut() {
		return date_debut;
	}
	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}
	public boolean isClosed() {
		return closed;
	}
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
}
