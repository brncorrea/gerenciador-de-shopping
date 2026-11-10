package com.brncorrea.shopping.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {

    @Test
    void criaDataValida() {
        Data data = new Data(15, 8, 2024);
        assertEquals(15, data.getDia());
        assertEquals(8, data.getMes());
        assertEquals(2024, data.getAno());
    }

    @Test
    void dataInvalidaLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> new Data(31, 2, 2024));
        assertThrows(IllegalArgumentException.class, () -> new Data(0, 1, 2024));
        assertThrows(IllegalArgumentException.class, () -> new Data(15, 13, 2024));
        assertThrows(IllegalArgumentException.class, () -> new Data(1, 1, 0));
    }

    @ParameterizedTest
    @CsvSource({
            "29, 2, 2024, true",   // leap year
            "28, 2, 2024, true",
            "29, 2, 2023, false",  // not a leap year
            "31, 4, 2024, false",  // April has 30 days
            "30, 4, 2024, true"
    })
    void validateCalendarDays(int dia, int mes, int ano, boolean valida) {
        assertEquals(valida, Data.isValida(dia, mes, ano));
    }

    @Test
    void detectaAnosBissextos() {
        assertTrue(Data.isAnoBissexto(2024));
        assertFalse(Data.isAnoBissexto(2100)); // divisible by 100 but not 400
        assertTrue(Data.isAnoBissexto(2000));  // divisible by 400
        assertFalse(Data.isAnoBissexto(2023));

        assertTrue(new Data(29, 2, 2024).verificaAnoBissexto());
        assertFalse(new Data(28, 2, 2023).verificaAnoBissexto());
    }

    @Test
    void comparaDatasCorretamente() {
        Data dataBase = new Data(15, 8, 2024);

        assertTrue(new Data(14, 8, 2024).isAnterior(dataBase));
        assertTrue(new Data(16, 8, 2024).isPosterior(dataBase));
        assertFalse(dataBase.isAnterior(dataBase));
        assertFalse(dataBase.isPosterior(dataBase));
        assertTrue(new Data(1, 1, 2025).isPosterior(dataBase));
        assertTrue(new Data(31, 12, 2023).isAnterior(dataBase));
    }

    @Test
    void igualdadeConsideraTodosOsCampos() {
        assertEquals(new Data(15, 8, 2024), new Data(15, 8, 2024));
        assertEquals(new Data(15, 8, 2024).hashCode(), new Data(15, 8, 2024).hashCode());
        assertNotEquals(new Data(15, 8, 2024), new Data(15, 9, 2024));
    }
}
