package com.stage.run.controllers;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.stage.run.entities.AppFile;
import com.stage.run.entities.DemandeStage;
import com.stage.run.entities.Departement;
import com.stage.run.entities.TypeStage;
import com.stage.run.entities.User;
import com.stage.run.files.UploadFile;
import com.stage.run.repositories.AppFileRepository;
import com.stage.run.repositories.DemandeStageRepository;
import com.stage.run.repositories.DepartementRepository;
import com.stage.run.repositories.TypeStageRepository;
import com.stage.run.repositories.UserRepository;

@Controller
public class WebController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DepartementRepository departementRepository;
	@Autowired
	private TypeStageRepository typeStageRepository;
	@Autowired
	private DemandeStageRepository demandeStageRepository;
	@Autowired
	private AppFileRepository appFileRepository;
	@RequestMapping(value="/")
	public String getIndex(Model model) {
		
		return "index";
	}
	@RequestMapping(value="/Responsable/{num}")
	public String getAdministrateur(Model model, @PathVariable long num) {
		User admin = userRepository.findById(num).orElse(new User());
		model.addAttribute("admin", admin);
		return "administrateur";
	}
	@RequestMapping(value="/demande", method=RequestMethod.GET)
	public String getDemande(Model model) {
		List<Departement> departements = departementRepository.findAll();
		model.addAttribute("departements", departements);
		List<TypeStage> typestages = typeStageRepository.findAll();
		model.addAttribute("typestages", typestages);
		return "demande";
	}
	@RequestMapping(value="/demandeEnvoi", method=RequestMethod.POST)
	public String getDemande2(Model model, DemandeStage demandeStage, @RequestParam("file") MultipartFile file) {
		long admin_id = 1;
		AppFile cv = new AppFile();
		User owner = userRepository.findById(admin_id).orElse(null);
		/*
		 * 
		 * Upload Cv File
		 */
		UploadFile up = new UploadFile();
		String fileName = file.getOriginalFilename();
		String fileExtension = up.getExtension(fileName);
		
		if(up.checkExtension(fileExtension)) {
			String newFileName = UploadFile.generateRandomText();
			cv.setFileExtension(fileExtension);
			cv.setDateUpload(new Date(Calendar.getInstance().getTime().getTime()));
			cv.setUser(owner);
			cv.setFileDirectory(UploadFile.webUploadDir);
			cv.setDescription("CV");
			appFileRepository.save(cv);
			newFileName = cv.getId()+"-"+newFileName;
			up.uploadTheFile(file, newFileName, fileExtension);
			cv.setFileName(newFileName);
			appFileRepository.save(cv);
		}
		demandeStage.setCv(cv);
			// Fin
		demandeStageRepository.save(demandeStage);
		return "demandeEnvoi";
	}
}