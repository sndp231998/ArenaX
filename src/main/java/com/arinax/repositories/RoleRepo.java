package com.arinax.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arinax.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{
	Optional<Role> findByName(String name);
}
