package com.algaworks.algaworkscurso.infrastructure.repository;

import com.algaworks.algaworkscurso.domain.model.FormaPagamento;
import com.algaworks.algaworkscurso.domain.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FormaPagamento> listar() {
        TypedQuery<FormaPagamento> formaPagamentos = entityManager
                .createQuery("from FormaPagamento",FormaPagamento.class);

        return formaPagamentos.getResultList();
    }

    @Override
    public FormaPagamento buscar(Long id) {
        return entityManager.find(FormaPagamento.class, id);
    }

    @Transactional
    @Override
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return entityManager.merge(formaPagamento);
    }

    @Transactional
    @Override
    public void remover(FormaPagamento formaPagamento) {
        formaPagamento = buscar(formaPagamento.getId());
        entityManager.remove(formaPagamento);
    }
}
