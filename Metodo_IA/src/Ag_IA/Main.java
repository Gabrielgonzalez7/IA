package Ag_IA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Entre com a palavra do estado final: ");
        String estadoFinal = scanner.nextLine();

        System.out.print("Tamanho da populacao: ");
        int tamanhoPopulacao = scanner.nextInt();

        System.out.print("Geracoes: ");
        int quantidadeGeracoes = scanner.nextInt();

        System.out.print("Selecao por gerecao (25 a 40): ");
        int taxaSelecao = scanner.nextInt();

        int taxaReproducao = 100 - taxaSelecao;

        System.out.print("Tempo para mutacao por geracao: ");
        int taxaMutacao = scanner.nextInt();

        ArrayList<Cromossomo> populacao =
                new ArrayList<>();

        ArrayList<Cromossomo> novaPopulacao =
                new ArrayList<>();

        // Primeira geração
        Cromossomo.gerarPopulacao(
                populacao,
                tamanhoPopulacao,
                estadoFinal
        );

        Collections.sort(populacao,
                Comparator.comparingInt(
                        c -> -c.aptidao));

        Cromossomo.exibirPopulacao(populacao, 0);

        //quantidade de gerações
        for (int i = 1; i <= quantidadeGeracoes; i++) {

            novaPopulacao.clear();
//selecao por torneio
            Cromossomo.selecionarPorTorneio(
                    populacao,
                    novaPopulacao,
                    taxaSelecao
            );

            Cromossomo.reproduzir(
                    populacao,
                    novaPopulacao,
                    taxaReproducao,
                    estadoFinal
            );

            if (i % taxaMutacao == 0) {
                Cromossomo.mutar(
                        novaPopulacao,
                        estadoFinal
                );
            }

            populacao.clear();
            populacao.addAll(novaPopulacao);

            Collections.sort(populacao,
                    Comparator.comparingInt(
                            c -> -c.aptidao));

            Cromossomo.exibirPopulacao(populacao, i);

            // verifica se encontrou a palavra
            if (populacao.get(0).palavra.equals(estadoFinal)) {

                System.out.println("\nPalavra encontrada!");
                System.out.println("Geracao: " + i);
                System.out.println("Resultado: " + populacao.get(0));

                break;
            }
        }

        scanner.close();
    }
}