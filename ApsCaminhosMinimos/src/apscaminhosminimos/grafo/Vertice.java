/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apscaminhosminimos.grafo;

/**
 *
 * @author Maa - PC
 */
public class Vertice {
    private String id;
    private int numero;
    private boolean status = true;
    private int distancia;
    private Vertice pai;
    private boolean visitar;
    

    public Vertice() {
       this.id = String.valueOf(getClass().hashCode());
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public Vertice getPai() {
        return pai;
    }

    public void setPai(Vertice pai) {
        this.pai = pai;
    }

    public boolean isVisitar() {
        return visitar;
    }

    public void setVisitar(boolean visitar) {
        this.visitar = visitar;
    }

    

    
    
    
    @Override
    public String toString() {
        return "Vertice{" + "numero=" + numero + '}';
    }

    
}
