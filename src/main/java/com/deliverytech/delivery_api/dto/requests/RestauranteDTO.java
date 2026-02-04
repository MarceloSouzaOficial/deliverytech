package com.deliverytech.delivery_api.dto.requests;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RestauranteDTO {

    @NotNull
    @NotBlank
    private String nome;

    @NotBlank
    private String categoria;

    @Size(max = 255)
    private String endereco;

    @NotBlank
    private String telefone;

    @NotNull(message = "A taxa de entrega não pode ser nula.")
    private BigDecimal taxaEntrega;

}