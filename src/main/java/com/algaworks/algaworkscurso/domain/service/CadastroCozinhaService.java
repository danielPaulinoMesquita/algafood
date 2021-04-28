package com.algaworks.algaworkscurso.domain.service;

import com.algaworks.algaworkscurso.domain.exception.EntidadeEmUsoException;
import com.algaworks.algaworkscurso.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.algaworks.algaworkscurso.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.salvar(cozinha);
    }

    public void excluir(Long id){
        try {
            cozinhaRepository.remover(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("N\u00E3o existe um cadastro de cozinha com c\u00F3digo %d", id));
        }catch (DataIntegrityViolationException e){
            throw  new EntidadeEmUsoException(String.format("Cozinha de codigo %d n\u00E3o pode ser removida, pois est\u00E1 em uso", id));
        }
    }
}
