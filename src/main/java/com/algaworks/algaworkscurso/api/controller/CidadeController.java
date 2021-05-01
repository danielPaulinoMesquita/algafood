package com.algaworks.algaworkscurso.api.controller;

import com.algaworks.algaworkscurso.domain.exception.EntidadeEmUsoException;
import com.algaworks.algaworkscurso.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworkscurso.domain.model.Cidade;
import com.algaworks.algaworkscurso.domain.repository.CidadeRepository;
import com.algaworks.algaworkscurso.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cidade> listar() {
        return cidadeRepository.listar();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{cidadeId}")
    public ResponseEntity<Cidade> buscarResponse(@PathVariable("cidadeId") Long cidadeId) {
        Cidade cidade = cidadeRepository.buscar(cidadeId);
        if (cidade != null)
            return ResponseEntity.ok(cidade); // <-- Correto

        return ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade){
        try {
            cidade = cadastroCidadeService.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cidade> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
        Cidade cidadeAtual = cidadeRepository.buscar(cidadeId);
        if (cidadeAtual != null){
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
            cadastroCidadeService.salvar(cidadeAtual);
            return ResponseEntity.ok(cidadeAtual);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Cidade> remover(@PathVariable Long cidadeId) {
        try {
            cadastroCidadeService.excluir(cidadeId);
            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
