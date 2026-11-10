package com.brncorrea.shopping.store;

import com.brncorrea.shopping.model.Data;
import com.brncorrea.shopping.model.Endereco;
import com.brncorrea.shopping.model.Produto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LojaTest {

    private Loja criarLoja(int quantidadeFuncionarios) {
        return new Loja("Loja Teste", quantidadeFuncionarios, 1000.0,
                new Endereco("Rua A", "1", "SN", "Fortaleza", "CE", "Brasil", "60000-000"),
                new Data(1, 1, 2020), 2);
    }

    @Test
    void gastosComSalarioMultiplicaFuncionarios() {
        assertEquals(20000.0, criarLoja(20).gastosComSalario());
    }

    @Test
    void gastosComSalarioRetornaUmQuandoSalarioNaoInformado() {
        Loja loja = new Loja("Loja", 10);
        assertEquals(-1, loja.gastosComSalario());
    }

    @Test
    void tamanhoDaLojaClassificaPorFuncionarios() {
        assertEquals('P', criarLoja(9).tamanhoDaLoja());
        assertEquals('M', criarLoja(10).tamanhoDaLoja());
        assertEquals('M', criarLoja(30).tamanhoDaLoja());
        assertEquals('G', criarLoja(31).tamanhoDaLoja());
    }

    @Test
    void insereERemoveProdutos() {
        Loja loja = criarLoja(5);
        Produto produtoA = new Produto("Arroz", 9.50);
        Produto produtoB = new Produto("Frisalda", 3.0);
        Produto extra = new Produto("Feijão", 6.0);

        assertTrue(loja.insereProduto(produtoA));
        assertTrue(loja.insereProduto(produtoB));
        assertFalse(loja.insereProduto(extra)); // only 2 slots

        assertTrue(loja.removeProduto("Arroz"));
        assertFalse(loja.removeProduto("Arroz")); // already removed
        assertTrue(loja.insereProduto(extra));    // slot reused
    }

    @Test
    void todosOsTiposDeLojaSaoSubclasses() {
        Data fundacao = new Data(1, 1, 2020);
        Endereco endereco = new Endereco("Rua A", "1", "SN", "Fortaleza", "CE", "Brasil", "60000-000");

        assertEquals("Alimentacao", new Alimentacao("A", 5, 1000, endereco, fundacao, new Data(1, 1, 2024), 1).getTipo());
        assertEquals("Bijuteria", new Bijuteria("B", 5, 1000, endereco, fundacao, 5000, 1).getTipo());
        assertEquals("Cosmetico", new Cosmetico("C", 5, 1000, endereco, fundacao, 100, 1).getTipo());
        assertEquals("Informatica", new Informatica("I", 5, 1000, endereco, fundacao, 2000, 1).getTipo());
        assertEquals("Vestuario", new Vestuario("V", 5, 1000, endereco, fundacao, true, 1).getTipo());
        assertEquals("Loja", criarLoja(5).getTipo());
    }
}
