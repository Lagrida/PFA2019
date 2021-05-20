package com.stage.run;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.stage.run.entities.Level;
import com.stage.run.entities.User;
import com.stage.run.repositories.LevelRepository;
import com.stage.run.repositories.UserRepository;
import com.stage.run.security.MD5Encoder;

@SpringBootApplication
public class PfaProjectApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(PfaProjectApplication.class, args);
		
		//commenter cela aprés installation
		/*UserRepository userRepository = context.getBean(UserRepository.class);
		LevelRepository levelRepository = context.getBean(LevelRepository.class);
		HashSet<Level> roles = new HashSet<Level>();
		Level lv = new Level("SUPERADMIN");
		Level lv2 = new Level("RESPONSABLE");
		Level lv3 = new Level("STAGIAIRE");
		levelRepository.save(lv);
		levelRepository.save(lv2);
		levelRepository.save(lv3);
		roles.add(lv);
		roles.add(lv2);
		try {
			User user = new User("LAGRIDA", MD5Encoder.encode("123456"), "LAGRIDA", "Yassine", "", "evfvezq");
			user.setLevels(roles);
			userRepository.save(user);
		}catch(NoSuchAlgorithmException ex) {}
		*/
		
	}
}
