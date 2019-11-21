package com.marcelolopes.cursomc.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marcelolopes.cursomc.domain.Cliente;
import com.marcelolopes.cursomc.domain.ItemPedido;
import com.marcelolopes.cursomc.domain.PagamentoComBoleto;
import com.marcelolopes.cursomc.domain.Pedido;
import com.marcelolopes.cursomc.domain.enums.EstadoPagamento;
import com.marcelolopes.cursomc.repositories.ItemPedidoRepository;
import com.marcelolopes.cursomc.repositories.PagamentoRepository;
import com.marcelolopes.cursomc.repositories.PedidoRepository;
import com.marcelolopes.cursomc.security.UserSS;
import com.marcelolopes.cursomc.services.exceptions.AuthorizationException;
import com.marcelolopes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	BoletoService boletoService;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	PagamentoRepository pagamentoRepository;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	EmailService emailService;

	@Transactional
	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id : " + id + "Tipo : " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstant(new Date());
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
		pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, pedido.getInstant());
		}
		pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		for (ItemPedido item : pedido.getItens()) {
			item.setDesconto(0.0);
			item.setProduto(produtoService.find(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		emailService.sendOrderConfirmationEmail(pedido);
		return pedido;
	}

	@Transactional
	public Page<Pedido> findByCliente(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		UserSS user = UserService.authenticated();
		if (null == user) {
			throw new AuthorizationException("Acesso negado");
		}
		Cliente cliente = clienteService.find(user.getId());
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}
	
}
