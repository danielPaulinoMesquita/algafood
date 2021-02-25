package com.algaworks.algaworkscurso.jpa;

import com.algaworks.algaworkscurso.domain.model.Cozinha;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CadastroCozinha {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Consulta com entityManager e jpql
     * @return
     */
    public List<Cozinha> listar(){
       TypedQuery<Cozinha> cozinhas = entityManager
               .createQuery("from Cozinha",Cozinha.class);

       return cozinhas.getResultList();

    }

    /**
     * Serve também para atualizar ou alterar
     * @param cozinha
     * @return
     */
    @Transactional
    public Cozinha adicionar(Cozinha cozinha){
        return entityManager.merge(cozinha);
    }

    @Transactional
    public void remover(Cozinha cozinha){
         cozinha = buscar(cozinha.getId());
         entityManager.remove(cozinha);
    }
    public Cozinha buscar(Long id){
        return entityManager.find(Cozinha.class, id);
    }
}
