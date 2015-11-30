/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author nani
 */
public class Grafo {
    HashMap<Vertice, Aresta> grafo = new HashMap<>();
//     ArrayList<Vertice> vertice = new ArrayList<>();
//     ArrayList<Aresta> aresta = new ArrayList<>();

    public HashMap<Vertice, Aresta> getGrafo() {
        return grafo;
    }

    public void setGrafo(HashMap<Vertice, Aresta> grafo) {
        this.grafo = grafo;
    }
    
    
}
