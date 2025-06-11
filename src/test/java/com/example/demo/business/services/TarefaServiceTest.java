package com.example.demo.business.services;

import com.example.demo.business.models.Projeto;
import com.example.demo.business.models.Tarefa;
import com.example.demo.business.models.dtos.TarefaRequestDTO;
import com.example.demo.business.repositories.ProjetoRepository;
import com.example.demo.business.repositories.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;
    @Mock
    private ProjetoRepository projetoRepository;
    @InjectMocks
    private TarefaService tarefaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarTarefaComProjetoValido() {
        TarefaRequestDTO dto = new TarefaRequestDTO("Título", "Descrição", 1L, true);
        Projeto projeto = new Projeto();
        projeto.setId(1L);

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        when(tarefaRepository.save(any(Tarefa.class))).thenAnswer(i -> i.getArgument(0));

        var response = tarefaService.saveTarefa(dto);

        assertThat(response).isNotNull();
        assertThat(response.titulo()).isEqualTo("Título");
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void deveLancarExcecaoQuandoProjetoNaoEncontradoAoSalvar() {
        TarefaRequestDTO dto = new TarefaRequestDTO("Título", "Descrição", 99L, true);

        when(projetoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tarefaService.saveTarefa(dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Projeto não encontrado");
    }

    @Test
    void deveBuscarTarefaPorId() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setTitulo("Teste");

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));

        var response = tarefaService.findTarefaById(1L);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoEncontrada() {
        when(tarefaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tarefaService.findTarefaById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Tarefa não encontrada");
    }

    @Test
    void deveDeletarTarefaExistente() {
        when(tarefaRepository.existsById(1L)).thenReturn(true);

        tarefaService.deleteTarefa(1L);

        verify(tarefaRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarTarefaInexistente() {
        when(tarefaRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> tarefaService.deleteTarefa(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Tarefa não encontrada");
    }

    @Test
    void deveRetornarTodasTarefas() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setTitulo("Título");

        when(tarefaRepository.findAll()).thenReturn(List.of(tarefa));

        var tarefas = tarefaService.findTarefas();

        assertThat(tarefas).hasSize(1);
        assertThat(tarefas.get(0).id()).isEqualTo(1L);
    }

    @Test
    void deveAtualizarTarefa() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);

        TarefaRequestDTO dto = new TarefaRequestDTO("Atualizado", "Descrição", 1L, true);

        Projeto projeto = new Projeto();
        projeto.setId(1L);

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        when(tarefaRepository.save(any(Tarefa.class))).thenAnswer(i -> i.getArgument(0));

        var response = tarefaService.updateTarefa(1L, dto);

        assertThat(response).isNotNull();
        assertThat(response.titulo()).isEqualTo("Atualizado");
    }
}
