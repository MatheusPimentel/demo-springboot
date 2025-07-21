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

        Mockito.when(tarefaService.save(any())).thenReturn(response);

        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/tarefas/1"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Título"));
    }

    @Test
    void deveAtualizarTarefa() throws Exception {
        TarefaRequestDTO request = new TarefaRequestDTO("Novo", "Novo", 1L, true);
        TarefaResponseDTO response = new TarefaResponseDTO(1L, "Novo", "Novo", true);

        Mockito.when(tarefaService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/tarefas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Novo"));
    }

    @Test
    void deveBuscarTarefaPorId() throws Exception {
        TarefaResponseDTO response = new TarefaResponseDTO(1L, "Título", "Descrição", true);

        Mockito.when(tarefaService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/tarefas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deveListarTarefas() throws Exception {
        TarefaResponseDTO response = new TarefaResponseDTO(1L, "Título", "Descrição", true);
        Page<TarefaResponseDTO> paginaDeTarefas = new PageImpl<>(List.of(response));

        Mockito.when(tarefaService.findAll(any(Pageable.class))).thenReturn(paginaDeTarefas);

        mockMvc.perform(get("/tarefas")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].titulo", is("Título")));
    }

    @Test
    void deveDeletarTarefa() throws Exception {
        mockMvc.perform(delete("/tarefas/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(tarefaService).delete(1L);
    }
}