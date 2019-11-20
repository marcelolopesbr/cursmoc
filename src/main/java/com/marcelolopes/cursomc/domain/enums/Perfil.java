package com.marcelolopes.cursomc.domain.enums;

public enum Perfil {

	ADMIN(1,"ROLE_ADMIN"),
	CLIENTE(2,"ROLE_CLIENTE");
	
	private Integer cod;
	private String descricao;
	
	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	private Perfil(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	static public Perfil toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (Perfil est: Perfil.values()) {
			if (est.getCod() == cod) {
				return est;
			}
		}
		
		throw new IllegalArgumentException("Perfil " + cod + " inexistente");
	}
	
}
