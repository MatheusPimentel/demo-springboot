package com.example.demo.business.controllers;

import com.example.demo.business.models.dtos.ProjetoRequestDTO;
import com.example.demo.business.models.dtos.ProjetoResponseDTO;
import com.example.demo.business.services.ProjetoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjetoController.class)
class ProjetoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjetoService projetoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarProjeto() throws Exception {
        ProjetoRequestDTO request = new ProjetoRequestDTO("Nome", "Descrição");
        ProjetoResponseDTO response = new ProjetoResponseDTO(1L, "Nome", "Descrição", List.of());

        Mockito.when(projetoService.save(any())).thenReturn(response);

        mockMvc.perform(post("/projetos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // 1. A asserção principal foi alterada de .isOk() para .isCreated()
                .andExpect(status().isCreated())
                // 2. Adicionamos uma verificação do cabeçalho "Location"
                .andExpect(header().string("Location", "http://localhost/projetos/1"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Nome")));
    }

    @Test
    void deveListarProjetos() throws Exception {
        ProjetoResponseDTO response = new ProjetoResponseDTO(1L, "Nome", "Descrição", List.of());
        Page<ProjetoResponseDTO> paginaDeProjetos = new PageImpl<>(List.of(response));

        Mockito.when(projetoService.findAll(any(Pageable.class))).thenReturn(paginaDeProjetos);

        mockMvc.perform(get("/projetos")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                // 4. Ajustar as asserções JSON para a estrutura de um objeto Page
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].nome", is("Nome")));
    }

    @Test
    void deveAtualizarProjeto() throws Exception {
        ProjetoRequestDTO request = new ProjetoRequestDTO("Novo", "Novo");
        ProjetoResponseDTO response = new ProjetoResponseDTO(1L, "Novo", "Novo", List.of());

        Mockito.when(projetoService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/projetos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Novo")));
    }

    @Test
    void deveBuscarProjetoPorId() throws Exception {
        ProjetoResponseDTO response = new ProjetoResponseDTO(1L, "Nome", "Descrição", List.of());

        Mockito.when(projetoService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/projetos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void deveDeletarProjeto() throws Exception {
        mockMvc.perform(delete("/projetos/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(projetoService).delete(1L);
    }
}