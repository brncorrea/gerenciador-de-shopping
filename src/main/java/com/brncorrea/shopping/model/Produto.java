package com.brncorrea.shopping.model;

/**
 * Represents a product with an optional expiration date.
 */
public class Produto {
    private String nome;
    private double preco;
    private Data dataValidade;

    public Produto(String nome, double preco) {
        this(nome, preco, null);
    }

    public Produto(String nome, double preco, Data dataValidade) {
        this.nome = nome;
        this.preco = preco;
        this.dataValidade = dataValidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Data getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Data dataValidade) {
        this.dataValidade = dataValidade;
    }

    /**
     * Checks whether the product is expired with respect to the given date.
     * A product without an expiration date is never considered expired.
     */
    public boolean estaVencido(Data data) {
        if (dataValidade == null) {
            return false;
        }
        return dataValidade.isAnterior(data);
    }

    /**
     * Checks whether the product is expired with respect to today's date.
     */
    public boolean estaVencidoHoje() {
        return estaVencido(Data.hoje());
    }

    @Override
    public String toString() {
        String validade = dataValidade != null ? dataValidade.toString() : "sem validade";
        return "Produto: " + nome + " | Preco: " + preco + " | Validade: " + validade;
    }
}
