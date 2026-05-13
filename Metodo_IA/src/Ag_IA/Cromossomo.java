package Ag_IA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Cromossomo {

    String palavra;     
    int aptidao; //se é boa ou nao 

    public Cromossomo(String palavra, String estadoFinal) {
        this.palavra = palavra;
        this.aptidao = calcularAptidao(estadoFinal);
    }

    //primeira população
    public static void gerarPopulacao(ArrayList<Cromossomo> populacao,
                                      int tamanhoPopulacao,
                                      String estadoFinal) {

        for (int i = 0; i < tamanhoPopulacao; i++) {

            String palavraGerada =
                    Util.gerarPalavra(estadoFinal.length());

            Cromossomo individuo =
                    new Cromossomo(palavraGerada, estadoFinal);

            populacao.add(individuo);
        }
    }

    //calcula o quao boa é a palavra de 1 a 5, caso esteja correta fica 50, soma 5 se a palavra existir e multipliaca pelo numero de letras da palavra para descobrir a aptidao
    public int calcularAptidao(String estadoFinal) { 

        int nota = 0;

        for (int i = 0; i < estadoFinal.length(); i++) {

            if (palavra.contains(
                    String.valueOf(estadoFinal.charAt(i)))) {

                nota += 5;
            }

            if (i < palavra.length()
                    && palavra.charAt(i) == estadoFinal.charAt(i)) {

                nota += 50;
            }
        }

        return nota;
    }

    @Override
    public String toString() {
        return palavra + " - " + aptidao;
    }

    public static void exibirPopulacao(ArrayList<Cromossomo> populacao,
                                       int numeroGeracao) {

        System.out.println("\nGeração " + numeroGeracao + ":");

        for (Cromossomo individuo : populacao) {
            System.out.println(individuo);
        }
    }

    //Utilizaçao de torneio
    public static void selecionarPorTorneio(
            ArrayList<Cromossomo> populacao,
            ArrayList<Cromossomo> novaPopulacao,
            int taxaSelecao) {

        Random random = new Random();

        //calcula a taxa de selecao = individuos selecionados por gerazao
        int qtdSelecionados =
                taxaSelecao * populacao.size() / 100;

        Cromossomo cromossomo = populacao.get(0);

        novaPopulacao.add(cromossomo);

        int i = 1;

        while (i <= qtdSelecionados) {

            Cromossomo c1 =
                    populacao.get(random.nextInt(populacao.size()));

            Cromossomo c2;

            do {
                c2 = populacao.get(random.nextInt(populacao.size()));
            } while (c2 == c1);

            Cromossomo c3;

            do {
                c3 = populacao.get(random.nextInt(populacao.size()));
            } while (c3 == c1 || c3 == c2);

            ArrayList<Cromossomo> torneio =
                    new ArrayList<>();

            torneio.add(c1);
            torneio.add(c2);
            torneio.add(c3);

            Collections.sort(torneio,
                    Comparator.comparingInt(
                            c -> -c.aptidao));

            Cromossomo selecionado = torneio.get(0);

            if (!novaPopulacao.contains(selecionado)) {

                novaPopulacao.add(selecionado);
                i++;
            }
        }
    }

    public static void reproduzir(
            ArrayList<Cromossomo> populacao,
            ArrayList<Cromossomo> novaPopulacao,
            int taxaReproducao,
            String estadoFinal) {

        Random random = new Random();

        int qtdReproduzidos =
                taxaReproducao * populacao.size() / 100;

        int i = 0;

        while (i < qtdReproduzidos) {

            Cromossomo pai =
                    populacao.get(random.nextInt(populacao.size()));

            Cromossomo mae;

            do {
                mae = populacao.get(random.nextInt(populacao.size()));
            } while (mae == pai);

            String sPai = pai.palavra;
            String sMae = mae.palavra;

            //cruzamento de 1 ponto
            String sFilho1 =
                    sPai.substring(0, sPai.length() / 2)
                            + sMae.substring(sMae.length() / 2);

            String sFilho2 =
                    sMae.substring(0, sMae.length() / 2)
                            + sPai.substring(sPai.length() / 2);

            novaPopulacao.add(
                    new Cromossomo(sFilho1, estadoFinal));

            novaPopulacao.add(
                    new Cromossomo(sFilho2, estadoFinal));

            i += 2;
        }

        while (novaPopulacao.size() > populacao.size()) {
            novaPopulacao.remove(novaPopulacao.size() - 1);
        }
    }

    public static void mutar(ArrayList<Cromossomo> populacao,
                             String estadoFinal) {

        Random random = new Random();

        int qtdMutantes =
                random.nextInt(populacao.size() / 5 + 1);

        while (qtdMutantes > 0) {

            int posicaoMutante =
                    random.nextInt(populacao.size());

            Cromossomo mutante =
                    populacao.get(posicaoMutante);

            System.out.println("vai mutar " + mutante);

            String valorMutado = mutante.palavra;

            char caracterMutante =
                    mutante.palavra.charAt(
                            random.nextInt(mutante.palavra.length()));

            char caracterSorteado =
                    Util.letras.charAt(
                            random.nextInt(Util.tamanho));

            valorMutado = valorMutado.replaceFirst(
                    String.valueOf(caracterMutante),
                    String.valueOf(caracterSorteado));

            mutante =
                    new Cromossomo(valorMutado, estadoFinal);

            populacao.set(posicaoMutante, mutante);

            qtdMutantes--;
        }
    }
}