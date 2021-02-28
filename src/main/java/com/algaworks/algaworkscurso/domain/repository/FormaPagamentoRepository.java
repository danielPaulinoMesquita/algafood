package com.algaworks.algaworkscurso.domain.repository;

import com.algaworks.algaworkscurso.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    List<FormaPagamento> listar();
    FormaPagamento buscar(Long id);
    FormaPagamento salvar(FormaPagamento cozinha);
    void remover(FormaPagamento cozinha);
}
