package com.arthurlmf.desafiopetz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.arthurlmf.desafiopetz.exception.DesafioPetzException;
import com.arthurlmf.desafiopetz.model.Cliente;
import com.arthurlmf.desafiopetz.model.Pet;
import com.arthurlmf.desafiopetz.repository.PetRepository;


@Service
public class PetService {
	
	@Autowired
	PetRepository repository;
	
	
	public Pet createPet(Integer id, String nome, String raca, Cliente dono) throws DesafioPetzException {
		if(id != null && nome != null &&  raca != null && dono != null) {
			try {
				return repository.save(new Pet(id, nome, raca, dono));
			} catch (JpaObjectRetrievalFailureException e) {
				 throw new DesafioPetzException("Impossivel criar Pet: cliente inexistente" );
			}
		}
			
		else throw new DesafioPetzException("Impossivel criar Pet: parametros inválidos" );
	}
	
	public void deletePet(Integer id) throws DesafioPetzException {
		if(id != null) {
			try {
				repository.deleteById(id);
			} catch (EmptyResultDataAccessException e) {
				throw new DesafioPetzException("Impossivel remover Pet: id inexistente" );
			}
		}
		else throw new DesafioPetzException("Impossivel remover Pet: parametros inválidos" );
	}
	
	public Pet findPet(Integer id) {
		Optional<Pet> findById = repository.findById(id);
		if(findById.isPresent()) {
			return findById.get();
		} else
			return null;
	}
	
	public void updatePet(Integer id, String nome, String raca, Cliente dono) throws DesafioPetzException {
		Optional<Pet> findById = repository.findById(id);
		if(findById.isPresent()) {
			Pet pet = findById.get();
			pet.setNome(nome);
			pet.setRaca(raca);
			pet.setDono(dono);
			repository.saveAndFlush(pet);
		} else throw new DesafioPetzException("Impossivel atualizar Pet: id inexistente" );
	}

}
