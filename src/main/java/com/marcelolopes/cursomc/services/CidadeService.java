package com.marcelolopes.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcelolopes.cursomc.domain.Cidade;
import com.marcelolopes.cursomc.domain.Estado;
import com.marcelolopes.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	public List<Cidade> findByEstado(Estado estado) {
		return cidadeRepository.findByEstadoId(estado.getId());
	}

}
