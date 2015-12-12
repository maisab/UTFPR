package apscaminhosminimos;

import apscaminhosminimos.grafo.Aresta;
import apscaminhosminimos.grafo.Djikstra;
import apscaminhosminimos.grafo.Grafo;
import apscaminhosminimos.grafo.Vertice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Maa - PC
 */
public class Arquivo {

    static ArrayList<Vertice> verticesCriados = new ArrayList<>();
    static Grafo grafo;

    public Arquivo() {

    }

    public static void lerArquivo() throws FileNotFoundException, IOException {

        grafo = new Grafo();
        Vertice v = null;
        Vertice u = null;
        Aresta a = null;
        int contador = 0;

        String[] linhaAtual = null;
        String linha = "";

        FileReader file = new FileReader(new File("roadNet-PA.txt"));
        BufferedReader br = new BufferedReader(file);

        linha = br.readLine();
        linha = br.readLine();
        linha = br.readLine();
        linha = br.readLine(); //pulando o cabeçalho

        while (linha != null) {
            contador++;
            if (contador < 3000) {
                linha = br.readLine();
                linhaAtual = linha.split("\t");

//                if (grafo.getVertice(Integer.parseInt(linhaAtual[0])) == null) { //se o vertice não existe
                v = criaVertice(Integer.parseInt(linhaAtual[0]));
//                    System.out.println("CRIOU VERTICE : " + v.getNumero());
//                } else { //se o vertice já existe
//                    v = grafo.getVertice(Integer.parseInt(linhaAtual[0]));
                System.out.println("ACHOU VERTICE : " + v.getNumero());
//                }

//                if (grafo.getVertice(Integer.parseInt(linhaAtual[1])) == null) {
                u = criaVertice(Integer.parseInt(linhaAtual[1]));
                System.out.println("ACHOU VERTICE : " + v.getNumero());
//                } else {
//                    u = grafo.getVertice(Integer.parseInt(linhaAtual[1]));
//                }

                a = new Aresta();
                a.setV1(v);
                a.setV2(u);
                a.setPeso(Math.abs(u.getNumero() - v.getNumero()));

                grafo.adicionaArestaPorVertice(v, a);

            } else {

                return;
            }
        }

        grafo.printGrafo();
//        System.out.println(grafo.getVerticesGrafo().toString());
    }

    static Vertice criaVertice(int numero) {
        Vertice v = null;

        if (grafo.getVertice(numero) == null) {
            v = new Vertice();
            v.setNumero(numero);
            v.setDistancia(99999999);
            grafo.adicionaVertice(v);
            System.out.println("adiciona v " + v.getNumero());
        } else {
            v = grafo.getVertice(numero);
            System.out.println("achou v " + v.getNumero());
        }
        return v;
    }

    static void djikstra(int v1, int v2) {
        grafo.printGrafo();
        int menorDistancia = 9999999;
        int posMenor = 0;
        Vertice raiz = null;
        Vertice u = null;
        Vertice vAtual = grafo.getVertice(v2);
        ArrayList<Vertice> vertices = grafo.getVerticesGrafo();
        ArrayList<Vertice> caminhoMinimo = new ArrayList<>();

        raiz = grafo.getVertice(v1);
        raiz.setDistancia(0); // raiz recebe distancia 0
        raiz.setPai(null); //seta o anterior como nulo

        while (!vertices.isEmpty()) { //enquanto existirem vertices na lista

            for (int i = 0; i > vertices.size(); i++) { //encontra o vertice com a menor distancia
                if (vertices.get(i).getDistancia() < menorDistancia) {
                    menorDistancia = vertices.get(i).getDistancia();
                    u = vertices.get(i);
                    posMenor = i;
                }
            }
            vertices.remove(posMenor);

            relaxaAresta(u);
        }

        if (vAtual.getPai() != null) { // ese existir adjacencia
            while (!vAtual.equals(u)) { // enquanto não for a raiz
                caminhoMinimo.add(vAtual);
                vAtual.setPai(vAtual.getPai());
            }
        } else {
            System.out.println("Não existe caminho");
        }
        System.out.println(caminhoMinimo.toString());
    }

    static void relaxaAresta(Vertice u) {
        Aresta e = null;
        int distanciaVertices = 0;
        ArrayList<Vertice> adjacentesU = grafo.getListaVerticesAdjacentes(u);;

        for (int j = 0; j < adjacentesU.size(); j++) { //vai relaxar as arestas
            //distanciaVertices = u.getDistancia()
            e = grafo.getArestaVU(u, adjacentesU.get(j));

            distanciaVertices = u.getDistancia() + e.getPeso();

            if (distanciaVertices < adjacentesU.get(j).getDistancia()) {
                adjacentesU.get(j).setDistancia(distanciaVertices);
                adjacentesU.get(j).setPai(u);
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        int n = 0;
//        Scanner i = new Scanner(System.in);
////        n = i.nextInt();

//        System.out.println("Digite v1 : ");
        lerArquivo();

//        do{
        djikstra(5, 15);

//        }while(n != 0);
//
//        Vertice v = criaVertice(5);
//        Vertice u = criaVertice(15);
//        new Djikstra().encontrarMenorCaminhoDijkstra(grafo, v, u);
    }

}
