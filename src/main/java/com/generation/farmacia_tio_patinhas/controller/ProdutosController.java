package com.generation.farmacia_tio_patinhas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmacia_tio_patinhas.model.Produtos;
import com.generation.farmacia_tio_patinhas.repository.CategoriasRepository;
import com.generation.farmacia_tio_patinhas.repository.ProdutosRepository;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {


	@Autowired
	private ProdutosRepository produtosRepository;

	@Autowired
	private CategoriasRepository categoriasRepository;
	
	//Buscando todos os Produtos
		@GetMapping          
	    public ResponseEntity<List<Produtos>> getAll() {
	        return ResponseEntity.ok(produtosRepository.findAll());
	    }
		
		//Buscando Produtos por ID
		@GetMapping("/{id}")
		public ResponseEntity<Produtos> getById(@PathVariable long id){
			return produtosRepository.findById(id)
					.map(resposta -> ResponseEntity.ok(resposta))
					.orElse(ResponseEntity
							.status(HttpStatus.NOT_FOUND).build());
		}
		
		//Buscando Produtos por nome
		@GetMapping("/nome/{nome}")
		public ResponseEntity<List<Produtos>> getByNome(@PathVariable String nome){
			return ResponseEntity.ok(produtosRepository.findAllByNomeContainingIgnoreCase(nome));
		}
		
		// Enviando dados via post
		@PostMapping
		public ResponseEntity<Produtos> post(@Valid @RequestBody Produtos produtos){
			if(categoriasRepository.existsById(produtos.getCategorias().getId())) {
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(produtosRepository.save(produtos));			
			}
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Categoria não existe!",null);	
		}
		
		//Put Mapping //atualizar os Produtos
		 @PutMapping
		    public ResponseEntity<Produtos> put(@Valid @RequestBody Produtos produtos) {
		    	if(produtosRepository.existsById(produtos.getId())){
		    		if(categoriasRepository.existsById(produtos.getCategorias().getId())) {
		    			return ResponseEntity.status(HttpStatus.OK)
		    					.body(produtosRepository.save(produtos));
		    		
		    		}
		    			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Produto não existe!",null);
		    	}
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		    }
		
		//Delete por Id
		@ResponseStatus(HttpStatus.NO_CONTENT) // Define o status de sucesso como 204 (No Content)
	    @DeleteMapping("/{id}")
		public void delete(@PathVariable Long id) {
			
			Optional<Produtos> produtos = produtosRepository.findById(id);
			if(produtos.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não existe!",null);
			}
			produtosRepository.deleteById(id);
			
		}
	
}
