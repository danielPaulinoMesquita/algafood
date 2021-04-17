package com.algaworks.algaworkscurso.api.controller;

import com.algaworks.algaworkscurso.api.model.CozinhasXmlWrapper;
import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.algaworks.algaworkscurso.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping
    public List<Cozinha> listar(){
        return cozinhaRepository.listar();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml(){
        return new CozinhasXmlWrapper(cozinhaRepository.listar());
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @GetMapping("/{cozinhaId}")
//    public Cozinha buscar(@PathVariable("cozinhaId") Long cozinhaId){
//        return cozinhaRepository.buscar(cozinhaId);
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscarResponse(@PathVariable("cozinhaId") Long cozinhaId) {
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
        return ResponseEntity.status(HttpStatus.OK).body(cozinha);
    }
}
