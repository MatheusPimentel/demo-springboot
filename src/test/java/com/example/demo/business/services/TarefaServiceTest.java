package com.example.demo.business.services;

import com.example.demo.business.mappers.TarefaMapper;
import com.example.demo.business.models.Projeto;
import com.example.demo.business.models.Tarefa;
import com.example.demo.business.models.dtos.TarefaRequestDTO;
import com.example.demo.business.models.dtos.TarefaResponseDTO;
import com.example.demo.business.repositories.ProjetoRepository;
import com.example.demo.business.repositories.TarefaRepository;
import com.example.demo.exceptions.domain.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;
    @Mock
    private ProjetoRepository projetoRepository;
    @Mock
    private TarefaMapper tarefaMapper;
    @InjectMocks
    private TarefaService tarefaService;

    @Test
    void deveSalvarTarefaComProjetoValido() {
        // 1. Arrange (Cenário)
        TarefaRequestDTO requestDTO = new TarefaRequestDTO("Título", "Descrição", 1L, true);
        Projeto projetoExistente = new Projeto();
        projetoExistente.setId(1L);
        Tarefa tarefaMapeada = new Tarefa();
        Tarefa tarefaSalva = new Tarefa();
        tarefaSalva.setId(10L);
        TarefaResponseDTO responseDTO = new TarefaResponseDTO(10L, "Título", "Descrição", true);

        // Programando os mocks
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projetoExistente));
        when(tarefaMapper.toEntity(requestDTO)).thenReturn(tarefaMapeada);
        when(tarefaRepository.save(tarefaMapeada)).thenReturn(tarefaSalva);
        when(tarefaMapper.toDto(tarefaSalva)).thenReturn(responseDTO);

        // 2. Act (Ação)
        TarefaResponseDTO resultado = tarefaService.saveTarefa(requestDTO);

        // 3. Assert (Verificação)
        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(10L);
        assertThat(resultado.titulo()).isEqualTo("Título");
    }

    @Test
    void deveLancarExcecaoQuandoProjetoNaoEncontradoAoSalvar() {
        TarefaRequestDTO dto = new TarefaRequestDTO("Título", "Descrição", 99L, true);

        when(tarefaMapper.toEntity(dto)).thenReturn(new Tarefa());

        when(projetoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tarefaService.saveTarefa(dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Projeto com ID 99 não encontrado.");
    }

    @Test
    void deveBuscarTarefaPorId() {
        // Arrange
        Tarefa tarefaDoBanco = new Tarefa();
        tarefaDoBanco.setId(1L);
        TarefaResponseDTO responseDTO = new TarefaResponseDTO(1L, "Teste", "Descrição", false);

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefaDoBanco));
        when(tarefaMapper.toDto(tarefaDoBanco)).thenReturn(responseDTO);

        // Act
        TarefaResponseDTO resultado = tarefaService.findTarefaById(1L);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(1L);
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoEncontrada() {
        // Arrange
        when(tarefaRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> tarefaService.findTarefaById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Tarefa com ID 99 não encontrada");
    }

    @Test
    void deveDeletarTarefaExistente() {
        // Arrange
        Tarefa tarefaExistente = new Tarefa();
        tarefaExistente.setId(1L);
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefaExistente));
        doNothing().when(tarefaRepository).deleteById(1L);

        // Act
        tarefaService.deleteTarefa(1L);

        // Assert
        verify(tarefaRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarTarefaInexistente() {
        // Arrange
        when(tarefaRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> tarefaService.deleteTarefa(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Tarefa com ID 99 não encontrada");
    }

    @Test
    void deveRetornarTodasAsTarefasPaginado() {
        // Arrange
        Tarefa tarefaDoBanco = new Tarefa();
        tarefaDoBanco.setId(1L);
        Page<Tarefa> paginaDeTarefas = new PageImpl<>(List.of(tarefaDoBanco));
        TarefaResponseDTO responseDTO = new TarefaResponseDTO(1L, "Título", "Descrição", true);
        Pageable pageable = PageRequest.of(0, 10);

        when(tarefaRepository.findAll(any(Pageable.class))).thenReturn(paginaDeTarefas);
        when(tarefaMapper.toDto(any(Tarefa.class))).thenReturn(responseDTO);

        // Act
        Page<TarefaResponseDTO> resultado = tarefaService.findTarefas(pageable);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().get(0).id()).isEqualTo(1L);
    }

    @Test
    void deveAtualizarTarefa() {
        // Arrange
        TarefaRequestDTO requestDTO = new TarefaRequestDTO("Atualizado", "Descrição", 1L, true);
        Tarefa tarefaExistente = new Tarefa();
        tarefaExistente.setId(1L);
        Projeto projetoExistente = new Projeto();
        projetoExistente.setId(1L);
        TarefaResponseDTO responseDTO = new TarefaResponseDTO(1L, "Atualizado", "Descrição", true);

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefaExistente));
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projetoExistente));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaExistente);
        when(tarefaMapper.toDto(any(Tarefa.class))).thenReturn(responseDTO);

        // Act
        TarefaResponseDTO resultado = tarefaService.updateTarefa(1L, requestDTO);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.titulo()).isEqualTo("Atualizado");
        verify(tarefaMapper, times(1)).updateTarefaFromRequestDto(requestDTO, tarefaExistente);
    }
}