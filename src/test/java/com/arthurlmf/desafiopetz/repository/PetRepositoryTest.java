package com.arthurlmf.desafiopetz.repository;



import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.arthurlmf.desafiopetz.model.Cliente;
import com.arthurlmf.desafiopetz.model.Pet;


@SpringBootTest
@Sql("/test-data.sql")
public class PetRepositoryTest  {
	
	@Autowired
	PetRepository repository;

	@Test
	public void testSavePet() {
		Cliente cl1 =  new Cliente(90, "Jose");
		Cliente cl2 =  new Cliente(91, "João");
		
		Pet pet1 = new Pet(9, "Lola", "Shitsu", cl1);
		Pet pet2 = new Pet(8, "Caue", "Pincher", cl1);
		Pet pet3 = new Pet(7, "Pacoca", "Fila", cl2);
		
		
		repository.save(pet1);
		repository.save(pet2);
		
		assertTrue(repository.findById(9).isPresent());
		
		List<Pet> list = repository.findAll();
		
		assertTrue(list.contains(pet1));
		assertTrue(list.contains(pet2));
		assertFalse(list.contains(pet3));
	}

}
