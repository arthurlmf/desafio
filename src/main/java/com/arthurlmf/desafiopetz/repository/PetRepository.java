package com.arthurlmf.desafiopetz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arthurlmf.desafiopetz.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
	

}
