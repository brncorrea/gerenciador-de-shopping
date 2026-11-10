package com.brncorrea.shopping.mall;

import com.brncorrea.shopping.model.Endereco;
import com.brncorrea.shopping.store.Informatica;
import com.brncorrea.shopping.store.Loja;

/**
 * Represents a shopping mall that holds a fixed number of store slots.
 */
public class Shopping {
    private String nome;
    private Endereco endereco;
    private Loja[] lojas;

    public Shopping(String nome, Endereco endereco, int tamanhoLojas) {
        this.nome = nome;
        this.endereco = endereco;
        this.lojas = new Loja[tamanhoLojas];
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Loja[] getLojas() {
        return lojas;
    }

    /**
     * Inserts a store in the first free slot of the mall.
     *
     * @return true when the store was inserted, false when the mall is full.
     */
    public boolean insereLoja(Loja loja) {
        for (int i = 0; i < lojas.length; i++) {
            if (lojas[i] == null) {
                lojas[i] = loja;
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the first store with the given name.
     *
     * @return true when a store was removed.
     */
    public boolean removeLoja(String nomeLoja) {
        for (int i = 0; i < lojas.length; i++) {
            if (lojas[i] != null && lojas[i].getNome().equals(nomeLoja)) {
                lojas[i] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Counts how many stores match the given type name (case-insensitive).
     *
     * @return the amount of stores of that type, -1 when the type is not valid.
     */
    public int quantidadeLojasPorTipo(String tipoLoja) {
        int quantidade = 0;
        for (Loja loja : lojas) {
            if (loja != null && loja.getTipo().equalsIgnoreCase(tipoLoja)) {
                quantidade++;
            }
        }
        boolean tipoValido = tipoLoja.equalsIgnoreCase("Cosmetico") || tipoLoja.equalsIgnoreCase("Vestuario")
                || tipoLoja.equalsIgnoreCase("Bijuteria") || tipoLoja.equalsIgnoreCase("Alimentacao")
                || tipoLoja.equalsIgnoreCase("Informatica") || tipoLoja.equalsIgnoreCase("Loja");
        if (quantidade == 0 && !tipoValido) {
            return -1;
        }
        return quantidade;
    }

    /**
     * Counts how many stores currently occupy slots in the mall.
     */
    public int quantidadeTotalDeLojas() {
        int quantidade = 0;
        for (Loja loja : lojas) {
            if (loja != null) {
                quantidade++;
            }
        }
        return quantidade;
    }

    /**
     * Returns the electronics store with the most expensive electronics
     * insurance, or null when the mall has no electronics stores.
     */
    public Informatica lojaSeguroMaisCaro() {
        Informatica maisCara = null;
        for (Loja loja : lojas) {
            if (loja instanceof Informatica) {
                Informatica inf = (Informatica) loja;
                if (maisCara == null || inf.getSeguroEletronicos() > maisCara.getSeguroEletronicos()) {
                    maisCara = inf;
                }
            }
        }
        return maisCara;
    }

    @Override
    public String toString() {
        return "Shopping: " + nome + " | Endereco: " + endereco;
    }
}
