package com.algaworks.algaworkscurso.domain.repository;

import com.algaworks.algaworkscurso.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> listar();
    Restaurante buscar(Long id);
    Restaurante salvar(Restaurante cozinha);
    void remover(Long id);

}
