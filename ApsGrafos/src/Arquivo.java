
import com.sun.javafx.scene.control.skin.VirtualFlow;
import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
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
    static double funcao = 0;
    static int contMatrizes = 0;

    static ArrayList<Vertice> verticesCriados = new ArrayList<>();
    static int cont = 0;

    public Arquivo() {

    }

    private static void lerArquivo() throws FileNotFoundException, IOException {
        String linha = "";
        FileReader file = new FileReader(new File("instancias/Group 1/LDGraph50_50.txt"));
        BufferedReader br = new BufferedReader(file);
        ArrayList<String> listaLinhaAtual = new ArrayList<>();

        linha = br.readLine();
        numeroInstancias = Integer.parseInt(String.valueOf(linha.split(" ")[0]));
        labelMatriz = linha.split(" ")[1];

        linha = br.readLine();
        while (linha != null) {
            if (linha.trim().length() == 0) { // acabou a matriz atual
                insereMatrizAtual(listaLinhaAtual);
//                System.out.println("\n****************ACABOU A MATRIZ*********************\n");
                listaLinhaAtual.clear(); //limpa a lista
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
//            System.out.println("MATRIZ INVÁLIDA");
        } else {
            contMatrizes++;
            criaArvoreMinima(matrizGrafo);
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

                if ((!matriz[i][j].equals(labelMatriz))) { // adiciona somente adjacencias certas
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
            }
            v = verticesCriados.get(numero);
        } else {
            v = verticesCriados.get(numero);
        }
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
            verticesNaoVisitados.remove(0);

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
                verticesVisitados.add(adjacentes.get(0));
                adjacentes.remove(0);
            }
        }
        return componentes;
    }

    static void criaArvoreMinima(Grafo g) {
        ArrayList<String> listaLabels = g.getLabels();
        ArrayList<Aresta> arestasPorLabel = new ArrayList<>();
        ArrayList<Aresta> arestasAtual = new ArrayList<>();
        ArrayList<Aresta> arestasMenores = new ArrayList<>();
        ArrayList<Vertice> verticesAtual = new ArrayList<>();
        int numComponentes = 999, menorNumeroComponentes = 999999, posMenorLabel = 0;

        Grafo novoGrafo = new Grafo();
        Grafo auxGrafo = new Grafo();

        for (int j = 0; j < g.getVerticesGrafo().size(); j++) { //adiciona todos os vertices
            auxGrafo.adicionaVertice(g.getVerticesGrafo().get(j));
            novoGrafo.adicionaVertice(g.getVerticesGrafo().get(j));
        }

        while (numComponentes > 1) {
            for (int i = 0; i < listaLabels.size(); i++) {
                auxGrafo.deleteAll();

                for (int j = 0; j < novoGrafo.getVerticesGrafo().size(); j++) { //adiciona todos os vertices
                    auxGrafo.adicionaVertice(g.getVerticesGrafo().get(j));
                }
                for (int k = 0; k < novoGrafo.getVerticesGrafo().size(); k++) { //adiciona as arestas
                    arestasAtual = novoGrafo.getListaArestasAdjacentes(novoGrafo.getVerticesGrafo().get(k)); //recebe adjacentes daquele vertice
                    auxGrafo.adicionaAresta(novoGrafo.getVerticesGrafo().get(k), arestasAtual);
                    arestasAtual.clear();
                }
                arestasPorLabel = g.getArestasPorLabel(listaLabels.get(i));
                insereArestasNoGrafo(arestasPorLabel, auxGrafo);
                numComponentes = contaCC(auxGrafo);
                if (menorNumeroComponentes > numComponentes) {
                    posMenorLabel = i;
                    menorNumeroComponentes = numComponentes;
                }
            }

            arestasMenores = g.getArestasPorLabel(listaLabels.get(posMenorLabel));
            novoGrafo = insereArestasNoGrafo(arestasMenores, novoGrafo);

            listaLabels.remove(posMenorLabel);
            menorNumeroComponentes = 999;
            posMenorLabel = 0;

        }
        System.out.println(novoGrafo.getLabels().toString());
        funcao += novoGrafo.getLabels().size();
    }
    

    static Grafo insereArestasNoGrafo(ArrayList<Aresta> arestasLabel, Grafo g) {
        for (int i = 0; i < arestasLabel.size(); i++) {
            g.adicionaArestaPorVertice(arestasLabel.get(i).getV1(), arestasLabel.get(i));
        }
        return g;
    }

    public static void main(String[] args) throws IOException {
        long time1 = System.currentTimeMillis();
        lerArquivo();
        long time2 = System.currentTimeMillis();
        System.out.println(time2-time1);
        
        System.out.println("funcao " + funcao);
        System.out.println("cont matrizes " + contMatrizes);
        System.out.println("valor da função : " + funcao/contMatrizes);
    }

}
