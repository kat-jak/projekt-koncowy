package com.example.projekt_koncowy;

import com.example.projekt_koncowy.model.Role;
import com.example.projekt_koncowy.repository.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjektKoncowyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjektKoncowyApplication.class, args);
	}
	@Bean
	public CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("ROLE_USER") == null) {
				Role userRole = new Role();
				userRole.setName("ROLE_USER");
				roleRepository.save(userRole);
			}

			if (roleRepository.findByName("ROLE_ADMIN") == null) {
				Role adminRole = new Role();
				adminRole.setName("ROLE_ADMIN");
				roleRepository.save(adminRole);
			}
		};
	}



}


