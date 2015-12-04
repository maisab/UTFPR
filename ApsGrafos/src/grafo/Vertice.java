/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;

/**
 *
 * @author Maa - PC
 */
public class Vertice {
    private String id;
    private int numero;
    private boolean status = true;
    private int cor; //0 - branco; 1 - cinza; 2 - preto
    

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

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }

    
    @Override
    public String toString() {
        return "Vertice{" + "numero=" + numero + '}';
    }

    
}
