/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvoreroteamento;

/**
 *
 * @author Maa - PC
 */
public class Node {

    private String conteudoNo;
    private Node esquerda;
    private Node direita;

    public Node(String conteudoNo) {
        super();
        this.conteudoNo = conteudoNo;
    }

    public String getConteudoNo() {
        return conteudoNo;
    }

    public void setConteudoNo(String conteudoNo) {
        this.conteudoNo = conteudoNo;
    }

    public Node getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(Node esquerda) {
        this.esquerda = esquerda;
    }

    public Node getDireita() {
        return direita;
    }

    public void setDireita(Node direita) {
        this.direita = direita;
    }

    @Override
    public String toString() {
        return "Node{" + "conteudoNo=" + conteudoNo + ", esquerda=" + esquerda + ", direita=" + direita + '}';
    }

}
