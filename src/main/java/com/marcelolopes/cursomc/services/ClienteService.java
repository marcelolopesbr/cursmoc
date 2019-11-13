package com.marcelolopes.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marcelolopes.cursomc.domain.Cidade;
import com.marcelolopes.cursomc.domain.Cliente;
import com.marcelolopes.cursomc.domain.Endereco;
import com.marcelolopes.cursomc.domain.enums.TipoCliente;
import com.marcelolopes.cursomc.dto.ClienteDTO;
import com.marcelolopes.cursomc.dto.ClienteNewDTO;
import com.marcelolopes.cursomc.repositories.CidadeRepository;
import com.marcelolopes.cursomc.repositories.ClienteRepository;
import com.marcelolopes.cursomc.repositories.EnderecoRepository;
import com.marcelolopes.cursomc.services.exceptions.DataIntegrityException;
import com.marcelolopes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
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
	
	public Cliente fromDTO(ClienteNewDTO clienteDTO) {
		
		Cliente cli = new Cliente(null, clienteDTO.getNome(), clienteDTO.getEmail(), clienteDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteDTO.getTipo()));
		Cidade cidade = new Cidade(clienteDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, clienteDTO.getLogradouro(), clienteDTO.getNumero(), clienteDTO.getComplemento(), clienteDTO.getBairro(), clienteDTO.getCep(), cli, cidade);
		cli.getEnderecos().add(endereco);
		cli.getTelefones().add(clienteDTO.getTelefone1());
		if (clienteDTO.getTelefone2() != null) {
			cli.getTelefones().add(clienteDTO.getTelefone2());
		}
		if (clienteDTO.getTelefone3() != null) {
			cli.getTelefones().add(clienteDTO.getTelefone3());
		}
		
		return cli;
		
	}
	
	public List<Cliente> findAll() { 
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> page(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);		
		return clienteRepository.findAll(pageRequest);
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		Cliente cli = clienteRepository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return cli;
	}
	
	@Transactional
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
