
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
    static Grafo matrizGrafo;
    static String[][] matriz;
    static List<Vertice> verticesGrafo = new ArrayList<>();
    static List<Aresta> adjacenciasGrafo = new ArrayList<>();
    static int componentes = 0;
    static ArrayList<String> listaLabels;

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

//        completarMatriz();
//        printMatriz();
        montaGrafo();
//        matrizGrafo.printGrafo();
//        matrizGrafo.numeroVertices();
        listaLabels = matrizGrafo.getLabels();
        System.out.println("VETOR DE LABELS : " + listaLabels.toString());
        

        
        listaLabels.clear(); //limpa para pegar a lista de labels do proximo grafo
        adjacenciasGrafo.clear();
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

//    static void completarMatriz() {
//        boolean existeNaLista = false;
//
//        for (int i = 0; i < numeroInstancias; i++) {
//            for (int j = 0; j < numeroInstancias; j++) {
//                if (i == j) {
//                    matriz[i][j] = label;
//                }
////                 else if (i > j) {
////                    matriz[i][j] = matriz[j][i];
////                }
//
//                existeNaLista = false;
//
//                for (String auxLabels : listaLabels) { // verifica se o label já esta na lista
//                    if (matriz[i][j] == null) {
//                        existeNaLista = true;
//                    } else if (auxLabels.equals(matriz[i][j])) {
//                        existeNaLista = true;
//                    }
//                }
//
//                if (!existeNaLista) { // se não conter na lista de labels atual, adiciona
//                    listaLabels.add(matriz[i][j]);
//                }
//            }
//        }
//    }

    static void montaGrafo() {
        matrizGrafo = new Grafo();

        List<Aresta> listaArestasVerticeAtual = new ArrayList<>();
        Vertice v = null;
        Vertice u = null;
        Aresta a = null;

        for (int i = 0; i < matriz.length; i++) {
            v = new Vertice();
            v.setNumero(i);
//            matrizGrafo.put(v, new ArrayList<Aresta>());
//            verticesGrafo.add(v);

            for (int j = 0; j < matriz.length; j++) {
                u = new Vertice();
                u.setNumero(j);
                a = new Aresta();

                if ((matriz[i][j] != null) && (!matriz[i][j].equals("20")) ) { // adiciona somente adjacencias certas
                    a.setV1(v);
                    a.setV2(u);
                    a.setLabel(matriz[i][j]);
                    listaArestasVerticeAtual.add(a);
                }
            }
//            adjacenciasGrafo.add(new ArrayList<Aresta>());
//            adjacenciasGrafo.get(i).addAll(listaArestasVerticeAtual);

            matrizGrafo.adicionaAresta(v, listaArestasVerticeAtual);
//            matrizGrafo.get(v).addAll(listaArestasVerticeAtual);

            listaArestasVerticeAtual.clear();
        }
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
        
        List<Vertice> verticesNaoVisitados = verticesGrafo;
        List<Aresta> arestasNaoVisitadas = new ArrayList<>();
        int numeroComponetes = 0;
        int posVerticeAtual = 0;
        
        
        
//        while(verticesNaoVisitados.isEmpty()){
//            numeroComponetes++;
//            for(Aresta e : adjacenciasGrafo.get(posVerticeAtual)){
//                arestasNaoVisitadas.add(e);
//            }
//            verticesNaoVisitados.remove(posVerticeAtual);
////            for(int i = 0; i )
////        }
//        }
        System.out.println("ARRAY VERTICES " + verticesGrafo.toString());
        System.out.println("ARRAY ARESTAS " + adjacenciasGrafo.toString());

    }

//    public void buscaProfundidade(Grafo grafo, int raiz) {
//
////        percArv.add(raiz);
//
////        visitados[raiz] = true;
//
//        int i;
//        for (i = 0; i < verticesGrafo.size(); i++) {
//            if (matriz[raiz][i] != 0 && visitados[i] == false) {
//                buscaProfundidade(grafo, i);
////                percArv.add(raiz);
//            }
//        }
//    }


    public static void main(String[] args) throws IOException {
        lerArquivo();

    }

}
