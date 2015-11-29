
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
    static ArrayList<String> lista = new ArrayList<String>(); //contem a quantidade de label de cada label,eh composto pelo nome do laber +  a quantidade delabel
    static Grafo grafo = new Grafo();
    static Grafo grafoMinimo = new Grafo();

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
        salvarHash();
        // calculo
     /*   for (Vertice v : matrizGrafo.keySet()) {
         System.out.println("Vertice : " + v.getNumero() + " Adjacencias  " + matrizGrafo.get(v).toString());
         }*/

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
            /*  for (Aresta s : adjacencias) {
             System.out.println("Lista Adjacencias : " + adjacencias.get(i).getLabel());

             }*/
            matrizGrafo.put(v, adjacencias);
            adjacencias.clear();

        }

    }

    static void criaGrafo() { //OK
        int numero = 0; // quantidade de vertice
        ArrayList<Vertice> ve = new ArrayList<Vertice>();
        ArrayList<Aresta> a = new ArrayList<Aresta>();
        for (int i = 0; i < matriz.length; i++) {
            Vertice v = new Vertice();
            v.setNumero(numero);
            numero++;
            ve.add(v);
        }
        for (int i = 0; i < matriz.length; i++) {
            for (int j = i; j < matriz.length; j++) {
                if (i != j) {
                    Aresta aresta = new Aresta();
                    aresta.setLabel(matriz[i][j]);

                    aresta.setV1(ve.get(i));
                    aresta.setV2(ve.get(j));
                    a.add(aresta);
                }
            }
        }
        grafo.setAresta(a);
        grafo.setVertice(ve);

    }

    static void adicionaArvoreMinima(Vertice v) {
        ArrayList<Vertice> ver = new ArrayList<Vertice>();
        ArrayList<Aresta> a = new ArrayList<Aresta>();
        if (ver.size() == 0) {
            ver.add(v);
        } else {
            int pos = ver.size() - 1;
            Aresta ar = new Aresta();
            ar.setV1(ver.get(pos));
            ar.setV2(v);
            for (int i = 0; i < grafo.getAresta().size(); i++) {
                if ((grafo.getAresta().get(i).getV1().equals(v) && grafo.getAresta().get(i).getV2().equals(ver.get(pos)))
                        || (grafo.getAresta().get(i).getV2().equals(v) && grafo.getAresta().get(i).getV1().equals(ver.get(pos)))) {
                    ar.setLabel(grafo.getAresta().get(i).getLabel());
                }
            }

        }

    }

    static void mlst() {
        criaGrafo();
        quantidade();
        for (int i = 0; i < grafo.getVertice().size(); i++) {
            Vertice v = new Vertice();
            if (i == 0) {
                adicionaArvoreMinima(grafo.getVertice().get(i));
            }
            v = mvca(grafo.getVertice().get(i));
            adicionaArvoreMinima(v);

        }
    }

    static void quantidade() {

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                String valor;
                if (lista.size() == 0) {
                    lista.add(matriz[i][j] + " " + "1");
                } else {
                    boolean axou = false;
                    for (int k = 0; k < lista.size(); k++) {
                        String aux = lista.get(k);
                        if (aux.split(" ")[0].equals(matriz[i][j])) {
//                            System.out.println("slipt :" + aux.split(" ")[1]);
                            int inc = Integer.parseInt(aux.split(" ")[1]) + 1;
                            aux = aux.split(" ")[0] + String.valueOf(inc);
                            lista.set(k, aux);
                            axou = true;
                        }
                    }
                    if (axou == false) {
                        lista.add(matriz[i][j] + " " + "1");
                    }
                }
            }
        }
        System.out.println("-----------------------");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("\n" + lista.get(i));
        }

    }

    static int quantidadeLabel(String label) {
        int qtd = 0;
        for (int i = 0; i < lista.size(); i++) {
            String aux = lista.get(i).split(" ")[0];
            if (aux.equals(label)) {
                qtd = Integer.parseInt(lista.get(i).split(" ")[1]);
            }
        }

        return qtd;
    }

    static Vertice mvca(Vertice v) {
        // mostrar o menor caminho do vertice para o mlst
        int menor = 0;
        String menorL = "";
        for (int i = 0; i < grafo.getAresta().size(); i++) {
            if (grafo.getAresta().get(i).getV1().equals(v) || grafo.getAresta().get(i).getV2().equals(v)) {
                String label = grafo.getAresta().get(i).getLabel();
                int qtdLabel = quantidadeLabel(label);
                if (qtdLabel < menor) {
                    menor = qtdLabel;
                    menorL = grafo.getAresta().get(i).getLabel();
                }
            }
        }
        for (int i = 0; i < grafo.getAresta().size(); i++) {
            if (grafo.getAresta().get(i).getLabel().equals(label)) {
                if (grafo.getAresta().get(i).getV1().equals(v)) {
                    grafo.getAresta().get(i).getV2().setStatus(false);
                    return grafo.getAresta().get(i).getV2();
                } else if (grafo.getAresta().get(i).getV2().equals(v)) {
                    grafo.getAresta().get(i).getV1().setStatus(false);
                    return grafo.getAresta().get(i).getV1();

                }
            }
        }
        return null;
    }

    static void printarArvoreMinima() {
        for (int i = 0; i < grafoMinimo.getAresta().size(); i++) {
            System.out.println("\n" + grafoMinimo.getAresta().get(i).getV1().getNumero()
                    + " -------------------" + grafoMinimo.getAresta().get(i).getLabel() + "------------------"
                    + grafoMinimo.getAresta().get(i).getV2().getNumero());
        }
    }
    
    static void componente() {
        for (int j = 0; j < lista.size(); j++) {
            String aux = lista.get(j).split(" ")[0];
            for (int i = 0; i < grafo.getAresta().size(); i++) {

            }
        }
    }

    public static void main(String[] args) throws IOException {
        lerArquivo();
        mlst();
    }
    
}
