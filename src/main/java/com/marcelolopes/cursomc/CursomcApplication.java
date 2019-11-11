package com.marcelolopes.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.marcelolopes.cursomc.domain.Categoria;
import com.marcelolopes.cursomc.domain.Cidade;
import com.marcelolopes.cursomc.domain.Cliente;
import com.marcelolopes.cursomc.domain.Endereco;
import com.marcelolopes.cursomc.domain.Estado;
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
import com.marcelolopes.cursomc.repositories.PagamentoRepository;
import com.marcelolopes.cursomc.repositories.PedidoRepository;
import com.marcelolopes.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
@EntityScan(basePackages="com.marcelolopes.cursomc.domain")
public class CursomcApplication implements CommandLineRunner{

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
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);		
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlândia",est1);
		Cidade c2 = new Cidade(null,"Campinas",est2);
		Cidade c3 = new Cidade(null,"São Paulo",est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));		
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria das Dores", "eita@eita.com", "27227800032", TipoCliente.PESSOAFISICA);

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
		
	}

}
