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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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

        Mockito.when(projetoService.saveProjeto(any())).thenReturn(response);

        mockMvc.perform(post("/projetos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Nome"));
    }

    @Test
    void deveAtualizarProjeto() throws Exception {
        ProjetoRequestDTO request = new ProjetoRequestDTO("Novo", "Novo");
        ProjetoResponseDTO response = new ProjetoResponseDTO(1L, "Novo", "Novo", List.of());

        Mockito.when(projetoService.updateProjeto(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/projetos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo"));
    }

    @Test
    void deveBuscarProjetoPorId() throws Exception {
        ProjetoResponseDTO response = new ProjetoResponseDTO(1L, "Nome", "Descrição", List.of());

        Mockito.when(projetoService.findProjetoById(1L)).thenReturn(response);

        mockMvc.perform(get("/projetos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deveListarProjetos() throws Exception {
        ProjetoResponseDTO response = new ProjetoResponseDTO(1L, "Nome", "Descrição", List.of());

        Mockito.when(projetoService.findProjetos()).thenReturn(List.of(response));

        mockMvc.perform(get("/projetos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void deveDeletarProjeto() throws Exception {
        mockMvc.perform(delete("/projetos/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(projetoService).deleteProjeto(1L);
    }
}