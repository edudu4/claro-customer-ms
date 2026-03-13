package com.github.edudu4.claro_customer_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Data
@Builder
public class ClienteDTO {
    @Schema(description = "Identificador do cliente", example = "1")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome do cliente", example = "Eduardo Alves")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Schema(description = "Email do cliente", example = "eduardo@email.com")
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    @Schema(description = "CPF do cliente", example = "12345678901")
    private String cpf;

    @Schema(description = "Telefone do cliente", example = "61999999999")
    private String telefone;
}
