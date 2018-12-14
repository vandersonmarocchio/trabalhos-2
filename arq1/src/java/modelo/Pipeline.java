package modelo;

import java.util.List;

public class Pipeline {

    private List<Clock> listaClocks;
    private int clockAtual;
    
    public Pipeline() {
        this.clockAtual = 1;
    }

    public List<Clock> getListaClocks() {
        return listaClocks;
    }

    public void setListaClocks(List<Clock> listaClocks) {
        this.listaClocks = listaClocks;
    }

    public int getClockAtual() {
        return clockAtual;
    }

    public void setClockAtual(int clockAtual) {
        this.clockAtual = clockAtual;
    }
    
    

}
