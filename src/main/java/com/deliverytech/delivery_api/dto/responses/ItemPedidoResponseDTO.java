package com.deliverytech.delivery_api.dto.responses;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoResponseDTO {
    private Long produtoId;

    private String nomeProduto;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal subtotal;
}
