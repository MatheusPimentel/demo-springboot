package com.example.demo.business.controllers;

import com.example.demo.business.models.dtos.TarefaRequestDTO;
import com.example.demo.business.models.dtos.TarefaResponseDTO;
import com.example.demo.business.services.TarefaService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TarefaController.class)
class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TarefaService tarefaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarTarefa() throws Exception {
        TarefaRequestDTO request = new TarefaRequestDTO("Título", "Descrição", 1L, true);
        TarefaResponseDTO response = new TarefaResponseDTO(1L, "Título", "Descrição", true);

        Mockito.when(tarefaService.saveTarefa(any())).thenReturn(response);

        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Título"));
    }

    @Test
    void deveAtualizarTarefa() throws Exception {
        TarefaRequestDTO request = new TarefaRequestDTO("Novo", "Novo", 1L, true);
        TarefaResponseDTO response = new TarefaResponseDTO(1L, "Novo", "Novo", true);

        Mockito.when(tarefaService.updateTarefa(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/tarefas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Novo"));
    }

    @Test
    void deveBuscarTarefaPorId() throws Exception {
        TarefaResponseDTO response = new TarefaResponseDTO(1L, "Título", "Descrição", true);

        Mockito.when(tarefaService.findTarefaById(1L)).thenReturn(response);

        mockMvc.perform(get("/tarefas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deveListarTarefas() throws Exception {
        TarefaResponseDTO response = new TarefaResponseDTO(1L, "Título", "Descrição", true);

        Mockito.when(tarefaService.findTarefas()).thenReturn(List.of(response));

        mockMvc.perform(get("/tarefas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void deveDeletarTarefa() throws Exception {
        mockMvc.perform(delete("/tarefas/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(tarefaService).deleteTarefa(1L);
    }
}