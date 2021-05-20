package com.stage.run.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stage.run.entities.Level;
import com.stage.run.entities.User;
import com.stage.run.repositories.DemandeStageRepository;
import com.stage.run.repositories.UserRepository;
import com.stage.run.security.UserDetailsImpl;

@ControllerAdvice
public class GlobalController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DemandeStageRepository demandeStageRepository;
	
	@ModelAttribute("userConnected")
	public User getUserConnected(RedirectAttributes redirectAttributes) {
		User userConnected;
		String username = "";
		Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = "";
		}
		userConnected = userRepository.findByUsername(username).orElse(new User(0, "visiteur"));
		redirectAttributes.addFlashAttribute("userConnected", userConnected);
		return userConnected;
	}
	
	@ModelAttribute("demandesCount")
	public int getDemandesCount() {
		String username = "";
		int result = 0;
		
		Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = "";
		}
		User userConnected = userRepository.findByUsername(username).orElse(new User(0, "visiteur"));
		for(Level level : userConnected.getLevels()) {
			if(level.getLevel().equals("SUPERADMIN")) {
				result = demandeStageRepository.demandesAllCount();
				return result;
			}
		}
		for(Level level : userConnected.getLevels()) {
			if(level.getLevel().equals("RESPONSABLE")) {
				result = demandeStageRepository.demandesCount(userConnected.getId());
				return result;
			}
		}
		return 0;
	}
	@ModelAttribute("isStagiaire")
	public boolean isStagiaire() {
		String username = "";
		Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = "";
			return true;
		}
		int count = userRepository.isStagiaire(username);
		if(count > 0)
			return true;
		return false;
	}
}