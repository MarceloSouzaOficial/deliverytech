package com.deliverytech.delivery_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes.")
public class ClienteController {

    @Autowired
    private final ClienteService service;

    public ClienteController(ClienteService service){
        this.service = service;
    }

    @Operation(summary= "Cadastrar novo cliente.")
    @ApiResponses(
                value={
                    @ApiResponse(responseCode="201", description="Cliente cadastrado com sucesso."),
                    @ApiResponse(responseCode="400", description="Erro de validação."),
                }
    )
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrar(@Valid @RequestBody ClienteDTO cliente){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(cliente));
    }

    @Operation(summary="Listar clientes ativos.")
    @ApiResponses(
        value={
            @ApiResponse(responseCode="200", description="Lista de clientes ativos retornado com sucesso."),
            @ApiResponse(responseCode="404", description="Cliente não encontrado."),
        }
    )
    @GetMapping("/listar")
    public List<ClienteResponseDTO> listar() {
        return service.listarAtivos();
    }

    @Operation(summary="Buscar cliente por Id.")
    @ApiResponses(
        value={
            @ApiResponse(responseCode="200", description="Cliente encontrado com sucesso."),
            @ApiResponse(responseCode="404", description="Cliente não encontrado pelo Id mencionado."),
        }
    )
    @GetMapping("/{id}")
    public ClienteResponseDTO buscar(@PathVariable Long id){
        return service.buscarPorId(id);
    }

/*     @PutMapping("/{id}")
    public Cliente atualizar (@PathVariable Long id, @RequestBody Cliente dados) {
        return service.atualizar(id, dados);
    }
     */

    @Operation(summary="Ativar ou desativar cliente")
    @ApiResponses(
        value={
            @ApiResponse(responseCode="200", description="Cliente encontrado com sucesso."),
            @ApiResponse(responseCode="404", description="Cliente não encontrado pelo Id mencionado."),
        }
    )
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ClienteResponseDTO> toggleAtivo(@PathVariable Long id){
        return ResponseEntity.ok(service.toggleAtivo(id));
    }    

}