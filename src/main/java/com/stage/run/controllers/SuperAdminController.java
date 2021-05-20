package com.stage.run.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.stage.run.entities.Departement;
import com.stage.run.entities.Level;
import com.stage.run.entities.TypeStage;
import com.stage.run.entities.User;
import com.stage.run.repositories.DepartementRepository;
import com.stage.run.repositories.LevelRepository;
import com.stage.run.repositories.TypeStageRepository;
import com.stage.run.repositories.UserRepository;
import com.stage.run.security.MD5Encoder;

@Controller
@Secured("ROLE_SUPERADMIN")
public class SuperAdminController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DepartementRepository departementRepository;
	@Autowired
	private TypeStageRepository typeStageRepository;
	@Autowired
	private LevelRepository levelRepository;
	/*
	 * *********************************
	 * Acueil Administration
	 * *********************************
	 */
	@RequestMapping(value="/admin")
	public String getAdministration() {
		return "admin";
	}
	/*
	 * *********************************
	 * Configuration des Administrateurs
	 * *********************************
	 */
	@RequestMapping(value="/admin/users", method=RequestMethod.GET)
	public String users(Model model) {
		List<String> userErrors = new ArrayList<String>();
		List<User> myList = userRepository.findAll();
		model.addAttribute("user", new User());
		model.addAttribute("myList", myList);
		model.addAttribute("userErrors", userErrors);
		
		return "users";
	}
	@RequestMapping(value="/admin/users", method=RequestMethod.POST)
	public String users2(Model model,
			@Valid User user,
			BindingResult bindingResult,
			@RequestParam(value="type", defaultValue="") String type) {
		String inputErrorClass = "";
		List<String> userErrors = new ArrayList<String>();
		Set<Level> roles = new HashSet<Level>();
		boolean submit = false;
		List<User> myList;
		
		if(bindingResult.hasErrors()) {
			inputErrorClass = "inputError";
			model.addAttribute("displayBlockClass","displayBlock");
			myList = userRepository.findAll();
			model.addAttribute("myList", myList);
			if(bindingResult.getFieldErrorCount("username") >= 1) {
				userErrors.add("username");
			}
			if(bindingResult.getFieldErrorCount("password") >= 1) {
				userErrors.add("password");
			}
			model.addAttribute("inputError", inputErrorClass);
			model.addAttribute("userErrors", userErrors);
			return "users";
		}
		
		if(type.equals("insert")) {
			model.addAttribute("msgSucces","Membre bien ajouté..");
			try {
				user.setPassword(MD5Encoder.encode(user.getPassword()));
			}catch(NoSuchAlgorithmException ns) {}
			submit = true;
			Level lv = levelRepository.findByLevel("RESPONSABLE").orElse(null);
			roles.add(lv);
			user.setLevels(roles);
		}
		if(type.equals("modify")) {
			model.addAttribute("msgSucces","Membre bien modifié..");
			Set<Level> userRoles = userRepository.findById(user.getId()).orElse(null).getLevels();
			if(!user.getInsertedPassword().equals("")) {
				String pass = user.getInsertedPassword();
				try {
					user.setPassword(MD5Encoder.encode(pass));
				}catch(NoSuchAlgorithmException ns) {
				}
			}
			user.setLevels(userRoles);
			submit = true;
			
		}
		model.addAttribute("userErrors", userErrors);
		model.addAttribute("msgSuccesClass","msgsucces");
		model.addAttribute("submit", submit);
		
		userRepository.save(user);
		//System.out.println("gverger : "+userInserted.getId());
		
		myList = userRepository.findAll();
		model.addAttribute("myList", myList);
		return "users";
	}
	@RequestMapping(value="/admin/deleteUser", method=RequestMethod.GET)
	public String deleteUser(Long id) {
		System.out.println("sefvzefgerre:");
		userRepository.deleteById(id);
		return "redirect:users";
	}
	/*
	 * *********************************
	 * Configuration des Départements
	 * *********************************
	 */
	@RequestMapping(value="/admin/departements", method=RequestMethod.GET)
	public String departements(Model model) {
		List<Departement> myList = departementRepository.findAll();
		List<User> myUserList = userRepository.findAll();
		model.addAttribute("departement", new Departement());
		model.addAttribute("myList", myList);
		model.addAttribute("myUserList", myUserList);
		
		return "departements";
	}
	@RequestMapping(value="/admin/departements", method=RequestMethod.POST)
	public String departements2(Model model,
			@Valid Departement departement,
			BindingResult bindingResult,
			@RequestParam(value="type", defaultValue="") String type) {
		String inputErrorClass = "";
		List<Departement> myList;
		List<User> myUserList = userRepository.findAll();
		model.addAttribute("myUserList", myUserList);
		if(bindingResult.hasErrors()) {
			inputErrorClass = "inputError";
			model.addAttribute("displayBlockClass","displayBlock");
			myList = departementRepository.findAll();
			model.addAttribute("myList", myList);

			if(bindingResult.getFieldErrorCount("titre") == 1) {
				model.addAttribute("inputError", inputErrorClass);
			}
			return "departements";
		}
		departement.setAdmin(userRepository.findById(departement.getAdmin().getId()).orElse(null));
		departementRepository.save(departement);
		myList = departementRepository.findAll();
		model.addAttribute("myList", myList);
		if(type.equals("insert")) {
			model.addAttribute("msgSucces","Département bien ajouté..");
		}
		if(type.equals("modify")) {
			//User responsable = userRepository.findById(departement.getAdmin().getId()).orElse(null);
			//departement.setAdmin(responsable);
			model.addAttribute("msgSucces","Département bien modifié..");
		}
		
		model.addAttribute("msgSuccesClass","msgsucces");
		
		return "departements";
	}
	@RequestMapping(value="/admin/deleteDepartement", method=RequestMethod.GET)
	public String deleteDepartement(Long id) {
		departementRepository.deleteById(id);
		return "redirect:departements";
	}
	/*
	 * *********************************
	 * Configuration des Type de Stages
	 * *********************************
	 */
	@RequestMapping(value="/admin/typestages", method=RequestMethod.GET)
	public String typestages(Model model) {
		List<TypeStage> myList = typeStageRepository.findAll();
		model.addAttribute("typeStage", new TypeStage());
		model.addAttribute("myList", myList);
		
		return "typestages";
	}
	@RequestMapping(value="/admin/typestages", method=RequestMethod.POST)
	public String typestages2(Model model,
			@Valid TypeStage typeStage,
			BindingResult bindingResult,
			@RequestParam(value="type", defaultValue="") String type) {
		String inputErrorClass = "";
		boolean submit = false;
		List<TypeStage> myList;
		if(bindingResult.hasErrors()) {
			inputErrorClass = "inputError";
			model.addAttribute("displayBlockClass","displayBlock");
			myList = typeStageRepository.findAll();
			model.addAttribute("myList", myList);
			if(bindingResult.getFieldErrorCount("titre") == 1) {
				model.addAttribute("inputError", inputErrorClass);
			}
			return "typestages";
		}
		typeStageRepository.saveAndFlush(typeStage);
		if(type.equals("insert")) {
			model.addAttribute("msgSucces","Type de stage bien ajouté..");
			submit = true;
		}
		if(type.equals("modify")) {
			model.addAttribute("msgSucces","Type de stage bien modifié..");
		}
		model.addAttribute("msgSuccesClass","msgsucces");
		model.addAttribute("submit", submit);
		
		myList = typeStageRepository.findAll();
		model.addAttribute("myList", myList);
		return "typestages";
	}
	@RequestMapping(value="/admin/deleteTypeStage", method=RequestMethod.GET)
	public String deleteTypeStage(long id) {
		typeStageRepository.deleteById(id);
		return "redirect:typestages";
	}
}