package br.com.trainning.pdv.domain.model;

import java.io.Serializable;
import java.util.List;

public class ListaProdutos implements Serializable {

    private List<Produto> lista;

    public List<Produto> getLista() {
        return lista;
    }

    public void setLista(List<Produto> lista) {
        this.lista = lista;
    }
}
