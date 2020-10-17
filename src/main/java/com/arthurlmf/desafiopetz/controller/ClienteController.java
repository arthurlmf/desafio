package com.arthurlmf.desafiopetz.controller;

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
import com.arthurlmf.desafiopetz.service.ClienteService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class ClienteController {

	static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	ClienteService service;

	@ApiOperation(value = "Cria um novo cliente", notes = "Este endpoint cria um novo cliente")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna o cliente criado"),
			@ApiResponse(code = 422, message = "Erro ao criar cliente") })
	@PostMapping("/cliente/v1.0/criar/")
	public ResponseEntity<String> createCliente(
			@ApiParam(name = "id", type = "Integer", value = "A identificação do cliente", example = "99", required = true) @RequestParam(value = "id") Integer id,
			@ApiParam(name = "nome", type = "String", value = "O nome do cliente em string", example = "Jose", required = true) @RequestParam(value = "nome") String nome) {
		try {
			Cliente createCliente = service.createCliente(id, nome);
			if (createCliente != null) {
				return ResponseEntity.status(201).body(createCliente.toString());
			}
		} catch (DesafioPetzException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
		return ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value = "Atualiza um cliente", notes = "Este endpoint atualiza cliente")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Cliente atualizado com sucesso"),
			@ApiResponse(code = 422, message = "Erro ao atualizar cliente") })
	@PutMapping("/cliente/v1.0/atualizar/")
	public ResponseEntity<String> updateCliente(
			@ApiParam(name = "id", type = "Integer", value = "A identificação do cliente", example = "99", required = true) @RequestParam(value = "id") Integer id,
			@ApiParam(name = "nome", type = "String", value = "O nome do cliente em string", example = "Jose", required = true) @RequestParam(value = "nome") String nome) {
		try {
			service.updateCliente(id, nome);
			return ResponseEntity.noContent().build();
		} catch (DesafioPetzException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
	}

	@ApiOperation(value = "Deleta um cliente", notes = "Este endpoint deleta um cliente")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Confirmacao do delete"),
			@ApiResponse(code = 204, message = "Cliente não encontrado"),
			@ApiResponse(code = 422, message = "Erro ao deletar cliente") })
	@DeleteMapping("/cliente/v1.0/deletar/{id}")
	public ResponseEntity<Object> deleteCliente(
			@ApiParam(name = "id",
					  type = "Integer", 
					  value = "A identificação do cliente", 
					  example = "99", 
					  required = true) 
			@PathVariable(value = "id") Integer id){
		try {
			service.deleteCliente(id);
			return ResponseEntity.ok().build();
		} catch (DesafioPetzException e) {
			logger.error(e.getMessage());
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
	}

	@ApiOperation(value = "Retorna o cliente", notes = "Este endpoint consulta o cliente")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna o cliente"),
			@ApiResponse(code = 204, message = "Cliente não encontrado"),
			@ApiResponse(code = 422, message = "Erro ao consultar cliente") })
	@GetMapping("/cliente/v1.0/consultar/{id}")
	public ResponseEntity<String> findCliente(
			@ApiParam(name = "id", type = "Integer", value = "A identificação do usuário", example = "99", required = true) @PathVariable Integer id) {
		try {
			Cliente cliente = service.findCliente(id);
			if (cliente != null) {
				logger.info("cliente encontrado " + cliente.toString());
				return ResponseEntity.ok(cliente.toString());
			}
		} catch (Exception e) {
			logger.error("Erro: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
		logger.info(String.format("cliente id %s nao encontrado", id));
		return ResponseEntity.noContent().build();
	}
}
