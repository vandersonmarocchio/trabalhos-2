package modelo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author diogo
 */
public class Cidade {

    int x;
    int y;

    public Cidade() {
        this.x = (int) (Math.random() * 200);
        this.y = (int) (Math.random() * 200);
    }

    public Cidade(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distanciaAte(Cidade cidade) {
        int distanciaX = Math.abs(getX() - cidade.getX());
        int distanciaY = Math.abs(getY() - cidade.getY());

        double distancia = Math.sqrt((distanciaX * distanciaX) + (distanciaY * distanciaY));

        return distancia;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public String toString(){
        return "X:" + getX()+", Y: "+getY();
    }

}
