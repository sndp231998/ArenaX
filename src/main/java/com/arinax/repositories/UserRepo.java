package com.arinax.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arinax.entities.User;




public interface UserRepo extends JpaRepository<User, Integer>{
		
	//Optional<User> findByMobileNo(String mobileNo);	
	Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
	List<User> findAllByRoleName(@Param("roleName") String roleName);
	
	boolean existsByURemark(String uRemark);

}
