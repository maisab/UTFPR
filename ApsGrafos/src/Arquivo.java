
import com.sun.javafx.scene.control.skin.VirtualFlow;
import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    static HashMap<Vertice, List<Aresta>> matrizGrafo;
    static String[][] matriz;
    static ArrayList<String> listaLabels = new ArrayList<>();
    static List<Vertice> verticesGrafo = new ArrayList<>();
    static List<List<Aresta>> adjacenciasVerticeAtual = new ArrayList<>();
//    static ArrayList<Aresta> listaAtual;
//    static ArrayList<String> lista = new ArrayList<>(); //contem a quantidade de label de cada label,eh composto pelo nome do laber +  a quantidade delabel

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
                return;
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
        montaGrafo();
//        printGrafo();
//        componentesPorLabel();
        componentesDoGrafo();
        System.out.println("LISTA LABELS ATUAL : " + listaLabels.toString());

//        printMatriz();
        listaLabels.clear(); //limpa para pegar a lista de labels do proximo grafo
        adjacenciasVerticeAtual.clear();
        verticesGrafo.clear();
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
        boolean existeNaLista = false;

        for (int i = 0; i < numeroInstancias; i++) {
            for (int j = 0; j < numeroInstancias; j++) {
                if (i == j) {
                    matriz[i][j] = label;
                }
//                 else if (i > j) {
//                    matriz[i][j] = matriz[j][i];
//                }

                existeNaLista = false;

                for (String auxLabels : listaLabels) { // verifica se o label já esta na lista
                    if (matriz[i][j] == null) {
                        existeNaLista = true;
                    } else if (auxLabels.equals(matriz[i][j])) {
                        existeNaLista = true;
                    }
                }

                if (!existeNaLista) { // se não conter na lista de labels atual, adiciona
                    listaLabels.add(matriz[i][j]);
                }
            }
        }
    }

    static void montaGrafo() {
        matrizGrafo = new HashMap<Vertice, List<Aresta>>();

        List<Aresta> listaArestasVerticeAtual = new ArrayList<>();
        Vertice v = null;
        Vertice u = null;
        Aresta a = null;

        for (int i = 0; i < matriz.length; i++) {

            v = new Vertice();
            v.setNumero(i);
            matrizGrafo.put(v, new ArrayList<Aresta>());
            verticesGrafo.add(v);

            for (int j = 0; j < matriz.length; j++) {
                u = new Vertice();
                u.setNumero(j);
                a = new Aresta();

                if (matriz[i][j] != null) { // adiciona somente adjacencias certas
                    a.setV1(v);
                    a.setV2(u);
                    a.setLabel(matriz[i][j]);
                    listaArestasVerticeAtual.add(a);
                }
            }
//            System.out.println("LISTA : " + listaArestasVerticeAtual.toString());
            adjacenciasVerticeAtual.add(new ArrayList<Aresta>());
            adjacenciasVerticeAtual.get(i).addAll(listaArestasVerticeAtual);
            
            matrizGrafo.get(v).addAll(listaArestasVerticeAtual);
            
            listaArestasVerticeAtual.clear();
        }
    }

    static void printGrafo() {

        for (Map.Entry entry : matrizGrafo.entrySet()) {
            System.out.println("VERTICE : " + entry.getKey().toString());
            for (Aresta aresta : (List<Aresta>) entry.getValue()) {
                System.out.println("Aresta " + aresta.toString());
            }
            System.out.println("\n");
        }

        System.out.println("------------------ACABOU HASH----------------------------");
    }

    static void printMatriz() {
        for (int l = 0; l < numeroInstancias; l++) {
            for (int c = 0; c < numeroInstancias; c++) {
                System.out.print(" " + matriz[l][c]);
            }
            System.out.println("\n");
        }
    }

    static void componentesDoGrafo() {
        
       int posVetices = 0;
        while(!verticesGrafo.isEmpty()){
            verticesGrafo.remove(posVetices);
            while(!adjacenciasVerticeAtual.isEmpty()){
                for(int i = 0; i < adjacenciasVerticeAtual.get(posVetices).size(); i++){
                    
                }
                
            }
        }
        
        System.out.println("ARRAY VERTICES " + verticesGrafo.toString());
        System.out.println("ARRAY ARESTAS " + adjacenciasVerticeAtual.toString());

    }

    static void listaLabels() {

    }

    public static void main(String[] args) throws IOException {
        lerArquivo();

    }

}
