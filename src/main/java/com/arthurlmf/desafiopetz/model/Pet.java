package com.arthurlmf.desafiopetz.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Pet {
	
	

	@Id
	private Integer id;
	private String nome;
	private String raca;
	
	@ManyToOne
	private Cliente dono;
	
	public Pet() {
	}

	public Pet(Integer id, String nome, String raca, Cliente dono) {
		super();
		this.id = id;
		this.nome = nome;
		this.raca = raca;
		this.dono = dono;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRaca() {
		return raca;
	}
	public void setRaca(String raca) {
		this.raca = raca;
	}
	public Cliente getDono() {
		return dono;
	}
	public void setDono(Cliente dono) {
		this.dono = dono;
	}
	
	@Override
	public String toString() {
		return String.format("Pet [id=%s, nome=%s, raca=%s, dono=%s]", id, nome, raca, dono);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dono == null) ? 0 : dono.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((raca == null) ? 0 : raca.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pet other = (Pet) obj;
		if (dono == null) {
			if (other.dono != null)
				return false;
		} else if (!dono.equals(other.dono))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (raca == null) {
			if (other.raca != null)
				return false;
		} else if (!raca.equals(other.raca))
			return false;
		return true;
	}


	
	
}
