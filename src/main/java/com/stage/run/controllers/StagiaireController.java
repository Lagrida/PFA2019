package com.stage.run.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured({"ROLE_ADMIN", "ROLE_SUPERADMIN","ROLE_STAGIAIRE"})
public class StagiaireController {
	@RequestMapping("/stagiaire")
	public String getStagiaire() {
		
		return "stagiaire";
	}
}
