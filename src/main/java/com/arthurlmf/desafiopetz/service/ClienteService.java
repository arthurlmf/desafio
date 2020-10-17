package com.arthurlmf.desafiopetz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.arthurlmf.desafiopetz.exception.DesafioPetzException;
import com.arthurlmf.desafiopetz.model.Cliente;
import com.arthurlmf.desafiopetz.repository.ClienteRepository;


@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository repository;
	
	
	public Cliente createCliente(Integer id, String nome) throws DesafioPetzException {
		if(id != null && nome != null)
			return repository.save(new Cliente(nome));
		else throw new DesafioPetzException("Impossivel criar cliente: parametros inválidos" );
	}
	
	public void deleteCliente(Integer id) throws DesafioPetzException {
		if(id != null) {
			try {
				repository.deleteById(id);
			} catch (EmptyResultDataAccessException e) {
				throw new DesafioPetzException("Impossivel remover cliente: id do cliente inexistente" );
			}  
		}
		else throw new DesafioPetzException("Impossivel remover cliente: parametros inválidos" );
	}
	
	public Cliente findCliente(Integer id) {
		Optional<Cliente> findById = repository.findById(id);
		if(findById.isPresent()) {
			return findById.get();
		} else
			return null;
	}
	
	public void updateCliente(Integer id, String nome) throws DesafioPetzException {
		Optional<Cliente> findById = repository.findById(id);
		if(findById.isPresent()) {
			Cliente cliente = findById.get();
			cliente.setNome(nome);
			repository.saveAndFlush(cliente);
		} else throw new DesafioPetzException("Impossivel atualizar cliente: id do cliente inexistente" );
	}

}
