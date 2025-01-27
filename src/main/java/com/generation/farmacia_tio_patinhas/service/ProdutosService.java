package com.generation.farmacia_tio_patinhas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmacia_tio_patinhas.model.Produtos;
import com.generation.farmacia_tio_patinhas.repository.CategoriasRepository;
import com.generation.farmacia_tio_patinhas.repository.ProdutosRepository;

@Service
public class ProdutosService {

    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private CategoriasRepository categoriasRepository;

    // Buscar todos os produtos
    public List<Produtos> listarTodos() {
        return produtosRepository.findAll();
    }

    // Buscar produto por ID
    public Produtos buscarPorId(Long id) {
        return produtosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    // Buscar produtos por nome
    public List<Produtos> buscarPorNome(String nome) {
        return produtosRepository.findAllByNomeContainingIgnoreCase(nome);
    }

    // Criar novo produto
    public Produtos criar(Produtos produto) {
        if (categoriasRepository.existsById(produto.getCategorias().getId())) {
            return produtosRepository.save(produto);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe");
    }

    // Atualizar produto
    public Produtos atualizar(Produtos produto) {
        if (!produtosRepository.existsById(produto.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
        if (!categoriasRepository.existsById(produto.getCategorias().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe");
        }
        return produtosRepository.save(produto);
    }

    // Deletar produto por ID
    public void deletar(Long id) {
        if (!produtosRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
        produtosRepository.deleteById(id);
    }
}
