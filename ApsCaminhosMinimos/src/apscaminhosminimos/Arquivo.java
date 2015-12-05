package apscaminhosminimos;

import apscaminhosminimos.grafo.Vertice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Maa - PC
 */
public class Arquivo {

    static ArrayList<Vertice> verticesCriados = new ArrayList<>();

    public static void lerArquivo() throws FileNotFoundException, IOException {

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
        }
    }

//    static Vertice criaVertice(int numero) {
//        Vertice v = null;
//
////        if (verticesCriados.isEmpty()) {
////            v = new Vertice();
////            v.setNumero(numero);
////            verticesCriados.add(v);
////            v = verticesCriados.get(numero);
////        } else {
////            v = verticesCriados.get(numero);
////        }
////        return v;
//    }

}
