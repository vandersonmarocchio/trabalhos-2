/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Diogo Alves de Almeida
 */
public class Dicionario {

    public static final List<String> PALAVRAS = Arrays.asList("the", "and", "are", "for", "type", "with", "each", "then", "have", 
                                                              "that", "can", "this", "from", "where","our", "which", "were");

    public List<String> getPalavras() {
        return PALAVRAS;
    }

}
