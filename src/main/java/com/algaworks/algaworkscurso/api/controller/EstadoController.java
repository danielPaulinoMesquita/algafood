package com.algaworks.algaworkscurso.api.controller;

import com.algaworks.algaworkscurso.domain.exception.EntidadeEmUsoException;
import com.algaworks.algaworkscurso.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworkscurso.domain.model.Estado;
import com.algaworks.algaworkscurso.domain.model.Estado;
import com.algaworks.algaworkscurso.domain.repository.EstadoRepository;
import com.algaworks.algaworkscurso.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Estado> listar() {
        return estadoRepository.listar();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscarResponse(@PathVariable("estadoId") Long estadoId) {
        Estado estado = estadoRepository.buscar(estadoId);
        if (estado != null)
            return ResponseEntity.ok(estado); // <-- Correto

        return ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Estado estado){
        try {
            estado = cadastroEstadoService.salvar(estado);
            return ResponseEntity.status(HttpStatus.CREATED).body(estado);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {
        Estado estadoAtual = estadoRepository.buscar(estadoId);
        if (estadoAtual != null){
            BeanUtils.copyProperties(estado, estadoAtual, "id");
            cadastroEstadoService.salvar(estadoAtual);
            return ResponseEntity.ok(estadoAtual);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<Estado> remover(@PathVariable Long estadoId) {
        try {
            cadastroEstadoService.excluir(estadoId);
            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
