package com.algaworks.algaworkscurso.infrastructure.repository.spec;

import com.algaworks.algaworkscurso.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

public class RestauranteSpecs {

    public static Specification<Restaurante> comFreteGratis(){
        return new RestauranteComFreteGratisSpec();
    }
    public static Specification<Restaurante> comNomeSemelhante(String nome){
        return new RestauraneComNomeSemelhanteSpec(nome);
    }
}
