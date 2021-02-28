package com.algaworks.algaworkscurso.domain.repository;

import com.algaworks.algaworkscurso.domain.model.Estado;
import com.algaworks.algaworkscurso.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> listar();
    Permissao buscar(Long id);
    Permissao salvar(Permissao permissao);
    void remover(Permissao permissao);
}
