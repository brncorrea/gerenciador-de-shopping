package com.brncorrea.shopping.mall;

import com.brncorrea.shopping.model.Data;
import com.brncorrea.shopping.model.Endereco;
import com.brncorrea.shopping.store.Alimentacao;
import com.brncorrea.shopping.store.Informatica;
import com.brncorrea.shopping.store.Loja;
import com.brncorrea.shopping.store.Vestuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingTest {

    private final Endereco endereco = new Endereco("Av. Central", "100", "-", "Fortaleza", "CE", "Brasil", "60000-000");

    private Alimentacao criarAlimentacao(String nome) {
        return new Alimentacao(nome, 5, 1000, endereco, new Data(1, 1, 2020), new Data(1, 1, 2024), 1);
    }

    private Vestuario criarVestuario(String nome, boolean importados) {
        return new Vestuario(nome, 5, 1000, endereco, new Data(1, 1, 2020), importados, 1);
    }

    private Informatica criarInformatica(String nome, double seguro) {
        return new Informatica(nome, 5, 1000, endereco, new Data(1, 1, 2020), seguro, 1);
    }

    @Test
    void insereRemoveLoja() {
        Shopping shopping = new Shopping("Mall", endereco, 2);
        assertTrue(shopping.insereLoja(criarAlimentacao("Cafeteria")));
        assertTrue(shopping.insereLoja(criarVestuario("Vestir", false)));
        assertFalse(shopping.insereLoja(criarAlimentacao("Extra"))); // full
        assertEquals(2, shopping.quantidadeTotalDeLojas());

        assertTrue(shopping.removeLoja("Cafeteria"));
        assertFalse(shopping.removeLoja("Cafeteria")); // already removed
        assertFalse(shopping.removeLoja("Inexistente"));
    }

    @Test
    void contaLojasPorTipo() {
        Shopping shopping = new Shopping("Mall", endereco, 4);
        shopping.insereLoja(criarAlimentacao("Padaria"));
        shopping.insereLoja(criarAlimentacao("Cafeteria"));
        shopping.insereLoja(criarVestuario("Vestir", true));
        shopping.insereLoja(criarInformatica("Electro", 1000));

        assertEquals(2, shopping.quantidadeLojasPorTipo("Alimentacao"));
        assertEquals(1, shopping.quantidadeLojasPorTipo("vestuario")); // case-insensitive
        assertEquals(1, shopping.quantidadeLojasPorTipo("INFORMATICA"));
        assertEquals(0, shopping.quantidadeLojasPorTipo("Bijuteria"));  // valid type, none found
        assertEquals(-1, shopping.quantidadeLojasPorTipo("Farmacia"));  // invalid type
    }

    @Test
    void retornaLojaComMaiorSeguro() {
        Shopping shopping = new Shopping("Mall", endereco, 3);
        shopping.insereLoja(criarInformatica("Barata", 500));
        shopping.insereLoja(criarAlimentacao("Padaria"));

        Informatica resultado = shopping.lojaSeguroMaisCaro();
        assertEquals("Barata", resultado.getNome());

        shopping.insereLoja(criarInformatica("Cara", 9999));
        assertEquals("Cara", shopping.lojaSeguroMaisCaro().getNome());
    }

    @Test
    void semLojasDeInformaticaRetornaNulo() {
        Shopping shopping = new Shopping("Mall", endereco, 2);
        shopping.insereLoja(criarAlimentacao("Padaria"));
        assertNull(shopping.lojaSeguroMaisCaro());
    }

    @Test
    void shoppingVazioNaoAceitaLojaNula() {
        Shopping shopping = new Shopping("Mall", endereco, 1);
        assertDoesNotThrow(() -> shopping.removeLoja("Qualquer"));
        assertEquals(0, shopping.quantidadeTotalDeLojas());
    }

    @Test
    void tamanhoNegativoDoEstoqueNaoQuebra() {
        Shopping shopping = new Shopping("Mall", endereco, -1);
        assertFalse(shopping.insereLoja(new Loja("Qualquer", 1)));
    }
}
