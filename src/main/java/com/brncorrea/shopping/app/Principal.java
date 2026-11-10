package com.brncorrea.shopping.app;

import com.brncorrea.shopping.model.Data;
import com.brncorrea.shopping.model.Endereco;
import com.brncorrea.shopping.model.Produto;
import com.brncorrea.shopping.store.Loja;

import java.util.Scanner;

/**
 * Menu-driven console application prototype.
 */
public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;
        Loja lojaAtual = null;

        do {
            System.out.println("(1) criar uma loja");
            System.out.println("(2) criar um produto");
            System.out.println("(3) sair");
            System.out.print("Digite uma opcao: ");
            opcao = sc.nextInt();
            sc.nextLine();

            if (opcao == 1) {
                System.out.print("Nome da loja: ");
                String nome = sc.nextLine();

                System.out.print("Quantidade de funcionarios: ");
                int qtdFuncionarios = sc.nextInt();

                System.out.print("Salario base dos funcionarios: ");
                double salario = sc.nextDouble();
                sc.nextLine();

                System.out.print("Nome da rua: ");
                String rua = sc.nextLine();

                System.out.print("Numero: ");
                String numero = sc.nextLine();

                System.out.print("Complemento: ");
                String complemento = sc.nextLine();

                System.out.print("Cidade: ");
                String cidade = sc.nextLine();

                System.out.print("Estado: ");
                String estado = sc.nextLine();

                System.out.print("Pais: ");
                String pais = sc.nextLine();

                System.out.print("CEP: ");
                String cep = sc.nextLine();

                Endereco endereco = new Endereco(rua, cidade, estado, pais, cep, numero, complemento);

                System.out.print("Dia de fundacao: ");
                int dia = sc.nextInt();
                System.out.print("Mes de fundacao: ");
                int mes = sc.nextInt();
                System.out.print("Ano de fundacao: ");
                int ano = sc.nextInt();
                sc.nextLine();

                Data dataFundacao = new Data(dia, mes, ano);

                System.out.print("Tamanho do estoque: ");
                int tamanhoEstoque = sc.nextInt();
                sc.nextLine();

                lojaAtual = new Loja(nome, qtdFuncionarios, salario, endereco, dataFundacao, tamanhoEstoque);

            } else if (opcao == 2) {
                System.out.print("Nome do produto: ");
                String nomeProduto = sc.nextLine();

                System.out.print("Preco do produto: ");
                double preco = sc.nextDouble();
                sc.nextLine();

                System.out.print("Dia de validade: ");
                int diaV = sc.nextInt();
                System.out.print("Mes de validade: ");
                int mesV = sc.nextInt();
                System.out.print("Ano de validade: ");
                int anoV = sc.nextInt();
                sc.nextLine();

                Data dataValidade = new Data(diaV, mesV, anoV);
                Produto produto = new Produto(nomeProduto, preco, dataValidade);

                Data dataVerificacao = new Data(20, 10, 2023);
                System.out.println(produto.estaVencido(dataVerificacao) ? "PRODUTO VENCIDO" : "PRODUTO NAO VENCIDO");

                if (lojaAtual != null) {
                    System.out.println(lojaAtual);
                }

            } else if (opcao != 3) {
                System.out.println("Opcao invalida");
            }

        } while (opcao != 3);

        sc.close();
    }
}
