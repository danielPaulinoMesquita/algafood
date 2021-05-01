package com.algaworks.algaworkscurso.domain.service;

import com.algaworks.algaworkscurso.domain.exception.EntidadeEmUsoException;
import com.algaworks.algaworkscurso.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworkscurso.domain.model.Cidade;
import com.algaworks.algaworkscurso.domain.model.Estado;
import com.algaworks.algaworkscurso.domain.repository.CidadeRepository;
import com.algaworks.algaworkscurso.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade){
        Long estadoId= cidade.getEstado().getId();
        Estado estado = estadoRepository.buscar(estadoId);

        if (estado == null){
            throw new EntidadeNaoEncontradaException(
                    String.format("N\u00E3o existe cadastro de estado com c\u00F3digo %d", estadoId));
        }
        return cidadeRepository.salvar(cidade);
    }

    public void excluir(Long id){
        try {
            cidadeRepository.remover(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("N\u00E3o existe um cadastro de cidade com c\u00F3digo %d", id));
        }catch (DataIntegrityViolationException e){
            throw  new EntidadeEmUsoException(String.format("Cidade de codigo %d n\u00E3o pode ser removida, pois est\u00E1 em uso", id));
        }
    }
}
