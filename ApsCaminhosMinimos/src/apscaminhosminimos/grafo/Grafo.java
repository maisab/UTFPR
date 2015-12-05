package apscaminhosminimos.grafo;

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

    public void adicionaVertice(Vertice v) {
        this.grafo.put(v, new ArrayList<Aresta>());
//        this.grafo.get(v).addAll(listaArestas);
    }

    public void adicionaArestaPorVertice(Vertice v, Aresta a) {
        ArrayList<Aresta> aresta = new ArrayList<>();

        for (Map.Entry entry : this.grafo.entrySet()) {
            if (entry.getKey().equals(v)) {
                aresta = (ArrayList<Aresta>) entry.getValue();
                aresta.add(a);//adiciono a aresta atual
                entry.setValue(aresta); //coloco o array de aresta de volta
            }
        }
    }

    public void printGrafo() {
        for (Map.Entry entry : this.grafo.entrySet()) {
            System.out.println("\n");
            System.out.println("VERTICE : " + entry.getKey().toString());
            for (Aresta aresta : (List<Aresta>) entry.getValue()) {
                System.out.println("Aresta " + aresta.toString());
            }
        }
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

//        System.out.println("não ENCONTROU VERTICE");
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

    public void deleteAll() {
        grafo.clear();

    }
}
