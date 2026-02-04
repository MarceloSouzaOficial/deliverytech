package com.deliverytech.delivery_api.service;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.dto.requests.ProdutoDTO;
import com.deliverytech.delivery_api.dto.responses.ProdutoResponseDTO;
import com.deliverytech.delivery_api.exceptions.BusinessException;
import com.deliverytech.delivery_api.exceptions.EntityNotFoundException;
import com.deliverytech.delivery_api.model.Produto;
import com.deliverytech.delivery_api.model.Restaurante;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final RestauranteRepository restauranteRepository;
    private final ModelMapper mapper;

    public ProdutoService(ProdutoRepository produtoRepository, RestauranteRepository restauranteRepository, ModelMapper mapper) {
        this.produtoRepository = produtoRepository;
        this.restauranteRepository = restauranteRepository;
        this.mapper = mapper;
    }

    @Transactional
    public ProdutoResponseDTO cadastrar(Long restauranteId, ProdutoDTO produto){
        if(produto.getPreco() == null ||  produto.getPreco().compareTo(BigDecimal.ZERO) <= 0){
            throw new BusinessException("Preço inválido.");
        }

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
            .orElseThrow(() -> new EntityNotFoundException("Restaurante não localizado."));
        
        if(!restaurante.isAtivo()){
            throw new BusinessException("Restaurante inativo. Não é possível cadastrar produtos.");
        }

        Produto novoProduto = mapper.map(produto, Produto.class);
        novoProduto.setDisponivel(true);
        novoProduto.setRestaurante(restaurante);
        Produto salvo = produtoRepository.save(novoProduto);

        ProdutoResponseDTO resposta = mapper.map(salvo, ProdutoResponseDTO.class);
        resposta.setRestauranteId(restaurante.getId());
        return resposta;
    }

    public List<ProdutoResponseDTO> listarPorRestaurante(Long restauranteId){
        if(!restauranteRepository.existsById(restauranteId)){
            throw new EntityNotFoundException("Restaurante não localizado.");
        }

        return produtoRepository.findByRestauranteIdAndDisponivelTrue(restauranteId)
            .stream()
            .map(produto -> {
                ProdutoResponseDTO dto = mapper.map(produto, ProdutoResponseDTO.class);
                dto.setRestauranteId(produto.getRestaurante().getId());
                return dto;
            })
            .toList();
    }



    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
    }

}