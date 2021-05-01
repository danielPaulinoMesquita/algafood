package com.algaworks.algaworkscurso.api.controller;

import com.algaworks.algaworkscurso.domain.exception.EntidadeEmUsoException;
import com.algaworks.algaworkscurso.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworkscurso.domain.model.Restaurante;
import com.algaworks.algaworkscurso.domain.repository.RestauranteRepository;
import com.algaworks.algaworkscurso.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar(){
        return restauranteRepository.listar();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscarResponse(@PathVariable("restauranteId") Long restauranteId) {
        Restaurante restaurante = restauranteRepository.buscar(restauranteId);
        if (restaurante != null)
            return ResponseEntity.ok(restaurante); // <-- Correto

        return ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){
        try {
            restaurante = cadastroRestauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurante> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
        Restaurante restauranteAtual = restauranteRepository.buscar(restauranteId);
        if (restauranteAtual != null){
            BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
            cadastroRestauranteService.salvar(restauranteAtual);
            return ResponseEntity.ok(restauranteAtual);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
        Restaurante restauranteAtual = restauranteRepository.buscar(restauranteId);
        if (restauranteAtual == null){
            return ResponseEntity.notFound().build();
        }
        merge(campos, restauranteAtual);

        return atualizar(restauranteId,restauranteAtual);
    }

    private void merge(@RequestBody Map<String, Object> campos, Restaurante restauranteAtual) {
        campos.forEach((chave, valor) -> System.out.println(chave + " = "+valor));
    }

    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId) {
        try {
            cadastroRestauranteService.excluir(restauranteId);
            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
