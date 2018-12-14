package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * @author Diogo Alves de Almeida
 * @since 22/06/2018
 */
public class LeituraPDFBox {

    private final List<String> nomesArquivos = Arrays.asList("118", "120", "13", "17", "19", "20", "21", "22",
            "23", "29", "31", "46", "48", "49", "50", "98");

    /**
     * Função que carrega o arquivo em pdf
     *
     */
    public void leitura() {
        System.out.println("Inicio");
        for (String nome : nomesArquivos) {
            PDFTextStripper pdfStripper = null;
            PDDocument pdDoc = null;
            try {
                FileWriter arq = new FileWriter(System.getProperty("user.dir") + "/src/saida/" + nome + ".txt");
                PrintWriter gravarArq = new PrintWriter(arq);

                pdDoc = PDDocument.load(new File(System.getProperty("user.dir") + "/src/arquivos/" + nome + ".pdf"));

                pdfStripper = new PDFTextStripper();

                //Começa a leitura do arquivo a partir da página informada
                pdfStripper.setStartPage(1); // começa na página 1
                pdfStripper.setEndPage(pdfStripper.getEndPage()); // termina no fim do pdf
                String parsedText = pdfStripper.getText(pdDoc);

                Scanner s = new Scanner(parsedText);
                s.useDelimiter("\n");

                HashMap<String, Integer> mapPalavras = new HashMap<>();

                boolean resumo = false;
                boolean referencia = false;
                String linha = "";

                while (s.hasNext()) {
                    linha = s.next();
//                    System.out.println(linha);
                    // impressão das referências
                    if (referencia) {
//                        System.out.println(linha);
                        gravarArq.printf(linha);
                    }
                    if (linha.contains("REFERENCES")) {
                        referencia = true;
                        gravarArq.print("REFERÊNCIA: ");
                    }

                    // impressão do resumo
                    if (linha.contains("Abstract")) {
                        resumo = true;
                        gravarArq.print("RESUMO: ");
                    }
                    if (linha.contains("Index Terms")) {
                        resumo = false;
                        gravarArq.print("\n");
                    }
                    if (resumo) {
//                        System.out.println(linha);
                        gravarArq.print(linha);
                    }

                    // recorrência de palavras
                    String minusculo = linha.toLowerCase();
                    Pattern p = Pattern.compile("([a-záéíóúçãõôê]+)");
                    Matcher m = p.matcher(minusculo);
                    List<String> dicionario = new Dicionario().getPalavras();
                    while (m.find()) {
                        String palavra = m.group(); //pega uma palavra   
                        if (!referencia) {
                            if (!dicionario.contains(palavra)) { //verifica no dicionário de palavras
                                if (palavra.length() > 2) { // pra tirar a maioria das palavras inuteis em ingles
                                    Integer frequencia = mapPalavras.get(palavra); //verifica se essa palavra já está no mapa    

                                    if (frequencia != null) { //se palavra existe, atualiza a frequência
                                        mapPalavras.put(palavra, frequencia + 1);
                                    } else { // se palavra não existe, insere com um novo id e frequência = 1.
                                        mapPalavras.put(palavra, 1);
                                    }
                                }
                            }
                        }

                    }
                }

                gravarArq.print("\nOCORRÊNCIA: ");
                mapPalavras = ordenarPorChave(mapPalavras);
                int aux = 0;
                for (Map.Entry<String, Integer> mapa : mapPalavras.entrySet()) {
                    if (aux == 10) {
                        break;
                    }
                    aux++;
                    System.out.println("[" + aux + "] " + mapa.getKey() + "\t Freq=" + mapa.getValue());
                    gravarArq.printf("[" + aux + "]" + mapa.getKey().toUpperCase() + "[" + mapa.getValue() + "];");
                }

                arq.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fim");

    }

    /**
     * Função que ordena o HashMap
     *
     * @param map hashmap a ser ordenado
     * @return hashmap ordenado
     */
    public HashMap ordenarPorChave(HashMap map) {
        List list = new LinkedList(map.entrySet());

        // Definição do comparador
        Collections.sort(list, (Object objeto1, Object objeto2) -> ((Comparable) ((Map.Entry) (objeto2)).getValue()).compareTo(((Map.Entry) (objeto1)).getValue()));

        // Copiando a lista ordenada para o HashMap
        // Usando o LinkedHashMap para preservar a inserção ordenada
        HashMap sortedHashMap = new LinkedHashMap();

        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

}
