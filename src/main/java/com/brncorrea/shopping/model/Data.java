package com.brncorrea.shopping.model;

import java.time.LocalDate;

/**
 * Represents a calendar date (day/month/year) with leap-year support.
 */
public class Data {
    private int dia;
    private int mes;
    private int ano;

    public Data(int dia, int mes, int ano) {
        int[] diasPorMes = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        boolean bissexto = isAnoBissexto(ano);
        if (bissexto) {
            diasPorMes[2] = 29;
        }

        boolean dataValida = mes >= 1 && mes <= 12 && dia >= 1 && dia <= diasPorMes[mes];

        if (dataValida) {
            this.dia = dia;
            this.mes = mes;
            this.ano = ano;
        } else {
            System.out.println("Data invalida! Definindo data padrao: 1/1/2000.");
            this.dia = 1;
            this.mes = 1;
            this.ano = 2000;
        }
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
     * Returns whether the given year is a leap year (Gregorian rule).
     */
    public static boolean isAnoBissexto(int ano) {
        return (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);
    }

    @Override
    public String toString() {
        return dia + "/" + mes + "/" + ano;
    }
}
