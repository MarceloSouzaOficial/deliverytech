package com.deliverytech.delivery_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.model.Restaurante;
import com.deliverytech.delivery_api.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    private final RestauranteService service;

    public RestauranteController(RestauranteService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Restaurante> cadastrar(@RequestBody Restaurante dados){
        return ResponseEntity.status(201).body(service.cadastrar(dados));
    }

    @GetMapping("/listar")
    public List<Restaurante> listar(){
        return service.listarAtivos();
    }

    @GetMapping("/{id}")
    public Restaurante buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }
    
    @GetMapping("/categoria/{categoria}")
    public List<Restaurante> buscarPorCategoria(@PathVariable String categoria){
        return service.buscarPorCategoria(categoria);
    }



    @DeleteMapping("/{id}")
    public void desativar(@PathVariable Long id){
        service.desativar(id);
    }


}
