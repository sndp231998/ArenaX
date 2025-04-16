package com.arinax.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arinax.entities.Category;



public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
