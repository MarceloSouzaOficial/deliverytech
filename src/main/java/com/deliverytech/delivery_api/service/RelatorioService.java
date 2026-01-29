package com.deliverytech.delivery_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.dto.TotalVendasPorRestauranteDTO;
import com.deliverytech.delivery_api.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RelatorioService {
    private final PedidoRepository respository;
    
    public List<TotalVendasPorRestauranteDTO> totalVendasPorRestaurante(){
        return respository.totalVendasPorRestaurante();
    }  

    public List<Object[]> rankingClientes(){
        return respository.rankingClientes();
    }

}