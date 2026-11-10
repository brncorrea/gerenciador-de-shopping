package com.brncorrea.shopping.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    private static final Data HOJE = Data.hoje();

    @Test
    void produtoSemValidadeNuncaVence() {
        Produto produto = new Produto("Teclado", 99.90);
        assertFalse(produto.estaVencidoHoje());
        assertFalse(produto.estaVencido(HOJE));
    }

    @Test
    void produtoSemValidadeTemDescricaoTratada() {
        Produto produto = new Produto("Teclado", 99.90);
        String descricao = produto.toString();
        assertTrue(descricao.contains("sem validade"), "Should mention missing validity: " + descricao);
    }

    @Test
    void produtoVenceuOntem() {
        Data validade = new Data(1, 1, 2020);
        Produto produto = new Produto("Leite", 5.0, validade);
        assertTrue(produto.estaVencido(new Data(2, 1, 2020)));
        assertTrue(produto.estaVencido(new Data(1, 1, 2021)));
        assertFalse(produto.estaVencido(new Data(1, 1, 2020))); // same day = still valid
    }

    @Test
    void produtoComValidadeFuturaNaoVence() {
        Produto produto = new Produto("Leite", 5.0, new Data(31, 12, 2999));
        assertFalse(produto.estaVencidoHoje());
    }

    @Test
    void construtorEncadeadoPreencheCampos() {
        Produto pai = new Produto("Mouse", 50.0);
        assertEquals("Mouse", pai.getNome());
        assertEquals(50.0, pai.getPreco());
        assertNull(pai.getDataValidade());
    }
}
