package com.algaworks.algaworkscurso.api.controller;

import com.algaworks.algaworkscurso.api.model.CozinhasXmlWrapper;
import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.algaworks.algaworkscurso.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (cozinha != null)
            return ResponseEntity.ok(cozinha); // <-- Correto

        return ResponseEntity.notFound().build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return cozinhaRepository.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);
        if (cozinhaAtual != null){
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
            cozinhaRepository.salvar(cozinhaAtual);
            return ResponseEntity.ok(cozinhaAtual);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {
        try {
            Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
            if(cozinha != null) {
                cozinhaRepository.remover(cozinha);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();

        }catch (DataIntegrityViolationException dataIntegrityViolationException){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
