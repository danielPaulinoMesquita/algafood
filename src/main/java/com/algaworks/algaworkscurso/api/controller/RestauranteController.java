package com.algaworks.algaworkscurso.api.controller;

import com.algaworks.algaworkscurso.domain.exception.EntidadeEmUsoException;
import com.algaworks.algaworkscurso.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworkscurso.domain.model.Restaurante;
import com.algaworks.algaworkscurso.domain.repository.RestauranteRepository;
import com.algaworks.algaworkscurso.domain.service.CadastroRestauranteService;
import static com.algaworks.algaworkscurso.infrastructure.repository.spec.RestauranteSpecs.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscarResponse(@PathVariable("restauranteId") Long restauranteId) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
        if (restaurante.isPresent())
            return ResponseEntity.ok(restaurante.get()); // <-- Correto

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
    public ResponseEntity<Restaurante> atualizar(@PathVariable Long restauranteId, @RequestBody Optional<Restaurante> restaurante) {
        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
        if (restauranteAtual.isPresent()){
            BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
            cadastroRestauranteService.salvar(restauranteAtual.get());
            return ResponseEntity.ok(restauranteAtual.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
        if (restauranteAtual.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        merge(campos, restauranteAtual);

        return atualizar(restauranteId,restauranteAtual);
    }

    private void merge(@RequestBody Map<String, Object> campos, Optional<Restaurante> restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);

        System.out.println(restauranteOrigem);

        campos.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            System.out.println(nomePropriedade + " = " + valorPropriedade + " novoValor =  "+ novoValor);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
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

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/por-taxa-frete")
    public ResponseEntity<List<Restaurante>> buscarResponse(@RequestParam("taxaFreteInicial") BigDecimal taxaFreteInicial,
                                                      @RequestParam("taxaFreteFinal") BigDecimal taxaFreteFinal) {
        List<Restaurante> restaurantes = restauranteRepository.findByTaxaFreteBetween(taxaFreteInicial,taxaFreteFinal);
        if (restaurantes.size() > 0)
            return ResponseEntity.ok(restaurantes); // <-- Correto

        return ResponseEntity.notFound().build();

    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/por-nome-e-cozinha")
    public ResponseEntity<List<Restaurante>> buscarResponse(@RequestParam("nome") String nome,
                                                            @RequestParam("CozinhaId") Long id) {
        List<Restaurante> restaurantes = restauranteRepository.queryByNomeAndCozinha(nome,id);
        if (restaurantes.size() > 0)
            return ResponseEntity.ok(restaurantes); // <-- Correto

        return ResponseEntity.notFound().build();

    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/por-nome")
    public Optional<Restaurante> buscarPrimeiroRestaurante(@RequestParam("nome") String nome) {
        return restauranteRepository.findFirstByNomeContaining(nome);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/existe")
    public ResponseEntity<Boolean> existe(@RequestParam("nome") String nome) {
        return ResponseEntity.ok(restauranteRepository.existsByNome(nome));
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/quantidade-restaurante")
    public long quantidadeRestaurante(@RequestParam("idCozinha") Long id) {
        return restauranteRepository.countByCozinha_Id(id);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/consultar-nome-e-cozinha")
    public ResponseEntity<List<Restaurante>> consultarPorNome(String nome, Long  idCozinha){
        List<Restaurante> restaurantes = restauranteRepository.consultarPorNome(nome, idCozinha);

        if (restaurantes.size() > 0){
            return ResponseEntity.ok(restaurantes);
        }else {
            return new ResponseEntity<>(restaurantes, HttpStatus.NO_CONTENT);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/por-nome-e-frete")
    public List<Restaurante> existe(String nome,
                                     BigDecimal taxaFreteInicial,  BigDecimal taxaFreteFinal) {
        return restauranteRepository.find(nome,taxaFreteInicial,taxaFreteFinal);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/com-frete-gratis")
    public List<Restaurante> restauranteComFreteGratis(String nome) {
        return restauranteRepository.findComFreteGratis(nome);
    }
}
