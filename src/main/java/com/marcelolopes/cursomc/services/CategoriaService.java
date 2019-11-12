package com.marcelolopes.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marcelolopes.cursomc.domain.Categoria;
import com.marcelolopes.cursomc.dto.CategoriaDTO;
import com.marcelolopes.cursomc.repositories.CategoriaRepository;
import com.marcelolopes.cursomc.services.exceptions.DataIntegrityException;
import com.marcelolopes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository categoriaRepository;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id : " + id + "Tipo : " + Categoria.class.getName()));
	}

	public List<Categoria> findAll() { 
		return categoriaRepository.findAll();
	}
	
	public Page<Categoria> page(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);		
		return categoriaRepository.findAll(pageRequest);
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return categoriaRepository.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		newObj = updateData(newObj, obj);
		return categoriaRepository.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException ex) {
			throw new DataIntegrityException("Impossivel excluir categoria que possui produtos", ex);
		}
	}
	
	public Categoria fromDTO(CategoriaDTO obj) {
		return new Categoria(obj.getId(), obj.getNome());
	}
	
	private Categoria updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
		return newObj;
	}
	
	
}
