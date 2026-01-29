package com.deliverytech.delivery_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.model.ItemPedido;
import com.deliverytech.delivery_api.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository repository;

    public ItemPedidoService(ItemPedidoRepository repository) {
        this.repository = repository;
    }

    public List<ItemPedido> listarPorPedido(Long pedidoId){
        return repository.findByPedidoId(pedidoId);
    }

    
}
