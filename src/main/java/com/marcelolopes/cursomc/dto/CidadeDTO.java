package com.marcelolopes.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcelolopes.cursomc.domain.Cidade;
import com.marcelolopes.cursomc.domain.Estado;

public class CidadeDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=2, max=255, message="O tamanho deve ser de 2 a 255 caracteres")	
	private String nome;
	
	@JsonIgnore
	private Estado estado;

	public CidadeDTO() {}
	
	public CidadeDTO(Cidade cidade) {
		this.id = cidade.getId();
		this.estado = cidade.getEstado();
		this.nome = cidade.getNome();
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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
}
