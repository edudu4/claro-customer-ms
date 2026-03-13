package com.github.edudu4.claro_customer_ms.service;

import com.github.edudu4.claro_customer_ms.dto.ClienteDTO;
import com.github.edudu4.claro_customer_ms.entity.Cliente;
import com.github.edudu4.claro_customer_ms.exception.ClienteNaoEncontradoException;
import com.github.edudu4.claro_customer_ms.mapper.ClienteMapper;
import com.github.edudu4.claro_customer_ms.messaging.producer.ClienteProducer;
import com.github.edudu4.claro_customer_ms.repository.ClienteRepository;
import com.github.edudu4.claro_customer_ms.utils.ClienteUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteProducer clienteProducer;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setup() {
        cliente = ClienteUtil.getCliente();
        clienteDTO = ClienteUtil.getClienteDTO();
    }

    @Test
    void deveCriarClienteComSucesso() {
        cliente.setId(1L);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO resultado = clienteService.criar(clienteDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Eduardo Alves", resultado.getNome());
        assertEquals("12345678901", resultado.getCpf());

        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(clienteProducer, times(1)).enviarClienteCriado(any());
    }

    @Test
    void deveBuscarClientePorCpf() {
        when(clienteRepository.findByCpf("12345678901")).thenReturn(Optional.of(clienteDTO));

        ClienteDTO resultado = clienteService.buscarPorCpf("12345678901");

        assertNotNull(resultado);
        assertEquals("Eduardo Alves", resultado.getNome());
        assertEquals("12345678901", resultado.getCpf());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontradoPorCpf() {
        when(clienteRepository.findByCpf("12345678901")).thenReturn(Optional.empty());

        ClienteNaoEncontradoException exception = assertThrows(
                ClienteNaoEncontradoException.class,
                () -> clienteService.buscarPorCpf("12345678901")
        );

        assertEquals("Cliente não encontrado", exception.getMessage());
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        ClienteDTO clienteAtualizado = ClienteUtil.getClienteDTO();
        clienteAtualizado.setId(1L);
        clienteAtualizado.setNome("Eduardo Atualizado");
        clienteAtualizado.setEmail("atualizado@email.com");
        clienteAtualizado.setCpf("12345678901");
        clienteAtualizado.setTelefone("61999999999");

        when(clienteRepository.existsById(1L)).thenReturn(true);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(ClienteMapper.toEntity(clienteAtualizado));

        ClienteDTO resultado = clienteService.atualizar(clienteAtualizado);

        assertNotNull(resultado);
        assertEquals("Eduardo Atualizado", resultado.getNome());
        assertEquals("atualizado@email.com", resultado.getEmail());
        assertEquals("12345678901", resultado.getCpf());
        assertEquals("61999999999", resultado.getTelefone());

        verify(clienteRepository).existsById(1L);
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarClienteInexistente() {
        ClienteDTO clienteAtualizado = ClienteUtil.getClienteDTO();
        clienteAtualizado.setId(1L);
        clienteAtualizado.setNome("Eduardo Atualizado");
        clienteAtualizado.setEmail("atualizado@email.com");
        clienteAtualizado.setCpf("12345678901");
        clienteAtualizado.setTelefone("61999999999");

        when(clienteRepository.existsById(1L)).thenReturn(false);

        ClienteNaoEncontradoException exception = assertThrows(
                ClienteNaoEncontradoException.class,
                () -> clienteService.atualizar(clienteAtualizado)
        );

        assertEquals("Cliente não encontrado", exception.getMessage());

        verify(clienteRepository).existsById(1L);
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void deveDeletarClienteComSucesso() {
        when(clienteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(1L);

        assertDoesNotThrow(() -> clienteService.deletar(1L));

        verify(clienteRepository).existsById(1L);
        verify(clienteRepository).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarClienteInexistente() {
        when(clienteRepository.existsById(1L)).thenReturn(false);

        ClienteNaoEncontradoException exception = assertThrows(
                ClienteNaoEncontradoException.class,
                () -> clienteService.deletar(1L)
        );

        assertEquals("Cliente não encontrado", exception.getMessage());

        verify(clienteRepository).existsById(1L);
        verify(clienteRepository, never()).deleteById(anyLong());
    }
}