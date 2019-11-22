package com.marcelolopes.cursomc.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcelolopes.cursomc.domain.Estado;
import com.marcelolopes.cursomc.dto.EstadoDTO;
import com.marcelolopes.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	EstadoRepository estadoRepository;
	
	public List<Estado> findAll() {
		return estadoRepository.findAllByOrderByNome();
	}
	
}
