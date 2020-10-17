package com.arthurlmf.desafiopetz.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.arthurlmf.desafiopetz.exception.DesafioPetzException;
import com.arthurlmf.desafiopetz.model.Cliente;
import com.arthurlmf.desafiopetz.model.Pet;


@SpringBootTest
@Sql("/test-data.sql")
public class PetServiceTest  {
	
	@Autowired
	PetService service;
	
	@Test
	public void testCreatePet_comSucesso() {
		
		try {
			Pet pet = service.createPet(1, "Xiu", "Dog alemao",  new Cliente(90,"Jose"));
			assertNotNull(pet);
		} catch (DesafioPetzException e1) {
			fail();
		}
	}
	
	@Test
	public void testCreatePet_erroClienteNaoEncontrado() {
		
		try {
			Pet pet = service.createPet(1, "Xiu", "Dog alemao",  new Cliente(1,"Jose"));
			fail();
		} catch (DesafioPetzException e) {
			assertEquals("Impossivel criar Pet: cliente inexistente" , e.getMessage());
		}
	}
	
	@Test
	public void testCreatePet_erroParametrosInvalidos() {
		
		try {
			Pet pet = service.createPet(null, "Xiu", "Dog alemao",  new Cliente(90,"Jose"));
			fail();
		} catch (DesafioPetzException e) {
			assertEquals("Impossivel criar Pet: parametros inválidos" , e.getMessage());
		}
		
		try {
			Pet pet = service.createPet(1, null, "Dog alemao",  new Cliente(90,"Jose"));
			fail();
		} catch (DesafioPetzException e) {
			assertEquals("Impossivel criar Pet: parametros inválidos" , e.getMessage());
		}
		
		try {
			Pet pet = service.createPet(1, "Xiu", null,  new Cliente(90,"Jose"));
			fail();
		} catch (DesafioPetzException e) {
			assertEquals("Impossivel criar Pet: parametros inválidos" , e.getMessage());
		}
		
		try {
			Pet pet = service.createPet(1, "Xiu", "Dog",  null);
			fail();
		} catch (DesafioPetzException e) {
			assertEquals("Impossivel criar Pet: parametros inválidos" , e.getMessage());
		}
	}
	
	@Test
	public void testDeletePet_comSucesso() {
		
		try {
			service.deletePet(1);
		} catch (DesafioPetzException e) {
			fail();
		}
		
		assertNull(service.findPet(1));
	}
	
	@Test
	public void testdeleteCliente_erro_idClienteInexistente() {
		
		try {
			service.deletePet(100);
			fail();
		} catch (DesafioPetzException e) {
			assertEquals("Impossivel remover Pet: id inexistente", e.getMessage());
		}
	}
}
