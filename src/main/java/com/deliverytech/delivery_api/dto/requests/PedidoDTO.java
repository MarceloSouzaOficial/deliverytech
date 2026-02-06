package com.deliverytech.delivery_api.dto.requests;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {
    @NotBlank
    private String enderecoEntrega;

    @NotNull
    private Long clienteId;

    @NotNull
    private Long restauranteId;

    @Valid
    @NotNull
    private List<ItemPedidoDTO> itens;
}