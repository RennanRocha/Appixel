package br.com.trainning.pdv.domain.model;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

@Table("Carrinho")
public class Carrinho extends Model {


    @Key
    @AutoIncrement
    @Column("id")
    private Long id;
    @Column("id_compra")
    private String idCompra;
    @Column("encerrada")
    private int encerrada;
    @Column("enviada")
    private int enviada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public int getEncerrada() {
        return encerrada;
    }

    public void setEncerrada(int encerrada) {
        this.encerrada = encerrada;
    }

    public int getEnviada() {
        return enviada;
    }

    public void setEnviada(int enviada) {
        this.enviada = enviada;
    }

    @Override
    public String toString() {
        return "Carrinho{" +
                "id=" + id +
                ", idCompra=" + idCompra +
                ", encerrada=" + encerrada +
                ", enviada=" + enviada +
                '}';
    }
}
