package com.github.edudu4.claro_customer_ms.utils;

import com.github.edudu4.claro_customer_ms.dto.ClienteDTO;
import com.github.edudu4.claro_customer_ms.entity.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteUtil {
    public static ClienteDTO getClienteDTO() {
        return ClienteDTO.builder()
                .nome("Eduardo Alves")
                .email("teste@email.com")
                .cpf("12345678901")
                .telefone("61999999999")
                .build();
    }

    public static Cliente getCliente() {
        return Cliente.builder()
                .nome("Eduardo Alves")
                .email("teste@email.com")
                .cpf("12345678901")
                .telefone("61999999999")
                .build();
    }

    public static List<Cliente> getListCliente() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(Cliente.builder()
                .nome("Eduardo Alves")
                .email("teste@email.com")
                .cpf("12345678901")
                .telefone("61999999999")
                .build());
        clientes.add(Cliente.builder()
                .nome("João da Silva")
                .email("testee@email.com")
                .cpf("12345678921")
                .telefone("61999999999")
                .build());
        clientes.add(Cliente.builder()
                .nome("Pedro Santos")
                .email("testte@email.com")
                .cpf("12345678201")
                .telefone("61999999999")
                .build());

        return clientes;
    }
}
