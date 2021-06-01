package com.algaworks.algaworkscurso.infrastructure.repository;

import com.algaworks.algaworkscurso.domain.model.Restaurante;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl {

    @PersistenceContext
    private EntityManager manager;

    public List<Restaurante> find(String nome,
                                  BigDecimal taxaFreteInicial,
                                  BigDecimal taxaFreteFinal){
        var jpql = "from Restaurante where nome like :nome "
                + "and taxaFrete between :taxaFreteInicial and :taxaFreteFinal";

        return manager.createQuery(jpql, Restaurante.class)
                .setParameter("nome","%" + nome + "%")
                .setParameter("taxaFreteInicial", taxaFreteInicial)
                .setParameter("taxaFreteFinal", taxaFreteFinal)
                .getResultList();
    }
}
