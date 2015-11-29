
import grafo.Aresta;
import grafo.Vertice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Maa - PC
 */
public class Arquivo {

    private static String label;
    private static int numeroInstancias;
    static ArrayList<Aresta> listaAtual;
    static HashMap<Vertice, ArrayList<Aresta>> matrizGrafo;
    static String[][] matriz;

    public Arquivo() {
    }

    private static void lerArquivo() throws FileNotFoundException, IOException {
        String linha = "";
        FileReader file = new FileReader(new File("HDGraph20_20.txt"));
        BufferedReader br = new BufferedReader(file);
        ArrayList<String> listaLinhaAtual = new ArrayList<>();

        linha = br.readLine();
        numeroInstancias = Integer.parseInt(String.valueOf(linha.split(" ")[0]));
        label = linha.split(" ")[1];

        linha = br.readLine();
        while (linha != null) {
            if (linha.trim().length() == 0) { // acabou a matriz atual
                insereMatrizAtual(listaLinhaAtual);
                System.out.println("\n****************ACABOU A MATRIZ*********************\n");
                listaLinhaAtual.clear(); //limpa a lista

            } else {
                listaLinhaAtual.add(linha);
            }
            linha = br.readLine();
        }
    }

    private static void insereMatrizAtual(ArrayList<String> listaLinhaMatrizAtual) {

        int controleLinhaAtual = 0;
        int posLinha = 0;

        ArrayList<String> linhaCertaAtual = new ArrayList<>();
        matriz = new String[numeroInstancias][numeroInstancias];

        for (String atual : listaLinhaMatrizAtual) {
            for (int k = atual.split(" ").length - 1; k >= 0; k--) {
                linhaCertaAtual.add(atual.split(" ")[k]);
            }
            controleLinhaAtual = linhaCertaAtual.size() - 1;
            preencherMatriz(linhaCertaAtual, controleLinhaAtual, posLinha);
            posLinha++; // guarda a linha atual da matriz

            linhaCertaAtual.clear();
        }

        completarMatriz();
        
        for (int l = 0; l < numeroInstancias; l++) {
            for (int c = 0; c < numeroInstancias; c++) {
                System.out.print(" " + matriz[l][c]);
            }
            System.out.println("\n");
        }
    }

    static void preencherMatriz(ArrayList<String> linhaCertaAtual, int controleLinhaAtual, int posLinha) {
        int j = numeroInstancias - 1;

        while (controleLinhaAtual >= 0) {
            if (posLinha == j) {
                matriz[posLinha][j] = label;
            } else {
                matriz[posLinha][j] = linhaCertaAtual.get(controleLinhaAtual);
                controleLinhaAtual--;
            }
            j--;
        }
    }

    static void completarMatriz() {
        for (int i = 0; i < numeroInstancias; i++) {
            for (int j = 0; j < numeroInstancias; j++) {
                if (i == j) {
                    matriz[i][j] = label;
                } else if (i > j) {
                    matriz[i][j] = matriz[j][i];
                }
            }
        }
    }

    static void salvarHash() {

        Vertice v = null;
        Vertice u = null;
        Aresta e = null;
        matrizGrafo = new HashMap<>();

        ArrayList<Aresta> adjacencias = new ArrayList<Aresta>();

        for (int i = 0; i < numeroInstancias; i++) {
            v = new Vertice();
            v.setNumero(i);

            for (int j = 0; j < numeroInstancias; j++) {
                u = new Vertice();
                u.setNumero(j);

                e = new Aresta();
                e.setV1(v);
                e.setV2(u);
                e.setLabel(matriz[i][j]);

                adjacencias.add(e);
            }
            for (Aresta s : adjacencias) {
                System.out.println("Lista Adjacencias : " + adjacencias.get(i).getLabel());

            }
            matrizGrafo.put(v, adjacencias);
            adjacencias.clear();

        }

    }

    public static void main(String[] args) throws IOException {
        lerArquivo();
    }
}
