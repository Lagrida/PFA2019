package com.stage.run.controllers;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.stage.run.entities.AppFile;
import com.stage.run.entities.DemandeStage;
import com.stage.run.files.UploadFile;
import com.stage.run.repositories.AppFileRepository;
import com.stage.run.repositories.UserRepository;

@Controller
@Secured({"ROLE_ADMIN", "ROLE_SUPERADMIN","ROLE_STAGIAIRE"})
public class UploadController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AppFileRepository appFileRepository;
	
	@RequestMapping(value="/uploadFiles")
	public String uploadFiles(Model model,
				@RequestParam(name="page", defaultValue="1") int page,
				@RequestParam(name="recherche", defaultValue="") String recherche) {
		int size = 5;
		page--;
		Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		Page<AppFile> myList = appFileRepository.finAllFilesByUser(username, recherche, PageRequest.of(page, size));
		model.addAttribute("myFile", new AppFile());
		model.addAttribute("myList", myList.getContent());
		model.addAttribute("totalPages", myList.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recherche", recherche);
		
		return"uploadFile";
	}
	@RequestMapping(value="/uploadFiles", method=RequestMethod.POST)
	public String uploadFiles2(Model model, AppFile myFile, @RequestParam("file") MultipartFile file) {
		UploadFile up = new UploadFile();
		String fileName = file.getOriginalFilename();
		String fileExtension = up.getExtension(fileName);
		
		Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		
		if(myFile.getDescription().equals("") || myFile.getDescription() == null) {
			myFile.setDescription(up.getOriginalName(fileName));
		}
		if(up.checkExtension(fileExtension)) {
			String newFileName = up.generateRandomText();
			myFile.setFileExtension(fileExtension);
			myFile.setDateUpload(new Date(Calendar.getInstance().getTime().getTime()));
			myFile.setUser(userRepository.findByUsername(username).orElse(null));
			myFile.setFileDirectory(UploadFile.webUploadDir);
			
			appFileRepository.save(myFile);
			newFileName = myFile.getId()+"-"+newFileName;
			up.uploadTheFile(file, newFileName, fileExtension);
			myFile.setFileName(newFileName);
			appFileRepository.save(myFile);
		}
		
		return"redirect:uploadFiles";
	}
	@RequestMapping(value="/deleteFile", method=RequestMethod.GET)
	public String deleteFile(@RequestParam(value="id", defaultValue="") long id) {
		AppFile file = appFileRepository.findById(id).orElse(null);
		String usernameOwner = file.getUser().getUsername();
		UserDetails userDaitails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(usernameOwner.equals(userDaitails.getUsername())) {
			file.setDeleted(true);
			appFileRepository.save(file);
		}
		return "redirect:uploadFiles";
	}
}