package com.marcelolopes.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcelolopes.cursomc.domain.Cliente;
import com.marcelolopes.cursomc.repositories.ClienteRepository;
import com.marcelolopes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id : " + id + "Tipo : " + Cliente.class.getName()));
	}
	
}
