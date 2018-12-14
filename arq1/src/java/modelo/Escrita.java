package modelo;

import java.util.List;

public class Escrita {

    private String status;
    private String r1;
    private int valor;
    private String[] escrita;

    public Escrita(String[] escrita, List<Integer> registradores) {
        this.escrita = new String[2];
        this.escrita[0] = escrita[1];
        this.escrita[1] = escrita[2];
        this.escrita(escrita, registradores);
    }

    public void escrita(String[] escrita, List<Integer> registradores) {
        this.status = "FINALIZADO";
        this.r1 = escrita[1];
        this.valor = Integer.parseInt(escrita[2]);

        int registrador = Integer.parseInt((this.r1.replace("$r", "")));
        for (int i = 0; i < registradores.size(); i++) {
            if (registrador == i + 1) {
                int valorAtual = registradores.get(i);
                registradores.set(i, valorAtual + this.valor);
            }
        }

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getR1() {
        return r1;
    }

    public void setR1(String r1) {
        this.r1 = r1;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String[] getEscrita() {
        return escrita;
    }

    public void setEscrita(String[] escrita) {
        this.escrita = escrita;
    }

}
