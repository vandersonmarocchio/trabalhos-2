import ataques.*;
import jogador.Humano;
import jogador.Jogador;
import jogador.Maquina;
import pokemons.Especie;
import pokemons.Pokemon;
import pokemons.Status;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Batalha {
    private Jogador jogador1;
    private Jogador jogador2;
    private String[][] tabEspecies;
    private String[][] tabAtaques;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public void carregarTabelas() {
        this.tabEspecies = new String[151][9];
        this.tabAtaques = new String[165][8];
        String arquivoEspecies = "src/especies.txt";
        String arquivoAtaques = "src/ataques.txt";
        BufferedReader conteudo = null;
        String linha;
        String separadorCampo = "\t";
        int i = 0;
        int j = 0;
        try {
            conteudo = new BufferedReader(new FileReader(arquivoEspecies));
            conteudo.readLine(); // ignorar a primeira linha
            while ((linha = conteudo.readLine()) != null) {
                String[] camposLidos = linha.split(separadorCampo); // separa pelo \t e retorna um vetor contendo a sub string
                for (String s : camposLidos) {
                    tabEspecies[i][j++] = s;
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

//        for (int l = 0; l < tabEspecies.length; l++)  {
//            for (int c = 0; c < tabEspecies[0].length; c++)     {
//                System.out.print(tabEspecies[l][c] + " "); //imprime caracter a caracter
//            }
//            System.out.println(" "); //muda de linha
//        }

        conteudo = null;
        i = 0;
        j = 0;
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
//        for (int l = 0; l < tabAtaques.length; l++)  {
//            for (int c = 0; c < tabAtaques[0].length; c++)     {
//                System.out.print(tabAtaques[l][c] + "\t"); //imprime caracter a caracter
//            }
//            System.out.println(" "); //muda de linha
//        }
    }

    public boolean inicializarJogadores() {
        int i = 0;
        try{ // ve a quantidade de elementos do time 1
            Scanner scannerTime1 = new Scanner(new File("src/jogador1.txt"));
            while(scannerTime1.hasNextInt()){
                scannerTime1.nextInt();
                i++;
            }
            scannerTime1.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado");
            return false;
        }
        String[] time1 = new String[i];
        i = 0;
        try{ // apos criado o vetor do time 1, coloca os elementos nesse vetor
            Scanner scannerTime1 = new Scanner(new File("src/jogador1.txt"));
            while(scannerTime1.hasNextInt()){
                time1[i++] = String.valueOf(scannerTime1.nextInt());
            }
            scannerTime1.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado");
            return false;
        }

        i = 0;
        try{ // ve a quantidade de elementos do time 2
            Scanner scannerTime2 = new Scanner(new File("src/jogador2.txt"));
            while(scannerTime2.hasNextInt()){
                scannerTime2.nextInt();
                i++;
            }
            scannerTime2.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado");
            return false;
        }
        String[] time2 = new String[i];
        i = 0;
        try{ // apos criado o vetor do time 1, coloca os elementos nesse vetor
            Scanner scannerTime2 = new Scanner(new File("src/jogador2.txt"));
            while(scannerTime2.hasNextInt()){
                time2[i++] = String.valueOf(scannerTime2.nextInt());
            }
            scannerTime2.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado");
            return false;
        }

        int indiceTime1 = 0;
        int indiceTime2 = 0;

        if (Integer.parseInt(time1[indiceTime1]) == 0) this.jogador1 = new Maquina();
        else if (Integer.parseInt(time1[indiceTime1]) == 1) this.jogador1 = new Humano();
        else {
            System.out.println("Erro ao criar jogador 1.");
            System.out.println("Jogador permitido: 0 (maquina) ou 1 (humano).");
            return false;
        }

        if (Integer.parseInt(time2[indiceTime2])== 0) this.jogador2 = new Maquina();
        else if (Integer.parseInt(time2[indiceTime2]) == 1) this.jogador2 = new Humano();
        else {
            System.out.println("Erro ao criar jogador 2.");
            System.out.println("Jogador permitido 0 (maquina) ou 1 (humano).");
            return false;
        }

        indiceTime1++;
        indiceTime2++;

        int tamTime1 = Integer.parseInt(time1[indiceTime1++]);
        int tamTime2 = Integer.parseInt(time2[indiceTime2++]);

        if((tamTime1 > 0) && (tamTime1<7) ){
            if( time1.length != (6 * Integer.parseInt(time1[1])) + 2){
                System.out.println("Erro ao criar time 1!");
                System.out.println("Problema com a definiçao dos Pokemons.");
                System.out.println("Formato: (POKEMON / LEVEL / ATK1 / ATK2 / ATK3 / ATK4)");
                return false;
            }
            List<Pokemon> time = new ArrayList<>();
            for(i=0; i<tamTime1; i++){
                int idEsp = Integer.parseInt(time1[indiceTime1++]);
                if ((idEsp < 1) || (idEsp > 151)){
                    System.out.println("Erro ao criar time 1!");
                    System.out.println("Nao existe pokemon com a id " + idEsp + ".");
                    System.out.println("Especie permitida: 1 ate 151.");
                    return false;
                }
                Especie especie = new Especie(tabEspecies[idEsp-1]);
                int level = Integer.parseInt(time1[indiceTime1++]);
                if((level < 1) || (level > 100)){
                    System.out.println("Erro ao criar time 1!");
                    System.out.println("Nao existe level " + level + ".");
                    System.out.println("Level permitido: 1 ate 100.");
                    return false;
                }
                Pokemon pokemon = new Pokemon(level, especie);
                List<Ataque> ataquesPokemon = new ArrayList<>();
                for(int j=0; j < 4; j++ ){
                    int idAtk = Integer.parseInt(time1[indiceTime1++]);
                    if((idAtk < 0) || (idAtk > 165)){
                        System.out.println("Erro ao criar time 1!");
                        System.out.println("Nao existe ataque com a id " + idAtk + ".");
                        System.out.println("Ataque permitido: 0 (nenhum ataque) ate 165.");
                        return false;
                    }
                    if (idAtk != 0 ){
                        ataquesPokemon.add(criarAtaque(idAtk,tabAtaques));
                    }
                }
                pokemon.setAtaques(ataquesPokemon);
                time.add(pokemon);
            }
            jogador1.setPokemons(time);
        } else {
            System.out.println("Erro ao criar time 1!");
            System.out.println("Tamanho do time invalido.");
            System.out.println("Tamanho permitido: 1 ate 6 pokemons.");
            return false;
        }

        if((tamTime2 > 0) && (tamTime2<7) ){
            if( time2.length != (6 * Integer.parseInt(time2[1])) + 2){
                System.out.println("Erro ao criar time 2!");
                System.out.println("Problema com a definiçao dos Pokemons.");
                System.out.println("Formato: (POKEMON / LEVEL / ATK1 / ATK2 / ATK3 / ATK4)");
                return false;
            }
            List<Pokemon> time = new ArrayList<>();
            for(i=0; i<tamTime2; i++){
                int idEsp = Integer.parseInt(time2[indiceTime2++]);
                if ((idEsp < 1) || (idEsp > 151)){

                    System.out.println("Erro ao criar time 2!");
                    System.out.println("Nao existe pokemon com a id " + idEsp + ".");
                    System.out.println("Especie permitida: 1 ate 151.");
                    return false;
                }
                Especie especie = new Especie(tabEspecies[idEsp-1]);
                int level = Integer.parseInt(time2[indiceTime2++]);
                if((level < 1) || (level > 100)){
                    System.out.println("Erro ao criar time 2!");
                    System.out.println("Nao existe level " + level + ".");
                    System.out.println("Level permitido: 1 ate 100.");
                    return false;
                }
                Pokemon pokemon = new Pokemon(level, especie);
                List<Ataque> ataquesPokemon = new ArrayList<>();
                for(int j=0; j < 4; j++ ){
                    int idAtk = Integer.parseInt(time2[indiceTime2++]);
                    if((idAtk < 0) || (idAtk > 165)){
                        System.out.println("Erro ao criar time 2!");
                        System.out.println("Nao existe ataque com a id " + idAtk + ".");
                        System.out.println("Ataques permitidos: 0 (nenhum ataque) ate 165.");
                        return false;
                    }
                    if (idAtk !=0){
                        ataquesPokemon.add(criarAtaque(idAtk,tabAtaques));
                    }
                }
                pokemon.setAtaques(ataquesPokemon);
                time.add(pokemon);
            }
            jogador2.setPokemons(time);
        } else {
            System.out.println("Erro ao criar time 2!");
            System.out.println("Tamanho do time invalido.");
            System.out.println("Tamanho permitido: 1 ate 6 pokemons.");
            return false;
        }
        return true;
    }

    private Ataque criarAtaque(int id, String[][] ataques){
        Ataque ataque = null;
        if (id != 0){
            switch (ataques[id-1][6]){
                case "comum" :
                    ataque = new Ataque(tabAtaques[id-1]);
                    break;
                case "hp" :
                    ataque = new AtaqueHP(tabAtaques[id-1]);
                    break;
                case "multihit" :
                    ataque = new AtaqueMultihit(tabAtaques[id-1]);
                    break;
                case "modifier" :
                    ataque = new AtaqueModifier(tabAtaques[id-1]);
                    break;
                case "fixo" :
                    ataque = new AtaqueFixo(tabAtaques[id-1]);
                    break;
                case "charge" :
                    ataque = new AtaqueCharge(tabAtaques[id-1]);
                    break;
                case "status" :
                    ataque = new AtaqueStatus(tabAtaques[id-1]);
                    break;
            }
        }
        return ataque;
    }

    public void batalha(){
        int jogadaJogador1;
        int jogadaJogador2;
        int turno = 1;
        while(jogador1.verificaTime() && jogador2.verificaTime()){ //atan
            System.out.println(ANSI_GREEN + "#####################################################################################");
            System.out.println("TURNO " + turno + ANSI_RESET);
            System.out.println();
            System.out.println("Jogador 1");
            jogador1.imprimeListaPokemons();
            jogador1.getPokemons().get(0).imprimeDadosPokemon();
            jogadaJogador1 = jogador1.escolherComando();
            System.out.println();

            System.out.println("Jogador 2");
            jogador2.imprimeListaPokemons();
            jogador2.getPokemons().get(0).imprimeDadosPokemon();
            jogadaJogador2 = jogador2.escolherComando();
            System.out.println();

            if (jogadaJogador1 == 3 && jogadaJogador2 == 3){ //os dois jogadores desistiram
                System.out.println("Empate!!");
                System.out.println("Obrigado por jogar o Pokemon Simulator!! =)");
                System.exit(0);
            }
            else if (jogadaJogador1 == 3){ // jogador 1 desistiu
                System.out.println("Jogador 2 venceu!!");
                System.out.println("Obrigado por jogar o Pokemon Simulator!! =)");
                System.exit(0);
            }
            else if (jogadaJogador2 == 3){ // jogador 2 desistiu
                System.out.println("Jogador 1 venceu!!");
                System.out.println("Obrigado por jogar o Pokemon Simulator!! =)");
                System.exit(0);
            }
            else if (jogadaJogador1 == 2 && jogadaJogador2 == 1 ){ //O jogador 1 vai trocar e o 2 atacar
                System.out.println("Jogador 1 trocou");
                System.out.println();
                System.out.println("Jogador 2: " + jogador2.getPokemons().get(0).getEspecie().getNome());
                jogador2.escolherAtaque(jogador2.getPokemons().get(0), jogador1.getPokemons().get(0));
                System.out.println();
            }
            else if (jogadaJogador1 == 1 && jogadaJogador2 == 2){ //O jogador 2 vai trocar e o 1 atacar
                //jogador1.escolherAtaque();
                System.out.println("Jogador2 trocou");
                System.out.println();
                System.out.println("Jogador 1: " + jogador1.getPokemons().get(0).getEspecie().getNome());
                jogador1.escolherAtaque(jogador1.getPokemons().get(0), jogador2.getPokemons().get(0));
                System.out.println();
            }
            else if (jogadaJogador1 == 1 && jogadaJogador2 == 1){  //Quando os 2 jogadores querem atacar
                System.out.println("Jogador 1 --> Spd: " + jogador1.getPokemons().get(0).getSpd());
                System.out.println("jogador 2 --> Spd: " + jogador2.getPokemons().get(0).getSpd());
                System.out.println();

                if (jogador1.getPokemons().get(0).getSpd() > jogador2.getPokemons().get(0).getSpd()){ //caso a spd do pokemon titular do jogador 1 for maior
                    System.out.println("Spd1 > Spd2");
                    System.out.println();
                    System.out.println("Jogador 1: " + jogador1.getPokemons().get(0).getEspecie().getNome());
                    jogador1.escolherAtaque(jogador1.getPokemons().get(0), jogador2.getPokemons().get(0));
                    System.out.println();
                    System.out.println("Jogador 2: " + jogador2.getPokemons().get(0).getEspecie().getNome());
                    jogador2.escolherAtaque(jogador2.getPokemons().get(0), jogador1.getPokemons().get(0));
                    System.out.println();
                }
                else if (jogador2.getPokemons().get(0).getSpd() > jogador1.getPokemons().get(0).getSpd()){ //caso a spd do pokemon titular do jogador 2 for maior
                    System.out.println("Spd2 > Spd1");
                    System.out.println();
                    System.out.println("Jogador 2: " + jogador2.getPokemons().get(0).getEspecie().getNome());
                    jogador2.escolherAtaque(jogador2.getPokemons().get(0), jogador1.getPokemons().get(0));
                    System.out.println();
                    System.out.println("Jogador 1: " + jogador1.getPokemons().get(0).getEspecie().getNome());
                    jogador1.escolherAtaque(jogador1.getPokemons().get(0), jogador2.getPokemons().get(0));
                    System.out.println();
                }
                else { //se os dois tiverem a mesma spd ele vai verificar pelo hp
                    System.out.println("Jogador 1 --> Hp: " + jogador1.getPokemons().get(0).getHpAtual());
                    System.out.println("jogador 2 --> Hp: " + jogador2.getPokemons().get(0).getHpAtual());
                    System.out.println();
                    if(jogador2.getPokemons().get(0).getHpAtual() < jogador1.getPokemons().get(0).getHpAtual()){ //caso a o hp do pokemon titular do jogador 2 for maior
                        System.out.println("hp2 < hp1");
                        System.out.println();
                        System.out.println("Jogador 2: " + jogador2.getPokemons().get(0).getEspecie().getNome());
                        jogador2.escolherAtaque(jogador2.getPokemons().get(0), jogador1.getPokemons().get(0));
                        System.out.println();
                        System.out.println("Jogador 1: " + jogador1.getPokemons().get(0).getEspecie().getNome());
                        jogador1.escolherAtaque(jogador1.getPokemons().get(0), jogador2.getPokemons().get(0));
                        System.out.println();
                    }
                    else if(jogador1.getPokemons().get(0).getHpAtual() < jogador2.getPokemons().get(0).getHpAtual()) { //caso a o hp do pokemon titular do jogador 1 for maior
                        System.out.println("hp1 < hp2");
                        System.out.println();
                        System.out.println("Jogador 1: " + jogador1.getPokemons().get(0).getEspecie().getNome());
                        jogador1.escolherAtaque(jogador1.getPokemons().get(0), jogador2.getPokemons().get(0));
                        System.out.println();
                        System.out.println("Jogador 2: " + jogador2.getPokemons().get(0).getEspecie().getNome());
                        jogador1.escolherAtaque(jogador2.getPokemons().get(0), jogador1.getPokemons().get(0));
                        System.out.println();
                    }
                    else { //se houver empate de tudo o jogador 1 começa jogando
                        System.out.println("Empatou em todos os requisitos implementados: Jogador 1 começa");
                        System.out.println();
                        System.out.println("Jogador 1: " + jogador1.getPokemons().get(0).getEspecie().getNome());
                        jogador1.escolherAtaque(jogador1.getPokemons().get(0), jogador2.getPokemons().get(0));
                        System.out.println();
                        System.out.println("Jogador 2: " + jogador2.getPokemons().get(0).getEspecie().getNome());
                        jogador2.escolherAtaque(jogador2.getPokemons().get(0), jogador1.getPokemons().get(0));
                        System.out.println();
                    }
                }
            }
            //se os pokemons titulares do jogador 1 e 2 estivem com status burn ou poison eles vao retirar 6.25% com base no hpbase
            if (jogador1.getPokemons().get(0).getStatus().equals("Burn") || jogador1.getPokemons().get(0).getStatus().equals("Poison")){
                jogador1.getPokemons().get(0).setHpAtual(jogador1.getPokemons().get(0).getHpAtual() - (jogador1.getPokemons().get(0).getHpMax()*0.0625));
            }
            if (jogador2.getPokemons().get(0).getStatus().equals("Burn") || jogador1.getPokemons().get(0).getStatus().equals("Poison")){
                jogador2.getPokemons().get(0).setHpAtual(jogador2.getPokemons().get(0).getHpAtual() - (jogador2.getPokemons().get(0).getHpMax()*0.0625));
            }

            // 10% de chance do status Frozen virar Ok
            if(jogador1.getPokemons().get(0).getStatus().equals("Frozen")){
                Random r = new Random();
                int num = r.nextInt(100) + 1;
                if(num <= 10){
                    System.out.println("Jogador 1: " + jogador1.getPokemons().get(0).getEspecie().getNome() + " saiu do status Frozen");
                    jogador1.getPokemons().get(0).setStatus(Status.Ok);
                }
            }
            // 10% de chance do  status Frozen virar Ok
            if(jogador2.getPokemons().get(0).getStatus().equals("Frozen")){
                Random r = new Random();
                int num = r.nextInt(100) + 1;
                if(num <= 10){
                    System.out.println( "Jogador 2: " + jogador2.getPokemons().get(0).getEspecie().getNome() + " saiu do status Frozen");
                    jogador2.getPokemons().get(0).setStatus(Status.Ok);
                }
            }
            // 20% de chance do status Frozen virar Ok
            if(jogador1.getPokemons().get(0).getStatus().equals("Sleep")){
                Random r = new Random();
                int num = r.nextInt(100) + 1;
                if(num <= 20){
                    System.out.println("Jogador 1: " + jogador1.getPokemons().get(0).getEspecie().getNome() + " saiu do status Sleep");
                    jogador1.getPokemons().get(0).setStatus(Status.Ok);
                }
            }
            // 20% de chance do status Sleep virar Ok
            if(jogador2.getPokemons().get(0).getStatus().equals("Sleep")){
                Random r = new Random();
                int num = r.nextInt(100) + 1;
                if(num <= 20){
                    System.out.println("Jogador 2: " + jogador2.getPokemons().get(0).getEspecie().getNome() + " saiu do status Sleep");
                    jogador2.getPokemons().get(0).setStatus(Status.Ok);
                }
            }
            // 20% do chance de setar confusion false
            if(jogador1.getPokemons().get(0).isConfusion()){
                Random r = new Random();
                int num = r.nextInt(100) + 1;
                if(num <= 20){
                    System.out.println("Jogador 1: " + jogador1.getPokemons().get(0).getEspecie().getNome() + " saiu do status Confusion");
                    jogador1.getPokemons().get(0).setConfusion(false);
                }
            }
            // 20% do chance de setar confusion false
            if(jogador2.getPokemons().get(0).isConfusion()){
                Random r = new Random();
                int num = r.nextInt(100) + 1;
                if(num <= 20){
                    System.out.println("Jogador 2: " + jogador2.getPokemons().get(0).getEspecie().getNome() + " saiu do status Confusion");
                    jogador2.getPokemons().get(0).setConfusion(false);
                }
            }

            // seta flinch pra false
            if(jogador1.getPokemons().get(0).isFlinch()){
                System.out.println("Jogador 1: " + jogador1.getPokemons().get(0).getEspecie().getNome() + " saiu do status Flinch");
                jogador1.getPokemons().get(0).setFlinch(false);
            }

            // seta flinch pra false
            if(jogador2.getPokemons().get(0).isFlinch()){
                System.out.println("Jogador 2: " + jogador2.getPokemons().get(0).getEspecie().getNome() + " saiu do status Flinch");
                jogador2.getPokemons().get(0).setFlinch(false);
            }

            //se o o pokemon titular estiver com o estado Fainted ele coloca ele para a ultima posiçao e o titular vira o segundo
            System.out.println();
            if (jogador1.getPokemons().get(0).getStatus().equals("Fainted")) jogador1.trocaFainted();
            if (jogador2.getPokemons().get(0).getStatus().equals("Fainted")) jogador2.trocaFainted();

            System.out.println(ANSI_GREEN +"FIM DO TURNO " + turno++ );
            System.out.println("#####################################################################################" + ANSI_RESET);
            System.out.println();

            //Verifica para printar qual o time ganhador
            if (!jogador1.verificaTime()) {
                System.out.println(ANSI_RED + "Pokemons do jogador 1:");
                jogador1.imprimeListaPokemons();
                System.out.println(ANSI_RESET);
                System.out.println(ANSI_CYAN + "Pokemons do jogador 2:");
                jogador2.imprimeListaPokemons();
                System.out.println(ANSI_RESET);
                System.out.println("Jogador 2 venceu!!");
                System.out.println("Obrigado por jogar o Pokemon Simulator!! =)");
            }
            else if (!jogador2.verificaTime()){
                System.out.println(ANSI_CYAN + "Pokemons do jogador 1:");
                jogador1.imprimeListaPokemons();
                System.out.println(ANSI_RESET);
                System.out.println(ANSI_RED + "Pokemons do jogador 2:");
                jogador2.imprimeListaPokemons();
                System.out.println(ANSI_RESET);
                System.out.println("Jogador 1 venceu!!");
                System.out.println("Obrigado por jogar o Pokemon Simulator!! =)");

            }
        }
    }
}