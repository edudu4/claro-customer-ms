package com.github.edudu4.claro_customer_ms.mapper;

import com.github.edudu4.claro_customer_ms.dto.ClienteDTO;
import com.github.edudu4.claro_customer_ms.entity.Cliente;
import com.github.edudu4.claro_customer_ms.messaging.event.ClienteCriadoEvent;

public class ClienteMapper {
    public static Cliente toEntity(ClienteDTO dto) {
        return Cliente.builder()
                .id(dto.getId())
                .cpf(dto.getCpf())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .build();
    }

    public static ClienteDTO toDto(Cliente cliente) {
        return ClienteDTO.builder()
                .id(cliente.getId())
                .cpf(cliente.getCpf())
                .nome(cliente.getNome())
                .email(cliente.getEmail())
                .telefone(cliente.getTelefone())
                .build();
    }

    public static ClienteCriadoEvent toEvent(Cliente cliente) {
        return ClienteCriadoEvent.builder().
                id(cliente.getId()).
                cpf(cliente.getCpf()).
                nome(cliente.getNome()).
                email(cliente.getEmail()).
                telefone(cliente.getTelefone()).
                build();
    }
}
