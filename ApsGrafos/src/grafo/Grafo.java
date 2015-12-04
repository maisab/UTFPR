/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nani
 */
public class Grafo {

    HashMap<Vertice, List<Aresta>> grafo = new HashMap<>();
//     ArrayList<Vertice> vertice = new ArrayList<>();
//     ArrayList<Aresta> aresta = new ArrayList<>();

    public Grafo() {
    }

    public HashMap<Vertice, List<Aresta>> getGrafo() {
        return grafo;
    }

    public void setGrafo(HashMap<Vertice, List<Aresta>> grafo) {
        this.grafo = grafo;
    }

    public void adicionaAresta(Vertice v, List<Aresta> listaArestas) {
        this.grafo.put(v, new ArrayList<Aresta>());
        this.grafo.get(v).addAll(listaArestas);
    }

    public void printGrafo() {
        for (Map.Entry entry : this.grafo.entrySet()) {
            System.out.println("VERTICE : " + entry.getKey().toString());
            for (Aresta aresta : (List<Aresta>) entry.getValue()) {
                System.out.println("Aresta " + aresta.toString());
            }
            System.out.println("\n");
        }
    }

    public void getNumeroVertices() {
        System.out.println("NUMERO DE VERTICES DO GRAFO : " + this.grafo.keySet().size());
    }

    public ArrayList<Vertice> getVerticesGrafo() {
        ArrayList<Vertice> vertices = new ArrayList<>();

        for (Map.Entry entry : this.grafo.entrySet()) {
            vertices.add((Vertice) entry.getKey());
        }

        return vertices;
    }

    public ArrayList<Aresta> getListaArestasAdjacentes(Vertice v) {
        ArrayList<Aresta> adjacencias = new ArrayList<>();
        

        for (Map.Entry entry : this.grafo.entrySet()) {
            if (entry.getKey().equals(v)) {
                for (Aresta e : (List<Aresta>) entry.getValue()) {
                    adjacencias.add(e);
                }
//                System.out.println("ENCONTROU VERTICE");
                return adjacencias;
            }
        }
//        System.out.println("não ENCONTROU VERTICE");

        return adjacencias;
    }

    public ArrayList<Vertice> getListaVerticesAdjacentes(Vertice v) {
        ArrayList<Vertice> adjacentes = new ArrayList<>();
        Vertice aux = new Vertice();

        for (Map.Entry entry : this.grafo.entrySet()) {
            aux = (Vertice) entry.getKey(); 
            if (aux.equals(v)) {
//            if (aux.getNumero() == v.getNumero()) {
                for (Aresta e : (List<Aresta>) entry.getValue()) {
                    adjacentes.add(e.getV2());
                }
//                System.out.println("\n ENCONTROU O VERTICE \n " + " \nADJACENTES  " + adjacentes.toString());
                return adjacentes;
            }
        }

//        System.out.println("\n NÃO ENCONTROU O VERTICE \n");
        return adjacentes;
    }

    public ArrayList<String> getLabels() {
        ArrayList labels = new ArrayList<>();

        for (Map.Entry entry : this.grafo.entrySet()) {
            for (Aresta e : (List<Aresta>) entry.getValue()) {
                if (!labels.contains(e.getLabel())) {
                    labels.add(e.getLabel());
                }
            }
        }

        return labels;
    }

}
