/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;

import java.util.ArrayList;

/**
 *
 * @author nani
 */
public class Grafo {
     ArrayList<Vertice> vertice = new ArrayList<>();
     ArrayList<Aresta> aresta = new ArrayList<>();

    public ArrayList<Vertice> getVertice() {
        return vertice;
    }

    public void setVertice(ArrayList<Vertice> vertice) {
        this.vertice = vertice;
    }

    public ArrayList<Aresta> getAresta() {
        return aresta;
    }

    public void setAresta(ArrayList<Aresta> aresta) {
        this.aresta = aresta;
    }
    
    
}
