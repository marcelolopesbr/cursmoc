package com.marcelolopes.cursomc.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1,"Pendente"),
	QUITADO(2,"Quitado"),
	CANCELADO(3,"Cancelado");
	
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

	private EstadoPagamento(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	static public EstadoPagamento toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (EstadoPagamento est: EstadoPagamento.values()) {
			if (est.getCod() == cod) {
				return est;
			}
		}
		
		throw new IllegalArgumentException("Estado de Pagamento" + cod + "inexistente");
	}
	
}
