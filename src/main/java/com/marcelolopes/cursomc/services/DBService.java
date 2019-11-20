package com.marcelolopes.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.marcelolopes.cursomc.domain.Categoria;
import com.marcelolopes.cursomc.domain.Cidade;
import com.marcelolopes.cursomc.domain.Cliente;
import com.marcelolopes.cursomc.domain.Endereco;
import com.marcelolopes.cursomc.domain.Estado;
import com.marcelolopes.cursomc.domain.ItemPedido;
import com.marcelolopes.cursomc.domain.PagamentoComBoleto;
import com.marcelolopes.cursomc.domain.PagamentoComCartao;
import com.marcelolopes.cursomc.domain.Pedido;
import com.marcelolopes.cursomc.domain.Produto;
import com.marcelolopes.cursomc.domain.enums.EstadoPagamento;
import com.marcelolopes.cursomc.domain.enums.TipoCliente;
import com.marcelolopes.cursomc.repositories.CategoriaRepository;
import com.marcelolopes.cursomc.repositories.CidadeRepository;
import com.marcelolopes.cursomc.repositories.ClienteRepository;
import com.marcelolopes.cursomc.repositories.EnderecoRepository;
import com.marcelolopes.cursomc.repositories.EstadoRepository;
import com.marcelolopes.cursomc.repositories.ItemPedidoRepository;
import com.marcelolopes.cursomc.repositories.PagamentoRepository;
import com.marcelolopes.cursomc.repositories.PedidoRepository;
import com.marcelolopes.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public void instantiateTestDatabase() throws ParseException {
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		Categoria cat3 = new Categoria(null,"Cama, mesa e banho");
		Categoria cat4 = new Categoria(null,"Utilidades domésticas");		
		Categoria cat5 = new Categoria(null,"Eletrônicos");
		Categoria cat6 = new Categoria(null,"Brinquedos");
		Categoria cat7 = new Categoria(null,"Vestuário");
		
		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);
		Produto p4 = new Produto(null,"Mesa de escritório", 80.00);
		Produto p5 = new Produto(null,"Toalha", 2000.00);
		Produto p6 = new Produto(null,"Colcha", 800.00);
		Produto p7 = new Produto(null,"TV true color", 80.00);
		Produto p8 = new Produto(null,"Roçadeira", 80.00);		
		Produto p9 = new Produto(null,"Abajur", 2000.00);
		Produto p10 = new Produto(null,"Pendente", 800.00);
		Produto p11 = new Produto(null,"Shampoo", 80.00);
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));		
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p6));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlândia",est1);
		Cidade c2 = new Cidade(null,"Campinas",est2);
		Cidade c3 = new Cidade(null,"São Paulo",est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));		
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria das Dores", "marcelo.almeida@serpro.gov.br", "27227800032", TipoCliente.PESSOAFISICA, pe.encode("batata"));

		cli1.getTelefones().addAll(Arrays.asList("34542874","9846753"));
		
		Endereco end1 = new Endereco(null, "Rua Java", "100", "Ap 301", "Oraculo", "3484787", cli1, c1);
		Endereco end2 = new Endereco(null, "Rua Fim do Mundo", "400", "casa 12", "Longe", "387644", cli1, c3);		
		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		
		Pedido ped1 = new Pedido(null, sdf.parse("01/11/2019 12:43"), end1, cli1);
		Pedido ped2 = new Pedido(null, sdf.parse("07/11/2019 09:27"), end2, cli1);
		
		PagamentoComCartao pgto1 = new PagamentoComCartao(null, EstadoPagamento.PENDENTE, ped1, 3);
		PagamentoComBoleto pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("09/11/2019 00:01"), null);
		
		ped1.setPagamento(pgto1);
		ped2.setPagamento(pgto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));
		
		ItemPedido ip1 = new ItemPedido(p1, ped1, 0.00, 2, 4000.00);
		ItemPedido ip2 = new ItemPedido(p2, ped1, 0.00, 1, 800.00);
		ItemPedido ip3 = new ItemPedido(p3, ped2, 10.00, 1, 70.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip2));		
		p3.getItens().addAll(Arrays.asList(ip3));	
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}
	
}
