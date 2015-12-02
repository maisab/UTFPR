
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
    static int componentes = 0;

    static ArrayList<Vertice> verticesCriados = new ArrayList<>();
    static ArrayList<Vertice> verticesNaoVisitados;

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
        verticesNaoVisitados = matrizGrafo.getVertices(); // recebe todos os vertices
//        matrizGrafo.printGrafo();
        contaComponentes();
//        matrizGrafo.printGrafo();
//        matrizGrafo.numeroVertices();
//        listaLabels = matrizGrafo.getLabels();
//        System.out.println("VETOR DE LABELS : " + listaLabels.toString());

//        
//        listaLabels.clear(); //limpa para pegar a lista de labels do proximo grafo
//        adjacenciasGrafo.clear();
//        verticesGrafo.clear();
//        verticesGrafo = matrizGrafo.getVertices();
//        
//        adjacenciasGrafo = matrizGrafo.getAdjacenciasPorVetice(verticesGrafo.get(4));
//        
//        System.out.println("LISTA DE ADJACENCIA VERTICE  : " + verticesGrafo.get(4).toString() + "\n" + adjacenciasGrafo.toString());
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

    static void montaGrafo() {
        matrizGrafo = new Grafo();

        List<Aresta> listaArestasVerticeAtual = new ArrayList<>();
        Vertice v = null;
        Vertice u = null;
        Aresta a = null;

        for (int i = 0; i < matriz.length; i++) {
            v = criaVertice(i);
//            v.setNumero(i);
//            matrizGrafo.put(v, new ArrayList<Aresta>());
//            verticesGrafo.add(v);

            for (int j = 0; j < matriz.length; j++) {
                u = criaVertice(j);
                u.setNumero(j);
                a = new Aresta();

                if ((matriz[i][j] != null) && (!matriz[i][j].equals("20"))) { // adiciona somente adjacencias certas
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

    static Vertice criaVertice(int numero) {
        Vertice v = null;
        if (verticesCriados.size() == 0) {
            for (int i = 0; i < numeroInstancias; i++) {
                v = new Vertice();
                v.setNumero(i);
                verticesCriados.add(v);
            }
//             System.out.println("CRIOU OS VERTICES + " + verticesCriados.toString());
        } else {
            v = verticesCriados.get(numero);
//            System.out.println("PEGOU VERTICE EXISTENTE" + v.toString());
        }
//        System.out.println("\nCRIA VERTICES : " + verticesCriados.toString());

        return v;
    }

    static void contaComponentes() {
//        System.out.println("\nCRIA VERTICES : " + verticesCriados.toString());

        ArrayList<Vertice> verticesNaoVisitados = matrizGrafo.getVertices();
        ArrayList<Aresta> adjacentes = new ArrayList<Aresta>();
        ArrayList<Aresta> auxAdj = new ArrayList<Aresta>();
        boolean estaNaLista = false;

//
        int componentes = 1;
        while (!verticesNaoVisitados.isEmpty()) {
            System.out.println("\n--------------- NÃO VISITADOS --------------\n");
            System.out.println("\n VERTICES NÃO VISITADOS " + verticesNaoVisitados.toString() + "\n");
            adjacentes = matrizGrafo.getListaArestasAdjacentes(verticesNaoVisitados.get(0));
            System.out.println("ADJACENTES : " + adjacentes.toString());

            verticesNaoVisitados.remove(0);
            
            while (!adjacentes.isEmpty()) {
                auxAdj = matrizGrafo.getListaArestasAdjacentes(adjacentes.get(0).getV2());
                System.out.println("VERTICE ATUAL : " + adjacentes.get(0).getV2());
                System.out.println("\n ----------- AUX ADJ " + auxAdj.toString());
                System.out.println("TAMANHO AUX ADJ : " + auxAdj.size());

                for (int j = 0; j < auxAdj.size(); j++) {
                    if (!auxAdj.get(j).isPercorrida()) {
                        adjacentes.add(auxAdj.get(j));
                    }
                }

                adjacentes.get(0).setPercorrida(true);

                for (int i = 0; i < verticesNaoVisitados.size(); i++) {
                    if (verticesNaoVisitados.get(i).equals(adjacentes.get(0).getV2())) {
                        verticesNaoVisitados.remove(i);
                    }
                }

                adjacentes.remove(0);
                auxAdj.clear();
                System.out.println("TAMANHO VERTICES ADJACENTES  " + adjacentes.size());
                System.out.println("TAMANHO VERTICES NAO PERCORRIDOS  " + verticesNaoVisitados.size());
            }

            componentes++;
        }
        
        System.out.println("COMPONENTES :  " + componentes);
    }

    public static void main(String[] args) throws IOException {
        lerArquivo();

    }

}
