package com.deliverytech.delivery_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.requests.PedidoDTO;
import com.deliverytech.delivery_api.dto.responses.PedidoResponseDTO;
import com.deliverytech.delivery_api.service.PedidoService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody @Valid PedidoDTO dto){
        return ResponseEntity.ok(pedidoService.criarPedido(dto)); 
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<PedidoResponseDTO> confirmar(@PathVariable Long id ){
        return ResponseEntity.ok(pedidoService.confirmarPedido(id));
    }

    @PutMapping("/{id}/status")
    public  ResponseEntity<PedidoResponseDTO> atualizarStatus(@PathVariable Long id){
        return  ResponseEntity.ok(pedidoService.atualizarStatus(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarItensPorCliente(@PathVariable Long clienteId){
        return ResponseEntity.ok(pedidoService.listarItensPorCliente(clienteId));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponseDTO> cancelarPedido(@PathVariable Long id){
        return ResponseEntity.ok(pedidoService.cancelarPedido(id));
    }

}