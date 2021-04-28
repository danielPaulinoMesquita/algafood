package com.algaworks.algaworkscurso.domain.repository;

import com.algaworks.algaworkscurso.domain.model.Cozinha;

import java.util.List;

/**
 * Repository orientado a persistência
 */
public interface CozinhaRepository {

    List<Cozinha> listar();
    Cozinha buscar(Long id);
    Cozinha salvar(Cozinha cozinha);
    void remover(Long id);

}
