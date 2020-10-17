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


@SpringBootTest
@Sql("/test-data.sql")
public class ClienteServiceTest  {
	
	@Autowired
	ClienteService service;
	

	@Test
	public void testCreateCliente_comSucesso() {
		
		try {
			Cliente cliente = service.createCliente(1, "Jose");
			assertNotNull(cliente);
		} catch (DesafioPetzException e1) {
			fail();
		}
	}
	
	@Test
	public void testCreateCliente_erro_parametros_invalidos() {
		
		try {
			service.createCliente(null, "Jose");
			fail();
		} catch (DesafioPetzException e) {
			assertEquals("Impossivel criar cliente: parametros inválidos", e.getMessage());
		}
		
		try {
			service.createCliente(1, null);
			fail();
		} catch (DesafioPetzException e) {
			assertEquals("Impossivel criar cliente: parametros inválidos", e.getMessage());
		}
	}
	
	@Test
	public void testDeleteCliente_comSucesso_e_FindCLiente() {
		
		try {
			service.deleteCliente(93);
		} catch (DesafioPetzException e) {
			fail();
		}
		assertNull(service.findCliente(93));
	}
	
	
	@Test
	public void testFindClient_comSucesso() {
		
		assertNotNull(service.findCliente(90));
	}
	
	
	@Test
	public void testDeleteCliente_usuarioIdInexistente() {
		
		try {
			service.deleteCliente(95);
		} catch (DesafioPetzException e) {
			assertEquals("Impossivel remover cliente: id do cliente inexistente", e.getMessage());
		}
		
	}
	
	@Test
	public void testUpdateCliente_comSucesso() {
		
		try {
			Cliente found = service.findCliente(90);
			assertNotNull(found);
			assertEquals("Jose", found.getNome());
			
			service.updateCliente(90, "Maria");
			
			found = service.findCliente(90);
			assertNotNull(found);
			assertEquals("Maria", found.getNome());
		} catch (DesafioPetzException e1) {
			fail();
		}
	}
	
	@Test
	public void testUpdateCliente_semSucesso_idInexistente() {
		
		try {
			service.updateCliente(1, "Maria");
			fail();
		} catch (DesafioPetzException e) {
			assertEquals("Impossivel atualizar cliente: id do cliente inexistente", e.getMessage());
		}
	}
	
	
}
