
import com.sun.javafx.scene.control.skin.VirtualFlow;
import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Maa - PC
 */
public class Arquivo {

    private static String labelMatriz;
    private static int numeroInstancias;
    static Grafo matrizGrafo;
    static String[][] matriz;
    static int componentes = 0;

    static ArrayList<Vertice> verticesCriados = new ArrayList<>();
//    static ArrayList<Vertice> verticesNaoVisitados;
//    static ArrayList<Aresta> adjacenciasVertice;

    public Arquivo() {

    }

    private static void lerArquivo() throws FileNotFoundException, IOException {
        String linha = "";
        FileReader file = new FileReader(new File("LDGraph20_20.txt"));
        BufferedReader br = new BufferedReader(file);
        ArrayList<String> listaLinhaAtual = new ArrayList<>();

        linha = br.readLine();
        numeroInstancias = Integer.parseInt(String.valueOf(linha.split(" ")[0]));
        labelMatriz = linha.split(" ")[1];

        linha = br.readLine();
        while (linha != null) {
            if (linha.trim().length() == 0) { // acabou a matriz atual
                insereMatrizAtual(listaLinhaAtual);
                System.out.println("\n****************ACABOU A MATRIZ*********************\n");
                listaLinhaAtual.clear(); //limpa a lista
//                return;
            } else {
                listaLinhaAtual.add(linha);
            }
            linha = br.readLine();
        }
    }

    private static void insereMatrizAtual(ArrayList<String> listaLinhaMatrizAtual) {

        int controleLinhaAtual = 0;
        int posLinha = 0;

        ArrayList<String> linhaCertaAtual = new ArrayList<>();
        matriz = new String[numeroInstancias][numeroInstancias];

        for (String atual : listaLinhaMatrizAtual) {
            for (int k = atual.split(" ").length - 1; k >= 0; k--) {
                linhaCertaAtual.add(atual.split(" ")[k]);
            }
            controleLinhaAtual = linhaCertaAtual.size() - 1;
            preencherMatriz(linhaCertaAtual, controleLinhaAtual, posLinha);
            posLinha++; // guarda a linha atual da matriz

            linhaCertaAtual.clear();

        }

        completarMatriz();
        montaGrafo();

        if (contaCC(matrizGrafo) > 1) {
            System.out.println("MATRIZ INVÁLIDA");
        } else {
            geraArvoreMinima(matrizGrafo);
        }

//        contaAdjacenciasPorVertice();
    }

    public static void contaAdjacenciasPorVertice() {
        for (Map.Entry entry : matrizGrafo.getGrafo().entrySet()) {
//            for(List<Aresta> a : matrizGrafo.getGrafo().values()){
            System.out.println("PARA O VERTICE  " + entry.getKey().toString() + " LISTA ADJ  " + entry.getValue());
//            }
        }
    }

    static void preencherMatriz(ArrayList<String> linhaCertaAtual, int controleLinhaAtual, int posLinha) {
        int j = numeroInstancias - 1;

        while (controleLinhaAtual >= 0) {
            if (posLinha == j) {
                matriz[posLinha][j] = labelMatriz;
            } else {
                matriz[posLinha][j] = linhaCertaAtual.get(controleLinhaAtual);
                controleLinhaAtual--;
            }
            j--;
        }
    }

    static void completarMatriz() {
        for (int i = 0; i < numeroInstancias; i++) {
            for (int j = 0; j < numeroInstancias; j++) {
                if (i == j) {
                    matriz[i][j] = labelMatriz;
                } else if (i > j) {
                    matriz[i][j] = matriz[j][i];
                }
            }
        }
    }

    static void montaGrafo() {
        matrizGrafo = new Grafo();

        List<Aresta> listaArestasVerticeAtual = new ArrayList<>();
        Vertice v = null;
        Vertice u = null;
        Aresta a = null;

        for (int i = 0; i < matriz.length; i++) {
            v = criaVertice(i);
            
            for (int j = 0; j < matriz.length; j++) {
                u = criaVertice(j);
                u.setNumero(j);
                a = new Aresta();

                if ((!matriz[i][j].equals("20"))) { // adiciona somente adjacencias certas
                    a.setV1(v);
                    a.setV2(u);
                    a.setLabel(matriz[i][j]);
                    listaArestasVerticeAtual.add(a);
                }
            }

            matrizGrafo.adicionaAresta(v, listaArestasVerticeAtual);
            listaArestasVerticeAtual.clear();
        }

    }

