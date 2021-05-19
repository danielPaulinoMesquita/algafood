package com.algaworks.algaworkscurso.api.controller;

import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.algaworks.algaworkscurso.domain.repository.CozinhaRepository;
import com.algaworks.algaworkscurso.domain.service.CadastroCozinhaService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/teste")
public class TestController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService service;

    /*Você pode pegar uma paramentro por QueryString ou QueryParam utilizando o @RequestParam
    * exemplo de uri com queryParam:  */
    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(@RequestParam String nome){
        return cozinhaRepository.findCozinhaByNome(nome);
    }


//    @RequestMapping(value="/zip/{nomeArquivo}", produces="application/zip", method = RequestMethod.GET)
    @GetMapping(value="/zip/{nomeArquivo}", produces="application/zip")
    public void zipFiles(HttpServletResponse response, @PathVariable String nomeArquivo) throws IOException {

        //setting headers
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"teste.zip\"");

        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        // create a list to add files to be zipped
        List<File> files = new ArrayList<>(2);
        files = service.download(nomeArquivo);
        //files.add(new File("README.md"));

        // package files
        for (File file : files) {
            //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        zipOutputStream.close();
    }
}
