package com.stage.run.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "type_stages")
public class TypeStage implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	@Size(min=2, max=255)
	private String titre;
	
	public TypeStage() {

	}
	public TypeStage(String titre) {
		this.titre = titre;
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
	public void vider() {
		this.titre = "";
	}
}
