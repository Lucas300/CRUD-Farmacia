package com.generation.farmacia_tio_patinhas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.farmacia_tio_patinhas.model.Categorias;


public interface CategoriasRepository extends JpaRepository<Categorias, Long>{
	
	public List<Categorias> findAllByNomeCategoriaContainingIgnoreCase(@Param("nomeCategoria") String nomeCategoria);

}
