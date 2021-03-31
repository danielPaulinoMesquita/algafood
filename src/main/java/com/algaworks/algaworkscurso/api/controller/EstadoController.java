package com.algaworks.algaworkscurso.api.controller;

import com.algaworks.algaworkscurso.domain.model.Estado;
import com.algaworks.algaworkscurso.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Estado> listar() {
        return estadoRepository.listar();
    }

}
