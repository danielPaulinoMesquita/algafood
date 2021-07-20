package com.algaworks.algaworkscurso.infrastructure.repository;

import com.algaworks.algaworkscurso.domain.model.Restaurante;
import com.algaworks.algaworkscurso.domain.repository.RestauranteRepository;
import com.algaworks.algaworkscurso.domain.repository.RestauranteRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.algaworks.algaworkscurso.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algaworkscurso.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(String nome,
                                  BigDecimal taxaFreteInicial,
                                  BigDecimal taxaFreteFinal) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        // builder vai ser o construtor para o Criteria

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root =  criteria.from(Restaurante.class); // from Restaurante
        // Root é como se indicasse a classe raiz ou ponto de partida para o Criteria

        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.hasText(nome)){
            Predicate nomePredicate = builder.like(root.get("nome"), "%"+nome+"%");
            predicates.add(nomePredicate);
        }

        if (taxaFreteInicial!= null){
            Predicate taxaInicialPredicate = builder.greaterThanOrEqualTo(root.get("taxaFrete"),taxaFreteInicial);
            predicates.add(taxaInicialPredicate);
        }

        if (taxaFreteFinal != null){
            Predicate taxaFinalPredicate = builder.lessThanOrEqualTo(root.get("taxaFrete"),taxaFreteFinal);
            predicates.add(taxaFinalPredicate);
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }
}
