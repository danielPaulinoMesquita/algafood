package com.algaworks.algaworkscurso.domain.service;

import com.algaworks.algaworkscurso.domain.exception.EntidadeEmUsoException;
import com.algaworks.algaworkscurso.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworkscurso.domain.model.Estado;
import com.algaworks.algaworkscurso.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado){
        return estadoRepository.salvar(estado);
    }

    public void excluir(Long id){
        try {
            estadoRepository.remover(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("N\u00E3o existe um cadastro de estado com c\u00F3digo %d", id));
        }catch (DataIntegrityViolationException e){
            throw  new EntidadeEmUsoException(String.format("Estado de codigo %d n\u00E3o pode ser removida, pois est\u00E1 em uso", id));
        }
    }
}
