package com.brncorrea.shopping.app;

import com.brncorrea.shopping.model.Data;

import java.util.Scanner;

/**
 * Wraps {@link Scanner} with validation loops so that typing an invalid
 * value never crashes the application.
 */
public class ConsoleInput {
    private final Scanner scanner;

    public ConsoleInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException erro) {
                System.out.println("Valor invalido. Digite um numero inteiro.");
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = scanner.nextLine().trim();
            try {
                return Double.parseDouble(linha.replace(',', '.'));
            } catch (NumberFormatException erro) {
                System.out.println("Valor invalido. Digite um numero decimal (ex: 1500.50).");
            }
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String resposta = readString(prompt + " (s/n): ");
            if (resposta.equalsIgnoreCase("s") || resposta.equalsIgnoreCase("sim")) {
                return true;
            }
            if (resposta.equalsIgnoreCase("n") || resposta.equalsIgnoreCase("nao")) {
                return false;
            }
            System.out.println("Valor invalido. Digite s ou n.");
        }
    }

    /**
     * Reads a date, repeating until the user types a valid calendar date.
     */
    public Data readData(String prompt) {
        while (true) {
            int dia = readInt(prompt + " - dia: ");
            int mes = readInt(prompt + " - mes: ");
            int ano = readInt(prompt + " - ano: ");
            try {
                return new Data(dia, mes, ano);
            } catch (IllegalArgumentException erro) {
                System.out.println("Data invalida: " + erro.getMessage() + ". Tente novamente.");
            }
        }
    }
}
