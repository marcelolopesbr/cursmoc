package com.marcelolopes.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marcelolopes.cursomc.domain.Cidade;
import com.marcelolopes.cursomc.domain.Estado;
import com.marcelolopes.cursomc.dto.EstadoDTO;
import com.marcelolopes.cursomc.services.CidadeService;
import com.marcelolopes.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {
	
	@Autowired
	EstadoService estadoService;
	
	@Autowired
	CidadeService cidadeService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> estados = estadoService.findAll();
		List<EstadoDTO> estadosDTO = estados.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(estadosDTO);
	}
	
	@RequestMapping(value="/{id}/cidades", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		List<Cidade> cidades = cidadeService.findByEstado(new Estado(id, null));
		return ResponseEntity.ok().body(cidades);
	}
		
	
	
	
}
