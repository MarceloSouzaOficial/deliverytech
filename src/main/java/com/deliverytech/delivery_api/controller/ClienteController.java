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

import com.deliverytech.delivery_api.dto.requests.ClienteDTO;
import com.deliverytech.delivery_api.dto.responses.ClienteResponseDTO;

import com.deliverytech.delivery_api.service.ClienteService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrar(@Valid @RequestBody ClienteDTO cliente){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(cliente));
    }

    @GetMapping("/listar")
    public List<ClienteResponseDTO> listar() {
        return service.listarAtivos();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO buscar(@PathVariable Long id){
        return service.buscarPorId(id);
    }

/*     @PutMapping("/{id}")
    public Cliente atualizar (@PathVariable Long id, @RequestBody Cliente dados) {
        return service.atualizar(id, dados);
    }
     */

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ClienteResponseDTO> toggleAtivo(@PathVariable Long id){
        return ResponseEntity.ok(service.toggleAtivo(id));
    }    

}