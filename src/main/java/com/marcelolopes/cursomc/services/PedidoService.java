package com.marcelolopes.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcelolopes.cursomc.domain.Categoria;
import com.marcelolopes.cursomc.domain.Pedido;
import com.marcelolopes.cursomc.repositories.PedidoRepository;
import com.marcelolopes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	PedidoRepository pedidoRepository;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id : " + id + "Tipo : " + Pedido.class.getName()));
	}
}
