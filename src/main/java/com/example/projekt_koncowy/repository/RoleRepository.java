package com.example.projekt_koncowy.repository;

import com.example.projekt_koncowy.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