    static void printMatriz() {
        for (int l = 0; l < numeroInstancias; l++) {
            for (int c = 0; c < numeroInstancias; c++) {
                System.out.print(" " + matriz[l][c]);
            }
            System.out.println("\n");
        }
    }

    static Vertice criaVertice(int numero) {
        Vertice v = null;

        if (verticesCriados.isEmpty()) {
            for (int i = 0; i < numeroInstancias; i++) {
                v = new Vertice();
                v.setNumero(i);
                verticesCriados.add(v);
//                System.out.println("VERTICES CRIADOS" + verticesCriados.toString());
//                System.out.println("numero + " + numero);
            }
            v = verticesCriados.get(numero);
        } else {
            v = verticesCriados.get(numero);
//            System.out.println("PEGOU VERTICE EXISTENTE" + v.toString());
        }
//        System.out.println("\nCRIA VERTICES : " + verticesCriados.toString());

        return v;
    }

    static int contaCC(Grafo grafo) {
        ArrayList<Vertice> verticesNaoVisitados = grafo.getVerticesGrafo();
        ArrayList<Vertice> verticesVisitados = new ArrayList<>();
        ArrayList<Vertice> adjacentes = new ArrayList<>();
        ArrayList<Vertice> auxAdjacentes = new ArrayList<>();
        int componentes = 0;

        while (!verticesNaoVisitados.isEmpty()) {
            componentes++;

            adjacentes = grafo.getListaVerticesAdjacentes(verticesNaoVisitados.get(0));
            verticesVisitados.add(verticesNaoVisitados.get(0));
//            System.out.println("REMOVENDO VERTICE :" + verticesNaoVisitados.get(0));
            verticesNaoVisitados.remove(0);

//            System.out.println("--------------------------CONTOU COMPONENTE--------------------------");
            while (!adjacentes.isEmpty()) {

                auxAdjacentes = grafo.getListaVerticesAdjacentes(adjacentes.get(0)); //recebo os adjacentes dele

                for (int j = 0; j < auxAdjacentes.size(); j++) {
                    if (!verticesVisitados.contains(auxAdjacentes.get(j)) && !adjacentes.contains(auxAdjacentes.get(j))) {
                        adjacentes.add(auxAdjacentes.get(j));
                    }
                }

                for (int k = 0; k < verticesNaoVisitados.size(); k++) {
                    if (verticesNaoVisitados.get(k).equals(adjacentes.get(0))) {

                        verticesNaoVisitados.remove(k);
                    }
                }

//                System.out.println("REMOVE VERTICE " + adjacentes.get(0));
                verticesVisitados.add(adjacentes.get(0));
                adjacentes.remove(0);

//                System.out.println("NÃO VISITADOS : " + verticesNaoVisitados.toString());
//                System.out.println("VISITADOS : " + verticesVisitados.toString());
//                System.out.println("ADJACENTES : " + adjacentes.toString());
//                System.out.println("-------PROXIMO ADJACENTE ------------------");
            }
        }
        System.out.println("COMPONENTES : " + componentes);
        return componentes;
    }

    static void geraArvoreMinima(Grafo grafo) {
        ArrayList<String> labels = grafo.getLabels();
        ArrayList<Aresta> arestasPorLabel = new ArrayList<>();
        System.out.println("LABELS : " + labels.toString());
        
        Grafo novoGrafo = new Grafo();
        Grafo auxGrafo = new Grafo();

//        while(!labels.isEmpty()){
            arestasPorLabel = grafo.getArestasPorLabel(labels.get(0));
            adicionaArestasLabel(auxGrafo, labels.get(0), arestasPorLabel);
            
//        }
    }

    static Grafo adicionaArestasLabel(Grafo grafo, String label, ArrayList<Aresta> arestasPorLabel) {
        ArrayList<Aresta> arestasLabel = arestasPorLabel;
        Grafo grafo2 = grafo;
        
        System.out.println("Arestas por label : " + arestasLabel.toString());
        for(int i = 0; i < arestasLabel.size(); i++){
            grafo2.adicionaArestaPorVerticePorAresta(arestasLabel.get(0).getV1(), arestasLabel.get(0));
        }
        
        System.out.println("PRINT GRAFO 2");
        grafo2.printGrafo();
        
        
        return grafo2;
    }

    public static void main(String[] args) throws IOException {
        lerArquivo();

    }

}
