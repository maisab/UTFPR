/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apscaminhosminimos.grafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Maa - PC
 */
public class Djikstra {

    //Atributos usados na função encontrarMenorCaminho
    //Lista que guarda os vértices pertencentes ao menor caminho encontrado
    List<Vertice> menorCaminho = new ArrayList<Vertice>();

    //Variável que recebe os vértices pertencentes ao menor caminho
    Vertice verticeCaminho = new Vertice();

    //Variável que guarda o vértice que está sendo visitado 
    Vertice atual = new Vertice();

    //Variável que marca o vizinho do vértice atualmente visitado 
    Vertice vizinho = new Vertice();

    //Corte de vértices que já tiveram suas distâncias marcadas e cujos vizinhos não foram visitados
    List<Vertice> fronteira = new ArrayList<Vertice>();

    //Guarda o número de vértices não visitados
    int verticesNaoVisitados;

    //Algoritmo de Dijkstra
    public List<Vertice> encontrarMenorCaminhoDijkstra(Grafo grafo, Vertice v1, Vertice v2) {

        //No início, todos os vértices do grafo não foram visitados
        verticesNaoVisitados = grafo.getVerticesGrafo().size();

        //O primeiro nó a ser visitado é o da origem do caminho
        atual = v1;
        //Adiciona o primeiro nó no corte
        fronteira.add(atual);
        //Adiciona a origem na lista do menor caminho
        menorCaminho.add(atual);

        //Colocando a distancias iniciais
        for (int i = 0; i < grafo.getVerticesGrafo().size(); i++) {

            //Nó atual tem distância zero, e todos os outros, 9999(infinita)
            if (grafo.getVerticesGrafo().get(i).equals(atual)) {

                grafo.getVerticesGrafo().get(i).setDistancia(0);

            } else {

                grafo.getVerticesGrafo().get(i).setDistancia(9999);

            }
        }

        //O algoritmo continua até que todos os vértices sejam visitados
        while (verticesNaoVisitados != 0) {

            //Toma-se sempre o vértice com menor distância, que é o primeiro da lista do corte
            atual = this.fronteira.get(0);
            /*Para cada vizinho (cada aresta), calcula-se a sua possível distância,
             somando a distância do vértice atual com a da aresta correspondente.
             Se essa distância for menor que a distância do vizinho, esta é atualizada.
             */
            for (int i = 0; i < grafo.getListaArestasAdjacentes(atual).size(); i++) {

                vizinho = grafo.getListaArestasAdjacentes(atual).get(i).getV2();
                if (!vizinho.isStatus()) {

                    vizinho.setPai(atual);

                    //Comparando a distância do vizinho com a possível distância
                    if (vizinho.getDistancia() > (atual.getDistancia() + grafo.getListaArestasAdjacentes(atual).get(i).getPeso())) {

                        vizinho.setDistancia(atual.getDistancia() + grafo.getListaArestasAdjacentes(atual).get(i).getPeso());

                        /*Se o vizinho é o vértice procurado, e foi feita uma mudança na distância,
                         * a lista com o menor caminho anterior é apagada, pois existe um caminho menor ainda.
                         * Cria-se a nova lista do menor caminho, com os vértices pais, até o vértice origem.
                         * */
                        if (vizinho == v2) {
                            menorCaminho.clear();
                            verticeCaminho = vizinho;
                            menorCaminho.add(vizinho);
                            while (verticeCaminho.getPai() != null) {

                                menorCaminho.add(verticeCaminho.getPai());
                                verticeCaminho = verticeCaminho.getPai();

                            }
                            //Ordena a lista do menor caminho, para que ele seja exibido da origem ao destino.
                            //Collections.sort(menorCaminho);

                        }
                    }
                    //Cada vizinho, depois de visitado, é adicionado ao corte
                    this.fronteira.add(vizinho);
                }

            }
            //Marca o vértice atual como visitado e o retira do corte
            atual.setVisitar(true);
            verticesNaoVisitados--;
            this.fronteira.remove(atual);
            /*Ordena a lista do corte, para que o vértice com menor distância fique na primeira
             * posição*/

            //Collections.sort(fronteira);

        }

        return menorCaminho;
    }

}
