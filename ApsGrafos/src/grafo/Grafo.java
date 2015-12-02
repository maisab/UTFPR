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
    
    public void getNumeroVertices(){
        System.out.println("NUMERO DE VERTICES DO GRAFO : " + this.grafo.keySet().size());
    }
    
    public ArrayList<Vertice> getVertices(){
        ArrayList<Vertice> vertices = new ArrayList<>();
        
        for(Map.Entry entry : this.grafo.entrySet()){
            vertices.add((Vertice)entry.getKey());
        }
        
        return vertices;
    }
    
    public ArrayList<String> getLabels(){
        ArrayList labels = new ArrayList<>();
        
        for(Map.Entry entry : this.grafo.entrySet()){
            for(Aresta e : (List<Aresta>) entry.getValue()){
                if(!labels.contains(e.getLabel())){
                    labels.add(e.getLabel());
                }
            }
        }
        
        return labels;
    }

}
