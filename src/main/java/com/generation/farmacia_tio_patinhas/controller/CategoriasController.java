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

import com.generation.farmacia_tio_patinhas.model.Categorias;
import com.generation.farmacia_tio_patinhas.repository.CategoriasRepository;

import jakarta.validation.Valid;
//Define esta classe como um controlador REST
@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriasController {

	// Injeta automaticamente uma instância do repositório de categorias
	@Autowired
	private CategoriasRepository categoriasRepository;

	// Endpoint para listar todas as categorias
	@GetMapping
	public ResponseEntity<List<Categorias>> getAll() {
		return ResponseEntity.ok(categoriasRepository.findAll());
	}

	// Endpoint para buscar uma categoria pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<Categorias> getById(@PathVariable Long id) {
		return categoriasRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	// Endpoint para buscar categorias pelo nome (parcial ou completo)
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Categorias>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(categoriasRepository.findAllByNomeCategoriaContainingIgnoreCase(nome));
	}

	// Endpoint para criar uma nova categoria
	@PostMapping
	public ResponseEntity<Categorias> post(@Valid @RequestBody Categorias categorias) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriasRepository.save(categorias));
	}

	// Endpoint para atualizar uma categoria existente
	@PutMapping
	public ResponseEntity<Categorias> put(@Valid @RequestBody Categorias categorias) {
		return categoriasRepository.findById(categorias.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(categoriasRepository.save(categorias)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	// Endpoint para deletar uma categoria pelo ID
	@ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Categorias> tema = categoriasRepository.findById(id);
        
        if(tema.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        
        categoriasRepository.deleteById(id);              
    }
	
}
