package com.algaworks.algaworkscurso.domain.repository;

import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.algaworks.algaworkscurso.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> queryByNomeAndCozinha(String nome, Long CozinhaId);

    Optional<Restaurante> findFirstByNomeContaining(String nome);

    boolean existsByNome(String nome);

    Long countByCozinha_Id(Long cozinhaId);

//    @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Restaurante> consultarPorNome(@Param("nome") String nome, @Param("id") Long id);

}
