package com.brncorrea.shopping.app;

import com.brncorrea.shopping.mall.Shopping;
import com.brncorrea.shopping.model.Data;
import com.brncorrea.shopping.model.Endereco;
import com.brncorrea.shopping.model.Produto;
import com.brncorrea.shopping.store.Alimentacao;
import com.brncorrea.shopping.store.Bijuteria;
import com.brncorrea.shopping.store.Cosmetico;
import com.brncorrea.shopping.store.Informatica;
import com.brncorrea.shopping.store.Loja;
import com.brncorrea.shopping.store.Vestuario;

import java.util.Scanner;

/**
 * Menu-driven console application for managing a shopping mall:
 * stores (with their specializations), inventories and products.
 */
public class Principal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleInput in = new ConsoleInput(scanner);
        Shopping shopping = null;
        int opcao;

        do {
            System.out.println("\n===== GERENCIADOR DE SHOPPING =====");
            System.out.println("(1) Criar shopping");
            System.out.println("(2) Criar loja e adicionar ao shopping");
            System.out.println("(3) Adicionar produto ao estoque de uma loja");
            System.out.println("(4) Listar lojas do shopping");
            System.out.println("(5) Exibir resumo do shopping");
            System.out.println("(6) Remover loja do shopping");
            System.out.println("(0) Sair");
            System.out.println("-----------------------------------");
            opcao = in.readInt("Digite uma opcao: ");

            switch (opcao) {
                case 1:
                    shopping = criarShopping(in);
                    break;
                case 2:
                    adicionarLoja(in, shopping);
                    break;
                case 3:
                    adicionarProduto(in, shopping);
                    break;
                case 4:
                    listarLojas(shopping);
                    break;
                case 5:
                    exibirResumo(shopping);
                    break;
                case 6:
                    removerLoja(in, shopping);
                    break;
                case 0:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opcao invalida");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static Shopping criarShopping(ConsoleInput in) {
        System.out.println("\n--- Criar shopping ---");
        String nome = in.readString("Nome do shopping: ");
        int tamanho = in.readInt("Quantidade maxima de lojas: ");

        System.out.println("--- Endereco ---");
        Endereco endereco = lerEndereco(in);

        System.out.println("Shopping \"" + nome + "\" criado com sucesso!");
        return new Shopping(nome, endereco, Math.max(tamanho, 0));
    }

    private static void adicionarLoja(ConsoleInput in, Shopping shopping) {
        System.out.println("\n--- Criar loja ---");
        if (!shoppingExiste(shopping)) {
            return;
        }

        System.out.println("Tipos de loja:");
        System.out.println("(1) Alimentacao");
        System.out.println("(2) Bijuteria");
        System.out.println("(3) Cosmetico");
        System.out.println("(4) Informatica");
        System.out.println("(5) Vestuario");
        System.out.println("(6) Loja generica");
        int tipo = in.readInt("Escolha o tipo: ");
        if (tipo < 1 || tipo > 6) {
            System.out.println("Tipo invalido.");
            return;
        }

        String nome = in.readString("Nome da loja: ");
        int qtdFuncionarios = in.readInt("Quantidade de funcionarios: ");
        double salario = in.readDouble("Salario base dos funcionarios: ");

        System.out.println("--- Endereco da loja ---");
        Endereco endereco = lerEndereco(in);

        Data dataFundacao = in.readData("Data de fundacao");
        int tamanhoEstoque = in.readInt("Tamanho do estoque: ");

        Loja loja = criarLojaDoTipo(in, tipo, nome, qtdFuncionarios, salario, endereco, dataFundacao, tamanhoEstoque);
        boolean inserida = shopping.insereLoja(loja);
        System.out.println(inserida ? "Loja adicionada ao shopping!" : "Nao foi possivel adicionar: shopping cheio.");
    }

    private static Loja criarLojaDoTipo(ConsoleInput in, int tipo, String nome, int qtdFuncionarios, double salario, Endereco endereco, Data dataFundacao, int tamanhoEstoque) {
        switch (tipo) {
            case 1: {
                Data dataAlvara = in.readData("Data do alvara");
                return new Alimentacao(nome, qtdFuncionarios, salario, endereco, dataFundacao, dataAlvara, tamanhoEstoque);
            }
            case 2: {
                double metaVendas = in.readDouble("Meta de vendas: ");
                return new Bijuteria(nome, qtdFuncionarios, salario, endereco, dataFundacao, metaVendas, tamanhoEstoque);
            }
            case 3: {
                double taxa = in.readDouble("Taxa de comercializacao: ");
                return new Cosmetico(nome, qtdFuncionarios, salario, endereco, dataFundacao, taxa, tamanhoEstoque);
            }
            case 4: {
                double seguro = in.readDouble("Seguro de eletronicos: ");
                return new Informatica(nome, qtdFuncionarios, salario, endereco, dataFundacao, seguro, tamanhoEstoque);
            }
            case 5: {
                boolean importados = in.readBoolean("Produtos importados");
                return new Vestuario(nome, qtdFuncionarios, salario, endereco, dataFundacao, importados, tamanhoEstoque);
            }
            default:
                return new Loja(nome, qtdFuncionarios, salario, endereco, dataFundacao, tamanhoEstoque);
        }
    }

    private static void adicionarProduto(ConsoleInput in, Shopping shopping) {
        System.out.println("\n--- Adicionar produto ---");
        if (!shoppingExiste(shopping)) {
            return;
        }

        Loja loja = escolherLoja(in, shopping);
        if (loja == null) {
            return;
        }

        String nome = in.readString("Nome do produto: ");
        double preco = in.readDouble("Preco do produto: ");

        Produto produto;
        if (in.readBoolean("Informar data de validade")) {
            Data validade = in.readData("Data de validade");
            produto = new Produto(nome, preco, validade);
        } else {
            produto = new Produto(nome, preco);
        }

        boolean inserido = loja.insereProduto(produto);
        if (!inserido) {
            System.out.println("Estoque cheio: produto nao adicionado.");
            return;
        }

        if (produto.estaVencidoHoje()) {
            System.out.println("Atencao: produto com validade vencida!");
        }
        System.out.println("Produto adicionado ao estoque de " + loja.getNome() + ".");
    }

    private static void listarLojas(Shopping shopping) {
        System.out.println("\n--- Lojas do shopping ---");
        if (!shoppingExiste(shopping)) {
            return;
        }
        boolean vazia = true;
        for (Loja loja : shopping.getLojas()) {
            if (loja != null) {
                System.out.println(loja);
                vazia = false;
            }
        }
        if (vazia) {
            System.out.println("Nenhuma loja cadastrada.");
        }
    }

    private static void exibirResumo(Shopping shopping) {
        System.out.println("\n--- Resumo do shopping ---");
        if (!shoppingExiste(shopping)) {
            return;
        }
        System.out.println(shopping);
        System.out.println("Total de lojas: " + shopping.quantidadeTotalDeLojas());

        String[] tipos = {"Alimentacao", "Bijuteria", "Cosmetico", "Informatica", "Vestuario"};
        for (String tipo : tipos) {
            System.out.println("Lojas de " + tipo + ": " + shopping.quantidadeLojasPorTipo(tipo));
        }

        Informatica maisCara = shopping.lojaSeguroMaisCaro();
        if (maisCara != null) {
            System.out.println("Maior seguro de eletronicos: "
                    + maisCara.getNome() + " (" + maisCara.getSeguroEletronicos() + ")");
        }
    }

    private static void removerLoja(ConsoleInput in, Shopping shopping) {
        System.out.println("\n--- Remover loja ---");
        if (!shoppingExiste(shopping)) {
            return;
        }
        String nome = in.readString("Nome da loja a remover: ");
        boolean removida = shopping.removeLoja(nome);
        System.out.println(removida ? "Loja removida com sucesso." : "Loja nao encontrada.");
    }

    /**
     * Lists the stores and lets the user pick one by number.
     *
     * @return the selected store, or null when there is nothing to select.
     */
    private static Loja escolherLoja(ConsoleInput in, Shopping shopping) {
        int total = shopping.quantidadeTotalDeLojas();
        if (total == 0) {
            System.out.println("Nenhuma loja cadastrada no shopping.");
            return null;
        }

        System.out.println("Lojas disponiveis:");
        int indice = 1;
        for (Loja loja : shopping.getLojas()) {
            if (loja != null) {
                System.out.println("(" + indice + ") " + loja.getNome());
                indice++;
            }
        }
        int escolha = in.readInt("Escolha a loja: ");
        if (escolha < 1 || escolha > total) {
            System.out.println("Escolha invalida.");
            return null;
        }

        int atual = 0;
        for (Loja loja : shopping.getLojas()) {
            if (loja != null) {
                atual++;
                if (atual == escolha) {
                    return loja;
                }
            }
        }
        return null;
    }

    private static Endereco lerEndereco(ConsoleInput in) {
        String rua = in.readString("Nome da rua: ");
        String numero = in.readString("Numero: ");
        String complemento = in.readString("Complemento: ");
        String cidade = in.readString("Cidade: ");
        String estado = in.readString("Estado: ");
        String pais = in.readString("Pais: ");
        String cep = in.readString("CEP: ");
        return new Endereco(rua, cidade, estado, pais, cep, numero, complemento);
    }

    /**
     * Ensures the shopping exists before running an operation.
     */
    private static boolean shoppingExiste(Shopping shopping) {
        if (shopping == null) {
            System.out.println("Nenhum shopping criado. Use a opcao (1) primeiro.");
            return false;
        }
        return true;
    }
}
