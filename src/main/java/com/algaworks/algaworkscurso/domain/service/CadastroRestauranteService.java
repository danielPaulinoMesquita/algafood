package com.algaworks.algaworkscurso.domain.service;

import com.algaworks.algaworkscurso.domain.exception.EntidadeEmUsoException;
import com.algaworks.algaworkscurso.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.algaworks.algaworkscurso.domain.model.Restaurante;
import com.algaworks.algaworkscurso.domain.repository.CozinhaRepository;
import com.algaworks.algaworkscurso.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId).get();

        if(cozinha == null){
            throw new EntidadeNaoEncontradaException(
                    String.format("N\u00E3o existe cadastro de cozinha com c\u00F3digo %d", cozinhaId));
        }
        return restauranteRepository.save(restaurante);
    }

    public void excluir(Long id){
        try {
            restauranteRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("N\u00E3o existe um cadastro de restaurante com c\u00F3digo %d", id));
        }catch (DataIntegrityViolationException e){
            throw  new EntidadeEmUsoException(String.format("Restaurante de codigo %d n\u00E3o pode ser removida, pois est\u00E1 em uso", id));
        }
    }
}
