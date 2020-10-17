package com.arthurlmf.desafiopetz.repository;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.arthurlmf.desafiopetz.model.Cliente;


@SpringBootTest
public class ClienteRepositoryTest  {
	
	@Autowired
	ClienteRepository repository;

	@Test
	public void testSaveCliente() {
		Cliente cl1 =  new Cliente(1, "Jose");
		Cliente cl2 =  new Cliente(2, "Maria");
		Cliente cl3 =  new Cliente(3, "Raul");
		
		
		repository.save(cl1);
		repository.save(cl2);
		
		List<Cliente> list = repository.findAll();
		
		assertTrue(list.contains(cl1));
		assertTrue(list.contains(cl2));
		assertFalse(list.contains(cl3));
	}

}
