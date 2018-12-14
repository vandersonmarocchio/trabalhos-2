package modelo;

import java.util.ArrayList;
import java.util.List;

public class Registradores {

    private List<Integer> registradores = new ArrayList<Integer>();

    public Registradores() {
        for (int i = 0; i <= 31; i++) {
            this.registradores.add(0);
        }
    }

    public List<Integer> getRegistradores() {
        return registradores;
    }

    public void setRegistradores(List<Integer> registradores) {
        this.registradores = registradores;
    }

}
