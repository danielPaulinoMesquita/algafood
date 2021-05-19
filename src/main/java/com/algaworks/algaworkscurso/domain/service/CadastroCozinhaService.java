package com.algaworks.algaworkscurso.domain.service;

import com.algaworks.algaworkscurso.domain.exception.EntidadeEmUsoException;
import com.algaworks.algaworkscurso.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.algaworks.algaworkscurso.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long id){
        try {
            cozinhaRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("N\u00E3o existe um cadastro de cozinha com c\u00F3digo %d", id));
        }catch (DataIntegrityViolationException e){
            throw  new EntidadeEmUsoException(String.format("Cozinha de codigo %d n\u00E3o pode ser removida, pois est\u00E1 em uso", id));
        }
    }

    public List<File> download(String nome) throws IOException {
        Path path = Paths.get("/tmp/teste/");
        List<Path> paths = findByFileName(path, nome);
        List<File> files=  new ArrayList<>();
        for (Path path1: paths){
            files.add(path1.toFile());
        }
        return files;
    }

    public static List<Path> findByFileName(Path path, String fileName)
            throws IOException {

        List<Path> result;
        try (Stream<Path> pathStream = Files.find(path,
                Integer.MAX_VALUE,
                (p, basicFileAttributes) ->{
                    if(Files.isDirectory(p) || !Files.isReadable(p)){
                        return false;
                    }
                    return p.getFileName().toString().contains(fileName);
                })
        ) {
            result = pathStream.collect(Collectors.toList());
        }
        return result;
    }
}
