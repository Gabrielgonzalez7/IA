package Ag_IA;

import java.util.Random;

public class Util {

    static String letras = "abcdefghijklmnopqrstuvxwyz";
    static int tamanho = letras.length();

    public static String gerarPalavra(int n) {

        String palavra = "";
        Random random = new Random();

            //repete as palavras várias vezes
        for (int i = 0; i < n; i++) {
            palavra += letras.charAt(random.nextInt(tamanho)); //Sorteio das letras
        }

        return palavra;
    }

    public static void main(String[] args) {
        System.out.println(gerarPalavra(5));
    }
}