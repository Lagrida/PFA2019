package com.stage.run.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.stage.run.files.UploadFile;


@Entity
@Table(name = "files")
public class AppFile {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String fileName;
	private String fileDirectory;
	private String fileExtension;
	private String description;
	private Date dateUpload;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	private boolean deleted = false;
	
	public AppFile() {
		
	}
	public AppFile(String fileName, String fileDirectory, String fileExtension) {
		this.fileName = fileName;
		this.fileDirectory = fileDirectory;
		this.fileExtension = fileExtension;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDateUpload() {
		return dateUpload;
	}
	public void setDateUpload(Date dateUpload) {
		this.dateUpload = dateUpload;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDirectory() {
		return fileDirectory;
	}
	public void setFileDirectory(String fileDirectory) {
		this.fileDirectory = fileDirectory;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isImg() {
		for(String extention : UploadFile.allowedImgExtensions) {
			if(this.fileExtension.equals(extention)) {
				return true;
			}
		}
		return false;
	}
}
