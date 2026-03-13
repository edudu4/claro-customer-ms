package com.github.edudu4.claro_customer_ms.repository;

import com.github.edudu4.claro_customer_ms.dto.ClienteDTO;
import com.github.edudu4.claro_customer_ms.dto.ClienteResumo;
import com.github.edudu4.claro_customer_ms.entity.Cliente;
import com.github.edudu4.claro_customer_ms.utils.ClienteUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteRepositoryTest {
    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void deveVerificarExistenciaPorCpf() {
        clienteRepository.saveAll(ClienteUtil.getListCliente());

        List<ClienteResumo> clientesSalvos = clienteRepository.findAllProjectedBy();

        assertFalse(clientesSalvos.isEmpty());
        assertEquals(3, clientesSalvos.size());
    }

    @Test
    void deveBuscarClientePorCpf() {
        Cliente cliente = ClienteUtil.getCliente();

        clienteRepository.save(cliente);

        Optional<ClienteDTO> resultado = clienteRepository.findByCpf("12345678901");

        assertTrue(resultado.isPresent());
        assertEquals("Eduardo Alves", resultado.get().getNome());
        assertEquals("12345678901", resultado.get().getCpf());
    }
}
