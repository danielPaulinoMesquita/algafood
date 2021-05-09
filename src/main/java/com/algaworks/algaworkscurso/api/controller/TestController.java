package com.algaworks.algaworkscurso.api.controller;

import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.algaworks.algaworkscurso.domain.repository.CozinhaRepository;
import com.algaworks.algaworkscurso.infrastructure.repository.CozinhaRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teste")
public class TestController {

    @Autowired
    private CozinhaRepositoryImpl cozinhaRepository;

    /*Você pode pegar uma paramentro por QueryString ou QueryParam utilizando o @RequestParam
    * exemplo de uri com queryParam:  */
    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(@RequestParam String nome){
        return cozinhaRepository.buscarPorNome(nome);
    }
}
