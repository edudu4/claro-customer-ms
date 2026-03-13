package com.github.edudu4.claro_customer_ms.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClienteCriadoEvent {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
}
