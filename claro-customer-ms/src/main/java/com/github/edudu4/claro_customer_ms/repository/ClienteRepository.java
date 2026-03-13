package com.github.edudu4.claro_customer_ms.repository;

import com.github.edudu4.claro_customer_ms.dto.ClienteDTO;
import com.github.edudu4.claro_customer_ms.dto.ClienteResumo;
import com.github.edudu4.claro_customer_ms.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT new com.github.edudu4.claro_customer_ms.dto.ClienteDTO(c.id,c.nome, c.email, c.cpf, c.telefone) " +
            "FROM Cliente c " +
            "WHERE c.cpf = ?1")
    Optional<ClienteDTO> findByCpf(String cpf);
    List<ClienteResumo> findAllProjectedBy();
}
