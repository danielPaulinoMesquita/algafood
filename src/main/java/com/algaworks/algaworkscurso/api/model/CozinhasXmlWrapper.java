package com.algaworks.algaworkscurso.api.model;

import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "cozinhas")
public class CozinhasXmlWrapper {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonProperty("cozinha")
    @NonNull
    private List<Cozinha> cozinhas;
}
