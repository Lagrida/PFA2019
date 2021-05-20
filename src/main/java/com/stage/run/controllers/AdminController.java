package com.stage.run.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.stage.run.entities.AppFile;
import com.stage.run.entities.DemandeStage;
import com.stage.run.entities.Departement;
import com.stage.run.entities.Level;
import com.stage.run.entities.Stage;
import com.stage.run.entities.User;
import com.stage.run.files.UploadFile;
import com.stage.run.repositories.DemandeStageRepository;
import com.stage.run.repositories.LevelRepository;
import com.stage.run.repositories.StageRepository;
import com.stage.run.repositories.UserRepository;
import com.stage.run.security.MD5Encoder;
import com.stage.run.security.UserDetailsImpl;

@Controller
public class AdminController {
	@Autowired
	private DemandeStageRepository demandeStageRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StageRepository stageRepository;
	@Autowired
    public JavaMailSender emailSender;
	@Autowired
	public LevelRepository levelRepository;
	@Secured({"ROLE_RESPONSABLE", "ROLE_SUPERADMIN"})
	@RequestMapping("/responsable")
	public String getResponsableAcceuil() {
		
		return "responsable";
	}
	@Secured({"ROLE_RESPONSABLE", "ROLE_SUPERADMIN"})
	@RequestMapping("/responsable/gestionDemandes")
	public String getDemands(Model model, @ModelAttribute("userConnected") User userConnected) {
		List<DemandeStage> myList;
		GrantedAuthority role = new SimpleGrantedAuthority("ROLE_SUPERADMIN");
		UserDetailsImpl principal =  (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal.getAuthorities().contains(role)) {
			myList = demandeStageRepository.findAll();
		}else {
			myList = demandeStageRepository.finAllByAdmin(userConnected.getId());
		}
		model.addAttribute("myList", myList);
		return "getDemands";
	}
	@Secured({"ROLE_RESPONSABLE", "ROLE_SUPERADMIN"})
	@RequestMapping(value="/responsable/actionDemande", method=RequestMethod.GET)
	public String actionDemande(Model model,
				@RequestParam(value="type", defaultValue="") String type,
				@RequestParam(value="id", defaultValue="") long id) {
		DemandeStage demandStage = demandeStageRepository.findById(id).orElse(null);
		Departement departement = demandStage.getDepartement();
		UserDetailsImpl principal =  (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		GrantedAuthority role = new SimpleGrantedAuthority("ROLE_SUPERADMIN");
		User userConnected = userRepository.findByUsername(principal.getUsername()).orElse(null);
		if(principal.getAuthorities().contains(role) || departement.getAdmin().getId() == userConnected.getId()) {
			if(type.equals("refuser")) {
				demandStage.setClosed(true);
				demandeStageRepository.save(demandStage);
				String messageText = "Bonjour "+demandStage.getNom()
				+"\n Votre demande de stage est refusé.";
				/*SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(demandStage.getEmail());
				message.setSubject("Refus de demande de stage");
				message.setText(messageText);
				emailSender.send(message);*/
			}
			if(type.equals("approuver")) {
				demandStage.setClosed(true);
				String password = UploadFile.generateRandomText();
				String passwordCripted = "";
				try {
					passwordCripted = MD5Encoder.encode(password);
				}catch(NoSuchAlgorithmException ex) {}
				User stagiaire = new User(demandStage.getEmail(), passwordCripted, demandStage.getNom(), demandStage.getPrenom(), "", "");
				System.out.println(stagiaire.getUsername());
				Stage stage = new Stage();
				stage.setDemandeStage(demandStage);
				
				String messageText = "Bonjour "+demandStage.getNom()
				+"\n Votre demande est accepté.\n Votre nformation d'authentification :\n"
				+"login : "+demandStage.getEmail()+"\n"
				+"password : "+password+"\n";
				/*SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(demandStage.getEmail());
				message.setSubject("Aprobation de demande de stage");
				message.setText(messageText);
				emailSender.send(message);*/
				
				
				
				Set<Level> roles = new HashSet<Level>();
				Level lv = levelRepository.findByLevel("STAGIAIRE").orElse(null);
				roles.add(lv);
				stagiaire.setLevels(roles);
				userRepository.save(stagiaire);
				demandStage.getCv().setUser(stagiaire);
				demandeStageRepository.save(demandStage);
				
				stage.setStagiaire(stagiaire);
				stageRepository.save(stage);

			}
		}
		return"redirect:gestionDemandes";
	}
	@Secured({"ROLE_RESPONSABLE", "ROLE_SUPERADMIN"})
	@RequestMapping(value="/responsable/stages", method=RequestMethod.GET)
	public String stages(Model model, @RequestParam(name="page", defaultValue="1") int page) {
		int size = 10;
		page--;
		
		Page<Stage> myList;
		GrantedAuthority role = new SimpleGrantedAuthority("ROLE_SUPERADMIN");
		UserDetailsImpl principal =  (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal.getAuthorities().contains(role)) {
			myList = stageRepository.finAllStages(PageRequest.of(page, size));
		}else {
			myList = stageRepository.finAllByAdmin(principal.getUsername(), PageRequest.of(page, size));
		}
		model.addAttribute("myList", myList.getContent());
		model.addAttribute("page", page);
		model.addAttribute("totalPages", myList.getTotalPages());
		return"getStages";
	}
}