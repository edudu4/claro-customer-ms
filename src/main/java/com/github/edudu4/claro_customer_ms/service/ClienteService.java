package com.github.edudu4.claro_customer_ms.service;

import com.github.edudu4.claro_customer_ms.dto.ClienteDTO;
import com.github.edudu4.claro_customer_ms.dto.ClienteResumo;
import com.github.edudu4.claro_customer_ms.entity.Cliente;
import com.github.edudu4.claro_customer_ms.exception.ClienteNaoEncontradoException;
import com.github.edudu4.claro_customer_ms.mapper.ClienteMapper;
import com.github.edudu4.claro_customer_ms.messaging.producer.ClienteProducer;
import com.github.edudu4.claro_customer_ms.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteProducer clienteProducer;

    public List<ClienteResumo> listar() {
        return clienteRepository.findAllProjectedBy();
    }

    public ClienteDTO buscar(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(ClienteNaoEncontradoException::new);
        return ClienteMapper.toDto(cliente);
    }

    public ClienteDTO buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).orElseThrow(ClienteNaoEncontradoException::new);
    }

    public ClienteDTO criar(ClienteDTO cliente) {
        Cliente clienteSalvo = clienteRepository.save(ClienteMapper.toEntity(cliente));
        clienteProducer.enviarClienteCriado(ClienteMapper.toEvent(clienteSalvo));
        return ClienteMapper.toDto(clienteSalvo);
    }

    public ClienteDTO atualizar(ClienteDTO cliente) {
        validarClienteExiste(cliente.getId());
        Cliente clienteAtualizado = clienteRepository.save(ClienteMapper.toEntity(cliente));
        return ClienteMapper.toDto(clienteAtualizado);
    }

    public void deletar(Long id) {
        validarClienteExiste(id);
        clienteRepository.deleteById(id);
    }

    private void validarClienteExiste(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNaoEncontradoException();
        }
    }
}
