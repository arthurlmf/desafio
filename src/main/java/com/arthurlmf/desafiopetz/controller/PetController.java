package com.arthurlmf.desafiopetz.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arthurlmf.desafiopetz.exception.DesafioPetzException;
import com.arthurlmf.desafiopetz.model.Cliente;
import com.arthurlmf.desafiopetz.model.Pet;
import com.arthurlmf.desafiopetz.service.ClienteService;
import com.arthurlmf.desafiopetz.service.PetService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class PetController {

	static final Logger logger = LoggerFactory.getLogger(PetController.class);

	@Autowired
	PetService service;
	
	@Autowired
	ClienteService clienteService;

	@ApiOperation(value = "Cria um novo pet", notes = "Este endpoint cria um novo pet")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna o pet criado"),
			@ApiResponse(code = 422, message = "Erro ao criar pet") })
	@PostMapping("/pet/v1.0/criar/")
	public ResponseEntity<String> createPet(
			@ApiParam(name = "id", type = "Integer", value = "A identificação do pet", example = "99", required = true) @RequestParam(value = "id") Integer id,
			@ApiParam(name = "nome", type = "String", value = "O nome do pet em string", example = "Jose", required = true) @RequestParam(value = "nome") String nome,
			@ApiParam(name = "raca", type = "String", value = "A Raça do pet em string", example = "Poodle", required = true) @RequestParam(value = "raca") String raca,
			@ApiParam(name = "id_cliente", type = "Integer", value = "O id do cliente", example = "33", required = true) @RequestParam(value = "id_cliente") Integer id_cliente)
{
		try {
			Cliente cliente = clienteService.findCliente(id_cliente);
			if( cliente == null) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Cliente não encontrado");
			}
			Pet createdPet = service.createPet(id, nome, raca, cliente);
			if (createdPet != null) {
				return ResponseEntity.status(201).body(createdPet.toString());
			}
		} catch (DesafioPetzException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
		return ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value = "Atualizar um pet", notes = "Este endpoint atualiza pet")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Pet atualizado com sucesso"),
			@ApiResponse(code = 422, message = "Erro ao atualizar pet") })
	@PutMapping("/pet/v1.0/atualizar/")
	public ResponseEntity<String> updatePet(
			@ApiParam(name = "id", type = "Integer", value = "A identificação do pet", example = "99", required = true) @RequestParam(value = "id") Integer id,
			@ApiParam(name = "nome", type = "String", value = "O nome do pet em string", example = "Jose", required = true) @RequestParam(value = "nome") String nome,
			@ApiParam(name = "raca", type = "String", value = "A Raça do pet em string", example = "Poodle", required = true) @RequestParam(value = "raca") String raca,
			@ApiParam(name = "id_cliente", type = "Integer", value = "O id do cliente", example = "33", required = true) @RequestParam(value = "id_cliente") Integer id_cliente){
		try {
			Cliente cliente = clienteService.findCliente(id_cliente);
			if( cliente == null) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Cliente não encontrado");
			}
			service.updatePet(id, nome, raca, cliente);
			return ResponseEntity.noContent().build();
		} catch (DesafioPetzException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
	}

	@ApiOperation(value = "Deleta um pet", notes = "Este endpoint deleta um pet")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Confirmacao do delete"),
			@ApiResponse(code = 204, message = "Pet não encontrado"),
			@ApiResponse(code = 422, message = "Erro ao deletar pet") })
	@DeleteMapping("/pet/v1.0/deletar/{id}")
	public ResponseEntity<Object> deletePet(
			@ApiParam(name = "id",
					  type = "Integer", 
					  value = "A identificação do pet", 
					  example = "99", 
					  required = true) 
			@PathVariable(value = "id") Integer id){
		try {
			service.deletePet(id);
			logger.info(String.format("Pet %s deletado com sucesso", id));
			return ResponseEntity.ok().build();
		} catch (DesafioPetzException e) {
			logger.error(e.getMessage());
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
	}

	@ApiOperation(value = "Retorna o pet", notes = "Este endpoint consulta o pet")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna o pet"),
			@ApiResponse(code = 204, message = "Pet não encontrado"),
			@ApiResponse(code = 422, message = "Erro ao consultar pet") })
	@GetMapping("/pet/v1.0/consultar/{id}")
	public ResponseEntity<String> findPet(
			@ApiParam(name = "id", type = "Integer", value = "A identificação do usuário", example = "99", required = true) @PathVariable Integer id) {
		try {
			Pet pet = service.findPet(id);
			if (pet != null) {
				logger.info("pet encontrado " + pet.toString());
				return ResponseEntity.ok(pet.toString());
			}
		} catch (Exception e) {
			logger.error("Erro: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
		logger.info(String.format("pet id %s nao encontrado", id));
		return ResponseEntity.noContent().build();
	}
}
