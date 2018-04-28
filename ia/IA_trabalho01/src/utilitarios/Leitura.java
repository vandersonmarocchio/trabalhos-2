/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Diogo Alves de Almeida
 */
public class Leitura {
    public static void main (String[]args) throws FileNotFoundException, IOException{
		BufferedReader br = new BufferedReader(new FileReader("src/dados/americanas/2014.txt")); 
		while(br.ready()){ 
   		String linha = br.readLine(); 
   		System.out.println(linha); 
		} 
		br.close(); 
	}
}
