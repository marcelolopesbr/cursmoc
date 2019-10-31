package com.marcelolopes.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.marcelolopes.cursomc.domain.Categoria;
import com.marcelolopes.cursomc.repositories.CategoriaRepository;

@SpringBootApplication
@EntityScan(basePackages="com.marcelolopes.cursomc.domain")
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
	}

}
