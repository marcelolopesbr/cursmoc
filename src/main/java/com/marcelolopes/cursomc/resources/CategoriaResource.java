package com.marcelolopes.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marcelolopes.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@RequestMapping(method=RequestMethod.GET)
	public List<Categoria> listar() {
		ArrayList<Categoria> categorias = new ArrayList<>();
		categorias.add(new Categoria(1,"Informática"));
		categorias.add(new Categoria(1,"Escritório"));		
		return categorias;
	}
	
}
