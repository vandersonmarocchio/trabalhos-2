package modelo;

import java.util.List;
import javafx.util.Pair;

public class Execucao {

    private String status;
    private boolean primeiraVez = true;
    private String comando;
    private String r1;
    private String r2;
    private String r3;
    private int valorImediato;
    private String label;
    private String[] escrever;

    public Execucao(String[] decodificacao, List<Integer> registradores) {
        this.executa(decodificacao, registradores);
    }

    public void executa(String[] decodificacao, List<Integer> registradores) {
        this.escrever = null;
        this.comando = decodificacao[0];

        if (this.comando.equals("move")) {
            this.status = "FINALIZADO";
            this.r1 = decodificacao[1];
            this.r2 = decodificacao[2];

            this.escrever = new String[3];
            this.escrever[0] = this.comando;
            this.escrever[1] = this.r1;

            int registrador = Integer.parseInt(this.r2.replace("$r", ""));
            int valor = 0;

            for (int i = 0; i < registradores.size(); i++) {
                if (registrador == i + 1) {
                    valor = registradores.get(i);
                }
            }

            this.escrever[2] = String.valueOf(valor);

        } else if (this.comando.equals("add")) {

            if (primeiraVez) {
                this.status = "EXECUTANDO";
                this.r1 = decodificacao[1];
                this.r2 = decodificacao[2];
                this.r3 = decodificacao[3];

                this.escrever = new String[3];
                this.escrever[0] = this.comando;
                this.escrever[1] = this.r1;

                int registrador2 = Integer.parseInt(this.r2.replace("$r", ""));
                int registrador3 = Integer.parseInt(this.r3.replace("$r", ""));
                int valor1 = 0;
                int valor2 = 0;

                for (int i = 0; i < registradores.size(); i++) {
                    if (registrador2 == i + 1) {
                        valor1 = registradores.get(i);
                    }
                    if (registrador3 == i + 1) {
                        valor2 = registradores.get(i);
                    }
                }
                this.escrever[2] = String.valueOf(valor1 + valor2);
            } else {
                this.status = "FINALIZADO";
            }

        } else if (this.comando.equals("addi")) {
            this.status = "FINALIZADO";
            this.r1 = decodificacao[1];
            this.r2 = decodificacao[2];
            this.valorImediato = Integer.parseInt(decodificacao[3]);

            this.escrever = new String[3];

            this.escrever[0] = this.comando;
            this.escrever[1] = this.r1;

            int registrador = Integer.parseInt(this.r2.replace("$r", ""));
            int valor = 0;

            for (int i = 0; i < registradores.size(); i++) {
                if (registrador == i + 1) {
                    valor = registradores.get(i);
                }
            }
            if (this.r1.equals(this.r2)) {
                this.escrever[2] = String.valueOf(this.valorImediato);

            } else {
                this.escrever[2] = String.valueOf(valor + this.valorImediato);

            }
        } else if (this.comando.equals("sub")) {
            if (!primeiraVez) {
                this.status = "EXECUTANDO";
                this.primeiraVez = true;
            } else {
                this.status = "FINALIZADO";
            }
            this.r1 = decodificacao[1];
            this.r2 = decodificacao[2];
            this.r3 = decodificacao[3];

            this.escrever = new String[3];
            this.escrever[0] = this.comando;
            this.escrever[1] = this.r1;

            int registrador2 = Integer.parseInt(this.r2.replace("$r", ""));
            int registrador3 = Integer.parseInt(this.r3.replace("$r", ""));
            int valor1 = 0;
            int valor2 = 0;

            for (int i = 0; i < registradores.size(); i++) {
                if (registrador2 == i + 1) {
                    valor1 = registradores.get(i);
                }
                if (registrador3 == i + 1) {
                    valor2 = registradores.get(i);
                }
            }
            this.escrever[2] = String.valueOf(valor1 - valor2);

        } else if (this.comando.equals("subi")) {
            this.status = "FINALIZADO";
            this.r1 = decodificacao[1];
            this.r2 = decodificacao[2];
            this.valorImediato = Integer.parseInt(decodificacao[3]);

            this.escrever = new String[3];
            this.escrever[0] = this.comando;
            this.escrever[1] = this.r1;

            int registrador = Integer.parseInt(this.r2.replace("$r", ""));
            int valor = 0;

            for (int i = 0; i < registradores.size(); i++) {
                if (registrador == i + 1) {
                    valor = registradores.get(i);
                }
            }
            if (this.r1.equals(this.r2)) {
                this.escrever[2] = String.valueOf(this.valorImediato);

            } else {
                this.escrever[2] = String.valueOf(valor - this.valorImediato);

            }

        } else if (this.comando.equals("j")) {
            this.status = "FINALIZADO";
            this.comando = decodificacao[0];
            this.label = decodificacao[1];
            this.escrever = new String[2];
            this.escrever[0] = this.comando;
            this.escrever[1] = this.label;
        }
    }

    public int setaPc(List<Pair<Integer, String>> listaLabel, String label) {
        int aux = 0;
        for (Pair<Integer, String> pair : listaLabel) {
            String jumpLabel = "";
            jumpLabel = pair.getValue();
            jumpLabel = jumpLabel.replaceAll(":", "");
            if (jumpLabel.equals(label)) {
                aux = pair.getKey();
            }
        }
        return aux;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isPrimeiraVez() {
        return primeiraVez;
    }

    public void setPrimeiraVez(boolean primeiraVez) {
        this.primeiraVez = primeiraVez;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getR1() {
        return r1;
    }

    public void setR1(String r1) {
        this.r1 = r1;
    }

    public String getR2() {
        return r2;
    }

    public void setR2(String r2) {
        this.r2 = r2;
    }

    public String getR3() {
        return r3;
    }

    public void setR3(String r3) {
        this.r3 = r3;
    }

    public int getValorImediato() {
        return valorImediato;
    }

    public void setValorImediato(int valorImediato) {
        this.valorImediato = valorImediato;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String[] getEscrever() {
        return escrever;
    }

    public void setEscrever(String[] escrever) {
        this.escrever = escrever;
    }

}
