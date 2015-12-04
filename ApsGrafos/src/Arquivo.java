
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
//    static ArrayList<Vertice> verticesNaoVisitados;
//    static ArrayList<Aresta> adjacenciasVertice;

    public Arquivo() {

    }

    private static void lerArquivo() throws FileNotFoundException, IOException {
        String linha = "";
        FileReader file = new FileReader(new File("LDGraph20_20.txt"));
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
//                return;
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
//        printMatriz();
        montaGrafo();
//        matrizGrafo.printGrafo();
        contaCC();
//        contaAdjacenciasPorVertice();

    }

    public static void contaAdjacenciasPorVertice() {
        for (Map.Entry entry : matrizGrafo.getGrafo().entrySet()) {
//            for(List<Aresta> a : matrizGrafo.getGrafo().values()){
            System.out.println("PARA O VERTICE  " + entry.getKey().toString() + " LISTA ADJ  " + entry.getValue());
//            }
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

    static void montaGrafo() {
        matrizGrafo = new Grafo();

        List<Aresta> listaArestasVerticeAtual = new ArrayList<>();
        Vertice v = null;
        Vertice u = null;
        Aresta a = null;

        for (int i = 0; i < matriz.length; i++) {
//            System.out.println(" I IGUAL A  " + i);
            v = criaVertice(i);
//            System.out.println("CRIANDO VERTICE  : " + v.toString());
//            v.setNumero(i);
//            matrizGrafo.put(v, new ArrayList<Aresta>());
//            verticesGrafo.add(v);

            for (int j = 0; j < matriz.length; j++) {
                u = criaVertice(j);
                u.setNumero(j);
                a = new Aresta();

                if ((!matriz[i][j].equals("20"))) { // adiciona somente adjacencias certas

                    a.setV1(v);
                    a.setV2(u);
                    a.setLabel(matriz[i][j]);
//                    System.out.println("VERTICE : " + v.toString() + " ARESTA " + a.toString());
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

        if (verticesCriados.isEmpty()) {
            for (int i = 0; i < numeroInstancias; i++) {
                v = new Vertice();
                v.setNumero(i);
                verticesCriados.add(v);
//                System.out.println("VERTICES CRIADOS" + verticesCriados.toString());
//                System.out.println("numero + " + numero);
            }
            v = verticesCriados.get(numero);
        } else {
            v = verticesCriados.get(numero);
//            System.out.println("PEGOU VERTICE EXISTENTE" + v.toString());
        }
//        System.out.println("\nCRIA VERTICES : " + verticesCriados.toString());

        return v;
    }

//       static void contaComponentes() {
////        System.out.println("\nCRIA VERTICES : " + verticesCriados.toString());
//        Vertice v = new Vertice();
//        int componentes = 0;
//        ArrayList<Aresta> aux = new ArrayList<>();
//
////        verticesNaoVisitados = matrizGrafo.getVerticesGrafo();
////        System.out.println("VERTICES NÃO VISITADOS : " + verticesNaoVisitados.toString());
//
////        ArrayList<Aresta> adjacentes = matrizGrafo.getListaArestasAdjacentes(verticesNaoVisitados.get(0));
////        System.out.println("ARESTAS ADJACENTES : " + adjacentes.toString());
//
////        verticesNaoVisitados.get(0).setStatus(false);
////        verticesNaoVisitados.remove(0); //removo o primeiro : já peguei os adjacentes
//
////        while (!verticesNaoVisitados.isEmpty()) {
//            componentes++;
//            System.out.println(" -------------- COMPONENTES -------------");
////            while (!adjacentes.isEmpty()) {
////                v = adjacentes.get(0).getV2(); //vertice atual
//                v.setStatus(false);
//                System.out.println("\n VERTICE ATUAL : " + v.getNumero());
//                aux = matrizGrafo.getListaArestasAdjacentes(v);
//                System.out.println("ADJACENCIAS VERTICE ATUAL  : " + aux.toString());
//
//                for (int i = 0; i < aux.size(); i++) {
//                    if (!aux.get(i).isPercorrida() && aux.get(i).getV2().isStatus()) {
//                        adjacentes.add(aux.get(i));
//                        aux.get(i).setPercorrida(true);
//                        aux.get(i).getV2().setStatus(false);
//                    }
//                }
//
//                aux.removeAll(aux);
//                aux.clear();
//                for (int j = 0; j < verticesNaoVisitados.size(); j++) {
//                    if (verticesNaoVisitados.get(j).equals(v)) {
//                        verticesNaoVisitados.remove(j);
//                        System.out.println("VERTICE REMOVIDO VERTICES NAO VISITADOS + " + v.getNumero());
//                    }
//                }
//                System.out.println("ARESTA REMOVIDA ADJACENTES + " + adjacentes.get(0));
//                adjacentes.remove(0);
//                System.out.println("ADJACENTES ATUAL : " + adjacentes.toString());
//            }
//            if (verticesNaoVisitados.size() >= 0) {
//                adjacentes = matrizGrafo.getListaArestasAdjacentes(verticesNaoVisitados.get(0));
//                verticesNaoVisitados.get(0).setStatus(false);
//                verticesNaoVisitados.remove(0);
//            }
//
//        }
//
//        System.out.println("componentes : " + componentes);
////        ArrayList<Aresta> auxAdj = new ArrayList<Aresta>();
//////        boolean estaNaLista = false;
////
//////
////        int componentes = 1;
////        while (!verticesNaoVisitados.isEmpty()) {
////            System.out.println("\n--------------- NÃO VISITADOS --------------\n");
////            System.out.println("\n VERTICES NÃO VISITADOS " + verticesNaoVisitados.toString() + "\n");
////            adjacentes = matrizGrafo.getListaArestasAdjacentes(verticesNaoVisitados.get(0));
////            System.out.println("ADJACENTES : " + adjacentes.toString());
////
////            verticesNaoVisitados.remove(0);
////
////            while (!adjacentes.isEmpty()) {
////                auxAdj = matrizGrafo.getListaArestasAdjacentes(adjacentes.get(0).getV2());
////                System.out.println("VERTICE ATUAL : " + adjacentes.get(0).getV2());
////                System.out.println("\n ----------- AUX ADJ " + auxAdj.toString());
////                System.out.println("TAMANHO AUX ADJ : " + auxAdj.size());
////
////                for (int j = 0; j < auxAdj.size(); j++) {
////                    if (!auxAdj.get(j).isPercorrida()) {
////                        adjacentes.add(auxAdj.get(j));
////                    }
////                }
////
////                adjacentes.get(0).setPercorrida(true);
////
////                for (int i = 0; i < verticesNaoVisitados.size(); i++) {
////                    if (verticesNaoVisitados.get(i).equals(adjacentes.get(0).getV2())) {
////                        verticesNaoVisitados.remove(i);
////                    }
////                }
////
////                adjacentes.remove(0);
////                auxAdj.clear();
////                System.out.println("TAMANHO VERTICES ADJACENTES  " + adjacentes.size());
////                System.out.println("TAMANHO VERTICES NAO PERCORRIDOS  " + verticesNaoVisitados.size());
////            }
////
////            componentes++;
////        }
////
////        System.out.println("COMPONENTES :  " + componentes);
//    }
    static void contaCC() {
        ArrayList<Vertice> verticesNaoVisitados = matrizGrafo.getVerticesGrafo();
        ArrayList<Vertice> verticesVisitados = new ArrayList<>();
        ArrayList<Vertice> adjacentes = new ArrayList<>();
        ArrayList<Vertice> auxAdjacentes = new ArrayList<>();
        int componentes = 0;

        while (!verticesNaoVisitados.isEmpty()) {
            componentes++;

            adjacentes = matrizGrafo.getListaVerticesAdjacentes(verticesNaoVisitados.get(0));
            verticesVisitados.add(verticesNaoVisitados.get(0));
            System.out.println("REMOVENDO VERTICE :" + verticesNaoVisitados.get(0));
            verticesNaoVisitados.remove(0);

            System.out.println("--------------------------CONTOU COMPONENTE--------------------------");
            while (!adjacentes.isEmpty()) {

                auxAdjacentes = matrizGrafo.getListaVerticesAdjacentes(adjacentes.get(0)); //recebo os adjacentes dele

                for (int j = 0; j < auxAdjacentes.size(); j++) {
                    if (!verticesVisitados.contains(auxAdjacentes.get(j)) && !adjacentes.contains(auxAdjacentes.get(j))) {
                        adjacentes.add(auxAdjacentes.get(j));
                    }
                }

                for (int k = 0; k < verticesNaoVisitados.size(); k++) {
                    if (verticesNaoVisitados.get(k).equals(adjacentes.get(0))) {

                        verticesNaoVisitados.remove(k);
                    }
                }

                System.out.println("REMOVE VERTICE " + adjacentes.get(0));
                verticesVisitados.add(adjacentes.get(0));
                adjacentes.remove(0);

                System.out.println("NÃO VISITADOS : " + verticesNaoVisitados.toString());
                System.out.println("VISITADOS : " + verticesVisitados.toString());
                System.out.println("ADJACENTES : " + adjacentes.toString());
                System.out.println("-------PROXIMO ADJACENTE ------------------");
            }

            System.out.println("COMPONENTES : " + componentes);

        }

        /*ANTERIOR*/
//
//        while (!verticesNaoVisitados.isEmpty()) {
//            componentes++;
//
//            for (Aresta a : matrizGrafo.getListaArestasAdjacentes(verticesNaoVisitados.get(0))) {
//                if (!a.getV2().equals(verticesNaoVisitados.get(0))) {
//                    adjacentes.add(a);
//                }
//            }
//            verticesVisitados.add(verticesNaoVisitados.get(0));
//
//            verticesNaoVisitados.get(0).setStatus(false);
//            System.out.println("REMOVO VERTICE  " + verticesNaoVisitados.get(0));
//            verticesNaoVisitados.remove(0);
//
//            while (!adjacentes.isEmpty()) {
//                System.out.println("VERTICE ATUAL " + adjacentes.get(0).getV2());
//                System.out.println("\nVERTICES NÃO VISITADOS : " + verticesNaoVisitados.toString());
//                System.out.println("VERTICES VISITADOS : " + verticesVisitados.toString());
//                System.out.println("VERTICES ADJACENTES : " + adjacentes.toString() + "\n");
////                
//                auxAdjacentes = matrizGrafo.getListaArestasAdjacentes(adjacentes.get(0).getV2());
//                verticesVisitados.add(adjacentes.get(0).getV2());
//                adjacentes.get(0).setPercorrida(true);
//
//                for (int i = 0; i < verticesNaoVisitados.size(); i++) { //remover da lista de vertices nao percorridos
//                    if (verticesNaoVisitados.get(i).equals(adjacentes.get(0).getV2())) {
//
//                        System.out.println("REMOVENDO VERTICE " + verticesNaoVisitados.get(i));
//                        verticesVisitados.add(verticesNaoVisitados.get(i));
//                        verticesNaoVisitados.get(i).setStatus(false);
//                        verticesNaoVisitados.remove(i);
//                    }
//                }
//
//                for (int j = 0; j < auxAdjacentes.size(); j++) {
//                    if ((!auxAdjacentes.get(j).isPercorrida()) && (auxAdjacentes.get(j).getV2().isStatus())
//                            && (!verticesVisitados.contains(auxAdjacentes.get(j).getV2()))) {
//                        System.out.println("ADICIONANDO ARESTA : " + auxAdjacentes.get(j));
//                        adjacentes.add(auxAdjacentes.get(j));
//                    }
//                }
//
//                System.out.println("REMOVENDO ARESTA : " + adjacentes.get(0).toString());
//                verticesVisitados.add(adjacentes.get(0).getV2());
//                adjacentes.remove(0);
//
//                System.out.println("\n------------------- proxima iteração --------------------\n");
//            }
//
//        }
//
//        for (int i = 0; i < matrizGrafo.getVerticesGrafo().size(); i++) {
//            if (matrizGrafo.getVerticesGrafo().get(i).isStatus()) {
//                System.out.println("VERTICE VÁLIDO ");
//            }
//        }
//        System.out.println("COMPONENTES : " + componentes);
    }

    public static void main(String[] args) throws IOException {
        lerArquivo();

    }

}
