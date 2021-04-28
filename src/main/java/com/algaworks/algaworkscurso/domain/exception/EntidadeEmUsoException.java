package com.algaworks.algaworkscurso.domain.exception;

public class EntidadeEmUsoException extends RuntimeException{

    public EntidadeEmUsoException(String message) {
        super(message);
    }

    static enum Messagens{
        ENTIDADE_EM_USO("Entidade n\u00E3o Encontrada ou em Uso");

        public String Descricao;

        Messagens(String descricao) {
            Descricao = descricao;
        }
    }


}
