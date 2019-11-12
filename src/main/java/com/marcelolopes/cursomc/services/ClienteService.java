package com.marcelolopes.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marcelolopes.cursomc.domain.Cliente;
import com.marcelolopes.cursomc.dto.ClienteDTO;
import com.marcelolopes.cursomc.repositories.ClienteRepository;
import com.marcelolopes.cursomc.services.exceptions.DataIntegrityException;
import com.marcelolopes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id : " + id + "Tipo : " + Cliente.class.getName()));
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), 
				clienteDTO.getNome(), 
				clienteDTO.getEmail(), 
				null, 
				null);
	}
	
	public List<Cliente> findAll() { 
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> page(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);		
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return clienteRepository.save(obj);
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		newObj = updateData(newObj, obj);
		return clienteRepository.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException ex) {
			throw new DataIntegrityException("Impossivel excluir cliente com pedidos", ex);
		}
	}
	
	private Cliente updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		return newObj;
	}
	
	
}
