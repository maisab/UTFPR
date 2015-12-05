/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apscaminhosminimos.grafo;

/**
 *
 * @author Maa - PC
 * @param <U>
 * @param <V>
 */
public class Aresta<U extends Vertice, V extends Vertice> {

    private Vertice v1 = null;
    private Vertice v2 = null;
    private int peso;
    private boolean percorrida = false;

    public Aresta() {
    }

    public Aresta(Vertice v1, Vertice v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Vertice getV1() {
        return v1;
    }

    public void setV1(Vertice v1) {
        this.v1 = v1;
    }

    public Vertice getV2() {
        return v2;
    }

    public void setV2(Vertice v2) {
        this.v2 = v2;
    }

    public boolean isPercorrida() {
        return percorrida;
    }

    public void setPercorrida(boolean percorrida) {
        this.percorrida = percorrida;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "Aresta{" + "v1=" + v1 + ", v2=" + v2 + ", peso=" + peso + '}';
    }

    
    
    

}
