package modelo;

public class Clock {
    
    private int numero;
    private Busca busca;
    private Decodificacao decodificacao;
    private Execucao execucao;
    private Escrita escrita;

    public Clock() {
        this.busca = null;
        this.decodificacao = null;
        this.execucao = null;
        this.escrita = null;
    }

    public Busca getBusca() {
        return busca;
    }

    public void setBusca(Busca busca) {
        this.busca = busca;
    }

    public Decodificacao getDecodificacao() {
        return decodificacao;
    }

    public void setDecodificacao(Decodificacao decodificacao) {
        this.decodificacao = decodificacao;
    }

    public Execucao getExecucao() {
        return execucao;
    }

    public void setExecucao(Execucao execucao) {
        this.execucao = execucao;
    }

    public Escrita getEscrita() {
        return escrita;
    }

    public void setEscrita(Escrita escrita) {
        this.escrita = escrita;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
}
