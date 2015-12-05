package apscaminhosminimos;

import apscaminhosminimos.grafo.Aresta;
import apscaminhosminimos.grafo.Grafo;
import apscaminhosminimos.grafo.Vertice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Maa - PC
 */
public class Arquivo {

    static ArrayList<Vertice> verticesCriados = new ArrayList<>();
    static Grafo grafo;

    public Arquivo() {
  
    }

    public static void lerArquivo() throws FileNotFoundException, IOException {

        grafo = new Grafo();
        Vertice v = null;
        Vertice u = null;
        Aresta a = null;

        String[] linhaAtual = null;
        String linha = "";

        FileReader file = new FileReader(new File("roadNet-PA.txt"));
        BufferedReader br = new BufferedReader(file);
        ArrayList<String> listaLinhaAtual = new ArrayList<>();

        linha = br.readLine();
        linha = br.readLine();
        linha = br.readLine();
        linha = br.readLine(); //pulando o cabeçalho

        while (linha != null) {
            linha = br.readLine();
            linhaAtual = linha.split("\t");
            
//            linhaAtual.add(linha.replaceAll(" ", "-").split("-")[0]);
//            linhaAtual.add(linha.replaceAll(" ", "-").split("-")[1]);
//            linhaAtual.add(linha.split("    ")[1]);
//            linhaAtual.add(linha.split("    ")[2]);
            
            System.out.println("LINHA ATUAL " + Arrays.toString(linhaAtual) + " Tamnho " + linhaAtual.length);

            if (grafo.getVertice(Integer.parseInt(linhaAtual[0])) == null) { //se o vertice não existe
                v = new Vertice();
                v.setNumero(Integer.parseInt(linhaAtual[0]));
                grafo.adicionaVertice(v);

            } else { //se o vertice já existe
                v = grafo.getVertice(Integer.parseInt(linhaAtual[0]));
            }

            if (grafo.getVertice(Integer.parseInt(linhaAtual[1])) == null) {
                u = new Vertice();
                u.setNumero(Integer.parseInt(linhaAtual[1]));
                grafo.adicionaVertice(u);
//                System.out.println(" numero u " + u.getNumero());

            } else {
                u = grafo.getVertice(Integer.parseInt(linhaAtual[1]));
            }

            a = new Aresta();
            a.setV1(v);
            a.setV2(u);
            a.setPeso(Math.abs(u.getNumero() - v.getNumero()));

            grafo.adicionaArestaPorVertice(v, a);

//            return;
        
        }

            grafo.printGrafo();
//        System.out.println(grafo.getVerticesGrafo().toString());
    }

    public static void main(String[] args) throws IOException {
        lerArquivo();
    }
}
