package com.algaworks.algaworkscurso.jpa;

import com.algaworks.algaworkscurso.AlgaworkscursoApplication;
import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.algaworks.algaworkscurso.domain.model.Restaurante;
import com.algaworks.algaworkscurso.domain.repository.CozinhaRepository;
import com.algaworks.algaworkscurso.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class RestauranteCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworkscursoApplication.class)
                .web(WebApplicationType.NONE).run(args);

        RestauranteRepository cadastroCozinha = applicationContext.getBean(RestauranteRepository.class);

        List<Restaurante> restaurantes = cadastroCozinha.findAll();

        for(Restaurante restaurante : restaurantes){
            System.out.println("Restaurante: "+restaurante.getNome()+" Cozinha: "+restaurante.getCozinha().getNome());
        }
    }
}
