package com.deliverytech.delivery_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    List<Produto> findByRestauranteId(Long restauranteId);

    List<Produto> findByCategoria(String categoria);

    List<Produto> findByDisponivelTrue();

    List<Produto> findByRestauranteIdAndDisponivelTrue(Long restauranteId);

}
