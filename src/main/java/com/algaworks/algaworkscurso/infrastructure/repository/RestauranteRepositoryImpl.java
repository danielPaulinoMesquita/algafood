package com.algaworks.algaworkscurso.infrastructure.repository;

import com.algaworks.algaworkscurso.domain.model.Restaurante;
import com.algaworks.algaworkscurso.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(String nome,
                                  BigDecimal taxaFreteInicial,
                                  BigDecimal taxaFreteFinal){
        var jpql = new StringBuilder();
        jpql.append("from Restaurante where 0=0 ");

        if(StringUtils.hasLength(nome)){
            jpql.append("and nome like :nome ");
        }

        if(taxaFreteInicial != null){
            jpql.append("and taxaFrete >= :taxaFreteInicial ");
        }
        if(taxaFreteFinal != null){
            jpql.append("and taxaFrete <= :taxaFreteFinal ");
        }

        return manager.createQuery(jpql.toString(), Restaurante.class)
                .setParameter("nome","%"+nome+"%")
                .setParameter("taxaFreteInicial",taxaFreteInicial)
                .setParameter("taxaFreteFinal", taxaFreteFinal)
                .getResultList();
    }
}
