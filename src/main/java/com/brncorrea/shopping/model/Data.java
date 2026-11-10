package com.brncorrea.shopping.model;

import java.time.LocalDate;

/**
 * Represents a calendar date (day/month/year) with leap-year support.
 */
public class Data {
    private int dia;
    private int mes;
    private int ano;

    /**
     * Creates and validates a date.
     *
     * @throws IllegalArgumentException when the date is not a valid calendar date.
     */
    public Data(int dia, int mes, int ano) {
        if (!isValida(dia, mes, ano)) {
            throw new IllegalArgumentException("Data invalida: " + dia + "/" + mes + "/" + ano);
        }
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    /**
     * Returns the current system date.
     */
    public static Data hoje() {
        LocalDate hoje = LocalDate.now();
        return new Data(hoje.getDayOfMonth(), hoje.getMonthValue(), hoje.getYear());
    }

    /**
     * Validates a calendar date, considering leap years.
     */
    public static boolean isValida(int dia, int mes, int ano) {
        if (ano < 1 || mes < 1 || mes > 12 || dia < 1) {
            return false;
        }
        return dia <= diasNoMes(mes, ano);
    }

    /**
     * Returns the number of days in the given month/year.
     */
    public static int diasNoMes(int mes, int ano) {
        int[] diasPorMes = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (mes == 2 && isAnoBissexto(ano)) {
            return 29;
        }
        return diasPorMes[mes];
    }

    /**
     * Returns whether the given year is a leap year (Gregorian rule).
     */
    public static boolean isAnoBissexto(int ano) {
        return (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    /**
     * Checks whether the year of this date is a leap year.
     */
    public boolean verificaAnoBissexto() {
        return isAnoBissexto(ano);
    }

    /**
     * Returns whether this date comes strictly before the other date.
     */
    public boolean isAnterior(Data outra) {
        if (ano != outra.ano) {
            return ano < outra.ano;
        }
        if (mes != outra.mes) {
            return mes < outra.mes;
        }
        return dia < outra.dia;
    }

    /**
     * Returns whether this date comes strictly after the other date.
     */
    public boolean isPosterior(Data outra) {
        return outra.isAnterior(this);
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof Data)) {
            return false;
        }
        Data outra = (Data) objeto;
        return dia == outra.dia && mes == outra.mes && ano == outra.ano;
    }

    @Override
    public int hashCode() {
        return 31 * dia + 31 * mes + ano;
    }

    @Override
    public String toString() {
        return dia + "/" + mes + "/" + ano;
    }
}
