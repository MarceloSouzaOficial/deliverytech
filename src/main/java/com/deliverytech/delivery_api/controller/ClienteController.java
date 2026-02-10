package com.deliverytech.delivery_api.controller;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.deliverytech.delivery_api.dto.requests.ClienteDTO;
import com.deliverytech.delivery_api.dto.responses.ClienteResponseDTO;
import com.deliverytech.delivery_api.dto.responses.PagedResponse;
import com.deliverytech.delivery_api.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping(
    value = "/clientes",
    produces = "application/json"
)
@CrossOrigin(origins = "*")
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
    public ResponseEntity<com.deliverytech.delivery_api.dto.responses.ApiResponse<ClienteResponseDTO>> cadastrar(@Valid @RequestBody ClienteDTO cliente){
        ClienteResponseDTO response = service.cadastrar(cliente);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(location).header("Content-Type", "application/json").body(new com.deliverytech.delivery_api.dto.responses.ApiResponse<>(response));
    }

    @Operation(summary="Listar clientes ativos (paginado).")
    @ApiResponses(
        value={
            @ApiResponse(responseCode="200", description="Lista de clientes ativos retornado com sucesso."),
            @ApiResponse(responseCode="404", description="Cliente não encontrado."),
        }
    )
    @GetMapping
    public ResponseEntity<PagedResponse<ClienteResponseDTO>> listar(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){ 
        Pageable pageable = PageRequest.of(page, size);
        var pageResult =  service.listarAtivos(pageable);
        var response = new PagedResponse<>(pageResult);
        return ResponseEntity.ok()
        .header("Content-Type", "application/json")
        .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
        .body(response);
    }

    @Operation(summary="Buscar cliente por Id.")
    @ApiResponses(
        value={
            @ApiResponse(responseCode="200", description="Cliente encontrado com sucesso."),
            @ApiResponse(responseCode="404", description="Cliente não encontrado pelo Id mencionado."),
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<com.deliverytech.delivery_api.dto.responses.ApiResponse<ClienteResponseDTO>> buscar(@PathVariable Long id){
        
        return ResponseEntity.ok().header("Content-Type", "application/json").body(new com.deliverytech.delivery_api.dto.responses.ApiResponse<>(service.buscarPorId(id)));
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
    public ResponseEntity<com.deliverytech.delivery_api.dto.responses.ApiResponse<ClienteResponseDTO>> toggleAtivo(@PathVariable Long id){
        return ResponseEntity.ok().header("Content-Type", "application/json").body(new com.deliverytech.delivery_api.dto.responses.ApiResponse<>(service.toggleAtivo(id)));
    }    

}