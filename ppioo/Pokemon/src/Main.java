import java.io.FileNotFoundException;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Batalha batalha = new Batalha();
        batalha.carregarTabelas();
        if (batalha.inicializarJogadores()){
            batalha.batalha();
        }
    }
}
