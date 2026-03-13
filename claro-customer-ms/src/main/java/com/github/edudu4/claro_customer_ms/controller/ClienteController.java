package com.github.edudu4.claro_customer_ms.controller;

import com.github.edudu4.claro_customer_ms.dto.ClienteDTO;
import com.github.edudu4.claro_customer_ms.dto.ClienteResumo;
import com.github.edudu4.claro_customer_ms.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping
    ResponseEntity<List<ClienteResumo>> listar() {
        return ResponseEntity.ok(clienteService.listar());
    }

    @GetMapping("{id}")
    ResponseEntity<ClienteDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscar(id));
    }

    @GetMapping("cpf/{cpf}")
    ResponseEntity<ClienteDTO> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(clienteService.buscarPorCpf(cpf));
    }

    @PostMapping
    ResponseEntity<ClienteDTO> salvar(@RequestBody @Validated ClienteDTO cliente) {
        return ResponseEntity.ok(clienteService.criar(cliente));
    }

    @PutMapping
    ResponseEntity<ClienteDTO> atualizar(@RequestBody @Validated ClienteDTO cliente) {
        return ResponseEntity.ok(clienteService.atualizar(cliente));
    }

    @DeleteMapping("{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}