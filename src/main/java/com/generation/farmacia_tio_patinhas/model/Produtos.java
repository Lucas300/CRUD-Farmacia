package com.generation.farmacia_tio_patinhas.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_produtos")
public class Produtos {
//	nome VARCHAR(50),
//	preco DECIMAL(6,2) NOT NULL,
//	descricao VARCHAR(255),
//	quantidade INT,
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	
	@DecimalMin(value = "0.0", inclusive = false, message = "O salário deve ser maior que zero.")
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal preco;
	
	@NotNull
	private int quantidade;

	@ManyToOne
	@JsonIgnoreProperties("produtos") //evita loop
	private Categorias categorias;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Categorias getCategorias() {
		return categorias;
	}

	public void setCategorias(Categorias categorias) {
		this.categorias = categorias;
	}
	
	
}
