package com.example.demo.business.repositories;

import com.example.demo.business.models.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
