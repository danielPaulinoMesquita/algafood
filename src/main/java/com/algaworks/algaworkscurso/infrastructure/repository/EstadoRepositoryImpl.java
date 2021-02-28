package com.algaworks.algaworkscurso.infrastructure.repository;

import com.algaworks.algaworkscurso.domain.model.Estado;
import com.algaworks.algaworkscurso.domain.repository.EstadoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Estado> listar() {
        TypedQuery<Estado> estados = entityManager
                .createQuery("from Estado",Estado.class);

        return estados.getResultList();
    }

    @Override
    public Estado buscar(Long id) {
        return entityManager.find(Estado.class, id);
    }

    @Transactional
    @Override
    public Estado salvar(Estado estado) {
        return entityManager.merge(estado);
    }

    @Transactional
    @Override
    public void remover(Estado estado) {
        estado = buscar(estado.getId());
        entityManager.remove(estado);
    }
}
