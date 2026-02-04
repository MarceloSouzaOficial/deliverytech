package com.deliverytech.delivery_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.requests.ProdutoDTO;
import com.deliverytech.delivery_api.dto.responses.ProdutoResponseDTO;
import com.deliverytech.delivery_api.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService  produtoService;

    public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }

/*   @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    } */

    @PostMapping("/restaurante/{restauranteId}")
    public ResponseEntity<ProdutoResponseDTO> cadastrar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoDTO produto){
        ProdutoResponseDTO resposta = produtoService.cadastrar(restauranteId, produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorRestaurante(@PathVariable Long restauranteId){
        return ResponseEntity.ok(produtoService.listarPorRestaurante(restauranteId));
    }

    @PatchMapping("/{produtoId}/disponibilidade")
    public ResponseEntity<ProdutoResponseDTO> toggleDisponibilidade(@PathVariable Long produtoId) {
        return ResponseEntity.ok(produtoService.toggleDisponibilidade(produtoId));
    }

}