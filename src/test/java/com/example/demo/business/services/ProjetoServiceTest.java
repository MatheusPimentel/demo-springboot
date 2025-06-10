package com.example.demo.business.services;

import com.example.demo.business.models.Projeto;
import com.example.demo.business.models.dtos.ProjetoRequestDTO;
import com.example.demo.business.models.dtos.ProjetoResponseDTO;
import com.example.demo.business.repositories.ProjetoRepository;
import com.example.demo.business.repositories.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjetoServiceTest {

    private ProjetoRepository projetoRepository;
    private TarefaRepository tarefaRepository;
    private ProjetoService projetoService;

    @BeforeEach
    void setUp() {
        projetoRepository = mock(ProjetoRepository.class);
        tarefaRepository = mock(TarefaRepository.class);
        projetoService = new ProjetoService(projetoRepository, tarefaRepository);
    }

    @Test
    void deveSalvarProjetoComSucesso() {
        ProjetoRequestDTO dto = new ProjetoRequestDTO("Nome", "Descrição");
        Projeto projetoSalvo = new Projeto(dto);
        projetoSalvo.setId(1L);

        when(projetoRepository.save(any())).thenReturn(projetoSalvo);

        ProjetoResponseDTO response = projetoService.saveProjeto(dto);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nome()).isEqualTo("Nome");
    }

    @Test
    void deveRetornarProjetoPorId() {
        Projeto projeto = new Projeto(new ProjetoRequestDTO("Nome", "Descricao"));
        projeto.setId(1L);

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        ProjetoResponseDTO response = projetoService.findProjetoById(1L);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nome()).isEqualTo("Nome");
    }

    @Test
    void deveLancarExcecao_ProjetoNaoEncontradoPorId() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projetoService.findProjetoById(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Projeto não encontrado");
    }

    @Test
    void deveRetornarListaDeProjetos() {
        Projeto projeto = new Projeto(new ProjetoRequestDTO("Nome", "Descricao"));
        projeto.setId(1L);

        when(projetoRepository.findAll()).thenReturn(List.of(projeto));

        List<ProjetoResponseDTO> projetos = projetoService.findProjetos();

        assertThat(projetos).hasSize(1);
        assertThat(projetos.get(0).id()).isEqualTo(1L);
    }

    @Test
    void deveAtualizarProjetoComSucesso() {
        Projeto projeto = new Projeto(new ProjetoRequestDTO("Antigo", "Antigo"));
        projeto.setId(1L);

        ProjetoRequestDTO novoDto = new ProjetoRequestDTO("Novo", "Novo");

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        when(projetoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        ProjetoResponseDTO atualizado = projetoService.updateProjeto(1L, novoDto);

        assertThat(atualizado.nome()).isEqualTo("Novo");
    }

    @Test
    void deveLancarExcecao_ProjetoNaoEncontradoParaAtualizar() {
        ProjetoRequestDTO novoDto = new ProjetoRequestDTO("Novo", "Novo");

        when(projetoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projetoService.updateProjeto(1L, novoDto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Projeto não encontrado");
    }

    @Test
    void deveDeletarProjetoExistente() {
        when(projetoRepository.existsById(1L)).thenReturn(true);

        projetoService.deleteProjeto(1L);

        verify(projetoRepository).deleteById(1L);
    }

    @Test
    void deveLancarExcecao_AoDeletarProjetoInexistente() {
        when(projetoRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> projetoService.deleteProjeto(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Projeto não encontrado");
    }
}
