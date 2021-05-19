package com.algaworks.algaworkscurso.domain.repository;

import com.algaworks.algaworkscurso.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository orientado a persistência
 */
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    List<Cozinha> findCozinhaByNome(String nome);

}
