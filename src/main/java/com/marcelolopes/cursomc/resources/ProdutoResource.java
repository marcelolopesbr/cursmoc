package com.marcelolopes.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcelolopes.cursomc.domain.Produto;
import com.marcelolopes.cursomc.dto.ProdutoDTO;
import com.marcelolopes.cursomc.resources.utils.URL;
import com.marcelolopes.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Produto produto = produtoService.find(id);
		return ResponseEntity.ok().body(produto);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> pageAll(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="24") String categorias, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="direction", defaultValue="DESC") String direction, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy) {
		Page<Produto> produtos = produtoService.search(URL.decodeString(nome), URL.decodeIntList(categorias), page, linesPerPage, direction, orderBy);
		Page<ProdutoDTO> categoriasDTO = produtos.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(categoriasDTO);
	}	
	
}
