package ataques;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by luis on 16/12/17.
 */
public class CarregaAtaques {
    private String[][] tabAtaques;

    public CarregaAtaques() { }

    public String carregar(int id) {
        this.tabAtaques = new String[165][8];
        String arquivoAtaques = "src/ataques.txt";
        BufferedReader conteudo = null;
        String linha;
        String separadorCampo = "\t";
        conteudo = null;

        int i = 0;
        int j = 0;
        try {
            conteudo = new BufferedReader(new FileReader(arquivoAtaques));
            conteudo.readLine(); // ignorar a primeira linha
            while ((linha = conteudo.readLine()) != null) {
                String[] camposLidos = linha.split(separadorCampo); // separa pelo \t e retorna um vetor contendo a sub string
                for (String s : camposLidos) {
                    tabAtaques[i][j++] = s;
                }
                i++;
                j = 0;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado");
        } catch (IOException e) {
            System.out.println("Erro de IO");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Indice fora do limite");
        } finally {
            if (conteudo != null) {
                try {
                    conteudo.close();
                } catch (IOException e) {
                    System.out.println("Erro de IO (close)");
                }
            }
        }
        return tabAtaques[id-1][7];

    }

}
