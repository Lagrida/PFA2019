package com.stage.run.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
@Entity
public class Stage {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "demande_stage_id")
	private DemandeStage demandeStage;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stagiaire_id")
	private User stagiaire;
	private boolean closed = false;
	public Stage() {
		
	}
	public long getId() {
		return id;
	}
	
	public boolean isClosed() {
		return closed;
	}
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	public void setId(long id) {
		this.id = id;
	}
	public DemandeStage getDemandeStage() {
		return demandeStage;
	}
	public void setDemandeStage(DemandeStage demandeStage) {
		this.demandeStage = demandeStage;
	}
	public User getStagiaire() {
		return stagiaire;
	}
	public void setStagiaire(User stagiaire) {
		this.stagiaire = stagiaire;
	}
	
}
