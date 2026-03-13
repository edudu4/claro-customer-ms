package com.github.edudu4.claro_customer_ms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.edudu4.claro_customer_ms.dto.ClienteDTO;
import com.github.edudu4.claro_customer_ms.exception.ClienteNaoEncontradoException;
import com.github.edudu4.claro_customer_ms.service.ClienteService;
import com.github.edudu4.claro_customer_ms.utils.ClienteUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    private ClienteDTO entrada;
    private ClienteDTO retorno;

    @BeforeEach
    void setUp() {
        entrada = ClienteUtil.getClienteDTO();
        retorno = ClienteUtil.getClienteDTO();
    }


    @Test
    void deveBuscarClientePorId() throws Exception {
        retorno.setId(1L);
        when(clienteService.buscar(1L)).thenReturn(retorno);

        mockMvc.perform(get("/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("Eduardo Alves"))
                .andExpect(jsonPath("$.cpf").value("12345678901"));
    }

    @Test
    void deveRetornar404QuandoIdForInvalido() throws Exception {
        doThrow(ClienteNaoEncontradoException.class).when(clienteService).buscar(1L);

        mockMvc.perform(get("/cliente/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveBuscarClientePorCpf() throws Exception {
        when(clienteService.buscarPorCpf("12345678901")).thenReturn(retorno);

        mockMvc.perform(get("/cliente/cpf/12345678901"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Eduardo Alves"))
                .andExpect(jsonPath("$.cpf").value("12345678901"));
    }

    @Test
    void deveRetornar404QuandoCpfForInvalido() throws Exception {
        doThrow(ClienteNaoEncontradoException.class).when(clienteService).buscarPorCpf(anyString());

        mockMvc.perform(get("/cliente/cpf/1234567890")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveCriarClienteERetornar201() throws Exception {
        retorno.setId(1L);

        when(clienteService.criar(any(ClienteDTO.class))).thenReturn(retorno);

        mockMvc.perform(post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Eduardo Alves"))
                .andExpect(jsonPath("$.cpf").value("12345678901"));
    }


    @Test
    void deveRetornar400QuandoCriarTiverCampoForInvalido() throws Exception {
        entrada.setCpf("123456789");

        mockMvc.perform(post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("cpf: CPF deve conter 11 dígitos"));
    }


    @Test
    void deveAtualizarClienteERetornar200() throws Exception {
        retorno.setId(1L);
        retorno.setNome("Eduardo Atualizado");
        retorno.setEmail("eduardo.atualizado@email.com");
        when(clienteService.atualizar(any(ClienteDTO.class))).thenReturn(retorno);

        mockMvc.perform(put("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Eduardo Atualizado"))
                .andExpect(jsonPath("$.email").value("eduardo.atualizado@email.com"));
    }

    @Test
    void deveRetornar400AoAtualizarComEmailInvalido() throws Exception {
        entrada.setEmail("teste");

        mockMvc.perform(put("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("email: Email inválido"));
    }

    @Test
    void deveDeletarClienteERetornar204() throws Exception {
        doNothing().when(clienteService).deletar(1L);

        mockMvc.perform(delete("/cliente/1"))
                .andExpect(status().isNoContent());
    }
}