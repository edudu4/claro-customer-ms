package com.github.edudu4.claro_customer_ms.exception;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException() {
        super("Cliente não encontrado.");
    }
}
