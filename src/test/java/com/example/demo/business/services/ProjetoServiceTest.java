package com.example.demo.business.services;

import com.example.demo.business.mappers.ProjetoMapper;
import com.example.demo.business.models.Projeto;
import com.example.demo.business.models.dtos.ProjetoRequestDTO;
import com.example.demo.business.models.dtos.ProjetoResponseDTO;
import com.example.demo.business.repositories.ProjetoRepository;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetoServiceTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private ProjetoMapper projetoMapper;

    @InjectMocks
    private ProjetoService projetoService;

    @Test
    void deveSalvarProjetoComSucesso() {
        // 1. Arrange (Cenário)
        ProjetoRequestDTO requestDTO = new ProjetoRequestDTO("Nome", "Descrição");

        Projeto projetoMapeado = new Projeto(); // O objeto que o mapper "criaria"
        Projeto projetoSalvo = new Projeto();   // O objeto que o repo "salvaria"
        projetoSalvo.setId(1L);
        ProjetoResponseDTO responseDTO = new ProjetoResponseDTO(1L, "Nome", "Descrição", Collections.emptyList());

        // Programando os mocks
        when(projetoMapper.toEntity(any(ProjetoRequestDTO.class))).thenReturn(projetoMapeado);
        when(projetoRepository.save(any(Projeto.class))).thenReturn(projetoSalvo);
        when(projetoMapper.toDto(any(Projeto.class))).thenReturn(responseDTO);

        // 2. Act (Ação)
        ProjetoResponseDTO resultado = projetoService.saveProjeto(requestDTO);

        // 3. Assert (Verificação)
        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(1L);
    }

    @Test
    void deveRetornarProjetoPorId() {
        // Arrange
        Projeto projetoDoBanco = new Projeto();
        projetoDoBanco.setId(1L);
        ProjetoResponseDTO responseDTO = new ProjetoResponseDTO(1L, "Nome", "Descrição", Collections.emptyList());

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projetoDoBanco));
        when(projetoMapper.toDto(any(Projeto.class))).thenReturn(responseDTO);

        // Act
        ProjetoResponseDTO resultado = projetoService.findProjetoById(1L);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(1L);
    }

    @Test
    void deveLancarExcecao_QuandoProjetoNaoEncontradoPorId() {
        when(projetoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projetoService.findProjetoById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Projeto com ID 99 não encontrado.");
    }

    @Test
    void deveRetornarListaDeProjetosPaginada() {
        Projeto projetoDoBanco = new Projeto();
        Page<Projeto> paginaDeProjetos = new PageImpl<>(List.of(projetoDoBanco));
        ProjetoResponseDTO responseDTO = new ProjetoResponseDTO(1L, "Nome", "Descrição", Collections.emptyList());
        Pageable pageable = PageRequest.of(0, 10);

        when(projetoRepository.findAll(any(Pageable.class))).thenReturn(paginaDeProjetos);
        when(projetoMapper.toDto(any(Projeto.class))).thenReturn(responseDTO);

        Page<ProjetoResponseDTO> resultado = projetoService.findProjetos(pageable);

        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().get(0).id()).isEqualTo(1L);
    }

    @Test
    void deveAtualizarProjetoComSucesso() {
        ProjetoRequestDTO requestDTO = new ProjetoRequestDTO("Novo Nome", "Nova Descrição");
        Projeto projetoExistente = new Projeto();
        projetoExistente.setId(1L);
        ProjetoResponseDTO responseDTO = new ProjetoResponseDTO(1L, "Novo Nome", "Nova Descrição", Collections.emptyList());

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projetoExistente));
        when(projetoRepository.save(any(Projeto.class))).thenReturn(projetoExistente); // O save retorna a entidade atualizada
        when(projetoMapper.toDto(any(Projeto.class))).thenReturn(responseDTO);

        ProjetoResponseDTO resultado = projetoService.updateProjeto(1L, requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.nome()).isEqualTo("Novo Nome");
        verify(projetoMapper, times(1)).updateProjetoFromRequestDto(requestDTO, projetoExistente);
    }

    @Test
    void deveLancarExcecao_ProjetoNaoEncontradoParaAtualizar() {
        ProjetoRequestDTO requestDTO = new ProjetoRequestDTO("Novo", "Novo");
        when(projetoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projetoService.updateProjeto(99L, requestDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Projeto com ID 99 não encontrado.");
    }

    @Test
    void deveDeletarProjetoExistente() {
        Projeto projetoExistente = new Projeto();
        projetoExistente.setId(1L);

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projetoExistente));

        doNothing().when(projetoRepository).deleteById(1L);

        projetoService.deleteProjeto(1L);

        verify(projetoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecao_AoDeletarProjetoInexistente() {
        lenient().when(projetoRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> projetoService.deleteProjeto(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Projeto com ID 99 não encontrado.");
    }
}