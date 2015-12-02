
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
//    static ArrayList<String> listaLabels;

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
            v = new Vertice();
            v.setNumero(i);
//            matrizGrafo.put(v, new ArrayList<Aresta>());
//            verticesGrafo.add(v);

            for (int j = 0; j < matriz.length; j++) {
                u = new Vertice();
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

    static void contaComponentes() {

        ArrayList<Vertice> verticesNaoVisitados = matrizGrafo.getVertices();
        ArrayList<Vertice> adjacentes = new ArrayList<Vertice>();
        ArrayList<Vertice> auxAdj = new ArrayList<Vertice>();
        boolean estaNaLista = false;

        int componentes = 1;
        while (!verticesNaoVisitados.isEmpty()) {
            System.out.println("\n--------------- NÃO VISITADOS --------------\n");
            System.out.println("\nVERTICES NÃO VISITADOS " + verticesNaoVisitados.toString());
            adjacentes = matrizGrafo.getListaVerticesAdjacentes(verticesNaoVisitados.get(0));
            System.out.println("ADJACENTES : " + adjacentes.toString());
            verticesNaoVisitados.get(0).setStatus(false);
            verticesNaoVisitados.remove(0);

            while (!adjacentes.isEmpty()) {
//                System.out.println("\n ENTROU WHILE ADJACENTES -------------");
                auxAdj = matrizGrafo.getListaVerticesAdjacentes(adjacentes.get(0));
                System.out.println("TAMANHO AUX ADJ " + auxAdj.size());
                System.out.println("-----------AUX ADJACENTES : " + auxAdj.toString());

                for (int j = 0; j < auxAdj.size(); j++) {
                    for (int k = 0; k < adjacentes.size(); k++) {
                        if ( (adjacentes.get(k).getNumero() == auxAdj.get(j).getNumero())) {
                            estaNaLista = true;
                        }
                    }
                    if (!estaNaLista) {
                        adjacentes.add(auxAdj.get(j));
                    }
                    estaNaLista = false;
                }
                
                System.out.println("ADJACENTES : " + adjacentes.toString());
                System.out.println("TAMANHO VERTICES NÃO VISITADOS " + verticesNaoVisitados.size());

                for (int i = 0; i < verticesNaoVisitados.size(); i++) {
                    if (verticesNaoVisitados.get(i).getNumero() == adjacentes.get(0).getNumero()) {
                        
                        verticesNaoVisitados.get(i).setStatus(false);
                        verticesNaoVisitados.remove(i);
                    }
                }

                System.out.println("REMOVENDO VERTICE : " + adjacentes.get(0));
                adjacentes.get(0).setStatus(false);
                adjacentes.remove(0);
            }
            componentes = componentes + 1;

//            System.out.println("PASSOU VERTICES");
////            System.out.println("VERTICE ATUAL : " + verticesNaoVisitados.get(0));
////            adjacencias = matrizGrafo.getListaAdjacencia(verticesNaoVisitados.get(0)); // recebe a lista de adjacencia do vertice atual
//            adjacentes = matrizGrafo.getListaVerticesAdjacentes(verticesNaoVisitados.get(0));
//            System.out.println("VERTICES ADJACENTES AO VERTICE : " + verticesNaoVisitados.get(0) + "\n" + adjacentes.toString());
//            System.out.println("\n");
//            verticesNaoVisitados.get(0).setStatus(false); // visitado
//            verticesNaoVisitados.remove(0); // remove o primeiro vertice da lista
//
//            while (!adjacentes.isEmpty()) {
//                System.out.println("DENTRO DOS ADJACENTES");
//                System.out.println("ADJACENTES: " + adjacentes.toString());
//                System.out.println("\n");
//
//                auxAdj = matrizGrafo.getListaVerticesAdjacentes(adjacentes.get(0));
//                System.out.println("POSIÇÃO 0 : "  + adjacentes.get(0));
//                System.out.println("AUX ADJ " + auxAdj.toString());
//
//                for (int i = 0; i < auxAdj.size(); i++) {
//                    if ( (!adjacentes.contains(auxAdj.get(i))) && auxAdj.get(i).isStatus()) {
//                        adjacentes.add(auxAdj.get(i));
//                    }
//                    System.out.println("ADICIONANDO VERTICE : " + auxAdj.get(i));
//                }
//                adjacentes.get(0).setStatus(false);
//                verticesNaoVisitados.remove(adjacentes.get(0));
//                adjacentes.remove(adjacentes.get(0));
//                System.out.println("TAMANHO ADJACENTES + " + adjacentes.size());
//                System.out.println("TAMANHO VERTICES NAO VISITADOS + " + verticesNaoVisitados.size());
//                for (int i = 0; i < adjacentes.size(); i++) {
////                    adjacentes.addAll(matrizGrafo.getListaVerticesAdjacentes((Vertice)adjacentes.get(i))); // para cada vertice adjacente, adiciona seus adjacentes na lista
//
////                        System.out.println("\n VERTICE DE ADJ ATUAL : \n" + adjacentes.get(i).toString());
//                    auxAdj = matrizGrafo.getListaVerticesAdjacentes((Vertice) adjacentes.get(i)); //recebe os adjs dos vertice atual da lista Adjacencias
////                        System.out.println("AUX ADJ " + auxAdj.toString());
//                    for (Vertice v : auxAdj) {
//                        adjacentes.add(v);
//                    }
////                        
//                    for (int j = 0; j < verticesNaoVisitados.size(); j++) {
////                        System.out.println("DENTRO DO FOR ---");
//                        if (verticesNaoVisitados.get(j).equals(adjacentes.get(i))) {
//                            verticesNaoVisitados.remove(adjacentes.get(0)); //remove da lista de vertices não visitados
//                            System.out.println("REMOVENDO VERTICE  DOS VERTICES NÃO VISITADOS: " + adjacentes.get(i).toString());
//                        }
//                    }
//                    System.out.println("REMOVENDO VERTICE  DOS VERTICES ADJACENTES: " + adjacentes.get(i).toString());
//                    adjacentes.remove(adjacentes.get(0)); //remove da lista de adjacencias
//                }
//            }
//            componentes++;
        }
        System.out.println("ADJACENTES : " + adjacentes.toString());
        System.out.println("TAMANHO ADJ : " + adjacentes.size());

        System.out.println(
                "NUMERO DE COMPONENTES DO GRAFO : " + componentes);

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
