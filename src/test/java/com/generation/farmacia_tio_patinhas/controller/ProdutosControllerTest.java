package com.generation.farmacia_tio_patinhas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.farmacia_tio_patinhas.model.Produtos;
import com.generation.farmacia_tio_patinhas.service.ProdutosService;

@WebMvcTest(ProdutosController.class)
public class ProdutosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutosService produtosService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void RetornarTodosOsProdutos() throws Exception {
        Produtos produto1 = new Produtos();
        produto1.setId(1L);
        produto1.setNome("Produto A");
        produto1.setPreco(new BigDecimal("10.00"));
        produto1.setQuantidade(5);

        Produtos produto2 = new Produtos();
        produto2.setId(2L);
        produto2.setNome("Produto B");
        produto2.setPreco(new BigDecimal("15.00"));
        produto2.setQuantidade(10);

        when(produtosService.listarTodos()).thenReturn(Arrays.asList(produto1, produto2));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Produto A"))
                .andExpect(jsonPath("$[1].nome").value("Produto B"));
    }

    @Test
    public void CriarNovoProduto() throws Exception {
        Produtos produto = new Produtos();
        produto.setId(1L);
        produto.setNome("Produto Novo");
        produto.setPreco(new BigDecimal("25.00"));
        produto.setQuantidade(3);

        when(produtosService.criar(any(Produtos.class))).thenReturn(produto);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Produto Novo"))
                .andExpect(jsonPath("$.preco").value(25.00))
                .andExpect(jsonPath("$.quantidade").value(3));
    }

    @Test
    public void RetornarProdutoPorId() throws Exception {
        Produtos produto = new Produtos();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(new BigDecimal("20.00"));
        produto.setQuantidade(2);

        when(produtosService.buscarPorId(1L)).thenReturn(produto);

        mockMvc.perform(get("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"))
                .andExpect(jsonPath("$.preco").value(20.00))
                .andExpect(jsonPath("$.quantidade").value(2));
    }

    @Test
    public void DeletarProduto() throws Exception {
        doNothing().when(produtosService).deletar(1L);

        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isNoContent());
    }
}

