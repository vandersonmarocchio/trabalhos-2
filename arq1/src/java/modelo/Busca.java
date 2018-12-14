package modelo;

import java.util.List;
import javafx.util.Pair;

public class Busca {

    private int pc;
    private String status;
    private String[] comandoBuscado;

    public Busca(List<Pair<Integer, String[]>> listaPc, Integer pc) {
        this.busca(listaPc, pc);
    }

    public void busca(List<Pair<Integer, String[]>> listaPc, Integer pc) {
        this.status = "FINALIZADO";
            for (Pair<Integer, String[]> pair : listaPc) {
            if (pair.getKey() == pc) {
                this.comandoBuscado = pair.getValue();
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getComandoBuscado() {
        return comandoBuscado;
    }

    public void setComandoBuscado(String[] comandoBuscado) {
        this.comandoBuscado = comandoBuscado;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }
}
