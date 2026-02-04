package com.deliverytech.delivery_api.dto.responses;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClienteResponseDTO {
    
    private String nome; 
    
    private String email;

    private String telefone;

    private String endereco; 

    private boolean ativo;  

    private LocalDateTime dataCadastro;
}