package com.generation.farmacia_tio_patinhas.controller;

import java.util.List;

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

import com.generation.farmacia_tio_patinhas.model.Produtos;
import com.generation.farmacia_tio_patinhas.service.ProdutosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {

    @Autowired
    private ProdutosService produtosService;

    @GetMapping
    public ResponseEntity<List<Produtos>> getAll() {
        return ResponseEntity.ok(produtosService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produtos> getById(@PathVariable Long id) {
        return ResponseEntity.ok(produtosService.buscarPorId(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produtos>> getByNome(@PathVariable String nome) {
        return ResponseEntity.ok(produtosService.buscarPorNome(nome));
    }

    @PostMapping
    public ResponseEntity<Produtos> post(@Valid @RequestBody Produtos produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtosService.criar(produto));
    }

    @PutMapping
    public ResponseEntity<Produtos> put(@Valid @RequestBody Produtos produto) {
        return ResponseEntity.ok(produtosService.atualizar(produto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        produtosService.deletar(id);
    }
}
