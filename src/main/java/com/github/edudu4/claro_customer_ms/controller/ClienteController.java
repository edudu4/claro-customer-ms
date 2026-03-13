package com.github.edudu4.claro_customer_ms.controller;

import com.github.edudu4.claro_customer_ms.dto.ClienteDTO;
import com.github.edudu4.claro_customer_ms.dto.ClienteResumo;
import com.github.edudu4.claro_customer_ms.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cliente")
@Tag(name = "Clientes", description = "Operações de gerenciamento de clientes")
public class ClienteController {
    private final ClienteService clienteService;

    @Operation(summary = "Listagem resumida de todos os clientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listagem dos clientes"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping
    ResponseEntity<List<ClienteResumo>> listar() {
        return ResponseEntity.ok(clienteService.listar());
    }

    @Operation(summary = "Buscar cliente por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping("{id}")
    ResponseEntity<ClienteDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscar(id));
    }

    @Operation(summary = "Buscar cliente por CPF")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping("cpf/{cpf}")
    ResponseEntity<ClienteDTO> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(clienteService.buscarPorCpf(cpf));
    }

    @Operation(summary = "Criar cliente", description = "Cria um novo cliente e publica um evento na fila RabbitMQ")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "CPF já cadastrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @PostMapping
    ResponseEntity<ClienteDTO> salvar(@RequestBody @Validated ClienteDTO cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.criar(cliente));
    }

    @Operation(summary = "Atualizar cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @PutMapping
    ResponseEntity<ClienteDTO> atualizar(@RequestBody @Validated ClienteDTO cliente) {
        return ResponseEntity.ok(clienteService.atualizar(cliente));
    }

    @Operation(summary = "Excluir cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @DeleteMapping("{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}