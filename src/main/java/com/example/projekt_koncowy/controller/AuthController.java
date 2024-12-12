package com.example.projekt_koncowy.controller;

import com.example.projekt_koncowy.model.Role;
import com.example.projekt_koncowy.model.User;
import com.example.projekt_koncowy.repository.RoleRepository;
import com.example.projekt_koncowy.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            throw new IllegalStateException("Role ROLE_USER not found in database. Please ensure the role exists.");
        }

        user.setRoles(Set.of(role)); // Dodaj rolę do użytkownika
        userRepository.save(user);
        return "redirect:/login"; // Przekierowanie na stronę logowania
    }



    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}
