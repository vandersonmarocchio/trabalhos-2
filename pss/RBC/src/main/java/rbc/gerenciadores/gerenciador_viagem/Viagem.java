package rbc.gerenciadores.gerenciador_viagem;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by luis on 07/06/17.
 */
@Entity
public class Viagem {

    @Id
    private String veiculo;
    private int periodo;
    private int km;
    private float valorFrete;
    private float comissao;
    private float combustivel;
    private float pedagio;
    private float borracharia;
    private float mecanica;
    private float despesasExtras;
    private float liquido;
    private String local;

    public Viagem(String veiculo, int periodo, int km, float valorFrete, float comissao, float combustivel, float pedagio, float borracharia, float mecanica, float despesasExtras, float liquido, String local) {
        this.veiculo = veiculo;
        this.periodo = periodo;
        this.km = km;
        this.valorFrete = valorFrete;
        this.comissao = comissao;
        this.combustivel = combustivel;
        this.pedagio = pedagio;
        this.borracharia = borracharia;
        this.mecanica = mecanica;
        this.despesasExtras = despesasExtras;
        this.liquido = liquido;
        this.local = local;
    }

    public String getVeiculo(){
        return veiculo;
    }

    public void setVeiculo(String veiculo){
        this.veiculo = veiculo;
    }

    public int getPeriodo(){
        return periodo;
    }

    public void setPeriodo(int periodo){
        this.periodo = periodo;
    }

    public int getKm(){
        return km;
    }

    public void setKm(int km){
        this.km = km;
    }

    public float getValorFrete(){
        return valorFrete;
    }

    public void setValorFrete(float valorFrete){
        this.valorFrete = valorFrete;
    }

    public float getComissao(){
        return comissao;
    }

    public void setComissao(float comissao){
        this.comissao = comissao;
    }

    public float getCombustivel(){
        return combustivel;
    }

    public void setCombustivel(){
        this.combustivel = combustivel;
    }

    public float getPedagio(){
        return pedagio;
    }

    public void setPedagio(float pedagio){
        this.pedagio = pedagio;
    }

    public float getBorracharia(){
        return borracharia;
    }

    public void setBorracharia(float borracharia){
        this.borracharia = borracharia;
    }

    public float getMecanica(){
        return mecanica;
    }

    public void setMecanica(float mecanica){
        this.mecanica = mecanica;
    }

    public float getDespesasExtras(){
        return despesasExtras = despesasExtras;
    }

    public void setDespesasExtras(float despesasExtras){
        this.despesasExtras = despesasExtras;
    }

    public float getLiquido(){
        return liquido;
    }

    public void setLiquido(float liquido){
        this.liquido = liquido;
    }

    public String getLocal(){
        return local;
    }

    public void setLocal(String local){
        this.local = local;
    }
}
