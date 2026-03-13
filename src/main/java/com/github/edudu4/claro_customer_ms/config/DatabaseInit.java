package com.github.edudu4.claro_customer_ms.config;

import com.github.edudu4.claro_customer_ms.entity.Cliente;
import com.github.edudu4.claro_customer_ms.repository.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseInit {
    @Bean
    CommandLineRunner initDatabase(
            ClienteRepository clienteRepository) {

        return args -> {
                Cliente c1 = new Cliente(null, "João Silva", "joao@email.com", "12345678901", "12345678912");
                Cliente c2 = new Cliente(null, "Maria Souza", "maria@email.com", "12345678902", "12345678911");
                Cliente c3 = new Cliente(null, "Pedro Santos", "pedro@email.com", "12345678903", "12345678913");
                Cliente c4 = new Cliente(null, "Ana Costa", "ana@email.com", "12345678904", "12345678910");
                Cliente c5 = new Cliente(null, "Lucas Oliveira", "lucas@email.com", "12345678905", "12345678914");

                clienteRepository.saveAll(List.of(c1, c2, c3, c4, c5));
        };
    }
}
