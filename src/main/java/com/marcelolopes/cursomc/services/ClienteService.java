package com.marcelolopes.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.marcelolopes.cursomc.domain.Cidade;
import com.marcelolopes.cursomc.domain.Cliente;
import com.marcelolopes.cursomc.domain.Endereco;
import com.marcelolopes.cursomc.domain.enums.Perfil;
import com.marcelolopes.cursomc.domain.enums.TipoCliente;
import com.marcelolopes.cursomc.dto.ClienteDTO;
import com.marcelolopes.cursomc.dto.ClienteNewDTO;
import com.marcelolopes.cursomc.repositories.CidadeRepository;
import com.marcelolopes.cursomc.repositories.ClienteRepository;
import com.marcelolopes.cursomc.repositories.EnderecoRepository;
import com.marcelolopes.cursomc.security.UserSS;
import com.marcelolopes.cursomc.services.exceptions.AuthorizationException;
import com.marcelolopes.cursomc.services.exceptions.DataIntegrityException;
import com.marcelolopes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (null == user || !user.getRole(Perfil.ADMIN) && id != user.getId()) {
			System.out.println("Usuario admin? " + user.getRole(Perfil.ADMIN));
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id : " + id + "Tipo : " + Cliente.class.getName()));
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), 
				clienteDTO.getNome(), 
				clienteDTO.getEmail(), 
				null, 
				null,
				null);
	}
	
	public Cliente fromDTO(ClienteNewDTO clienteDTO) {
		
		Cliente cli = new Cliente(null, clienteDTO.getNome(), clienteDTO.getEmail(), clienteDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteDTO.getTipo()), pe.encode(clienteDTO.getSenha()));
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
	
	public Cliente findByEmail(String email) {
		
		UserSS user = UserService.authenticated();
		if (null == user || !user.getRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if (null == cliente) {
			throw new ObjectNotFoundException("Cliente não encontrado");
		}
		
		return cliente;
		
//		Optional<Cliente> cliente = clienteRepository.findById(user.getId());
//		return cliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
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
			throw new DataIntegrityException("Não é possivel excluir porque o cliente possui pedidos", ex);
		}
	}
	
	private Cliente updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		return newObj;
	}
	
	
}
