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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.requests.RestauranteDTO;
import com.deliverytech.delivery_api.dto.responses.RestauranteResponseDTO;
import com.deliverytech.delivery_api.service.RestauranteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    private final RestauranteService service;

    public RestauranteController(RestauranteService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> cadastrar(@RequestBody @Valid RestauranteDTO dados){
        RestauranteResponseDTO response = service.cadastrar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/listar")
    public ResponseEntity<List<RestauranteResponseDTO>> listar(){
        return ResponseEntity.ok(service.listarAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    
    @GetMapping("/categoria")
    public List<RestauranteResponseDTO> buscarPorCategoria(@RequestParam String categoria){
        return service.buscarPorCategoria(categoria);
    }


    @PatchMapping("/{id}/toggle")
    public ResponseEntity<RestauranteResponseDTO> toggle(@PathVariable Long id){
        return ResponseEntity.ok(service.toggle(id));
    }


}