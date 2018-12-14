package modelo;

public class Decodificacao {

    private boolean jump = false;
    private String status;
    private String comando;
    private String r1;
    private String r2;
    private String r3;
    private int valorImediato;
    private String label;
    private String[] execucao;

    public Decodificacao(String[] decodificacao) {
        this.decodifica(decodificacao);
    }

    public void decodifica(String[] decodificacao) {
        this.comando = decodificacao[0];
        if (this.comando.equals("move")) {
            this.status = "FINALIZADO";
            this.r1 = decodificacao[1];
            this.r2 = decodificacao[2];

            this.execucao = new String[3];
            this.execucao[0] = this.comando;
            this.execucao[1] = this.r1;
            this.execucao[2] = this.r2;

        } else if (this.comando.equals("add") || this.comando.equals("sub")) {
            this.status = "FINALIZADO";
            this.r1 = decodificacao[1];
            this.r2 = decodificacao[2];
            this.r3 = decodificacao[3];

            this.execucao = new String[4];
            this.execucao[0] = this.comando;
            this.execucao[1] = this.r1;
            this.execucao[2] = this.r2;
            this.execucao[3] = this.r3;

        } else if (this.comando.equals("addi") || this.comando.equals("subi")) {
            this.status = "FINALIZADO";
            this.r1 = decodificacao[1];
            this.r2 = decodificacao[2];
            this.valorImediato = Integer.parseInt(decodificacao[3]);

            this.execucao = new String[4];
            this.execucao[0] = this.comando;
            this.execucao[1] = this.r1;
            this.execucao[2] = this.r2;
            this.execucao[3] = String.valueOf(this.valorImediato);

        } else if (this.comando.equals("j")) {
            this.status = "FINALIZADO";
            this.comando = decodificacao[0];
            this.label = decodificacao[1];
            this.jump = true;
            this.execucao = new String[2];
            this.execucao[0] = this.comando;
            this.execucao[1] = this.label;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String[] getExecucao() {
        return execucao;
    }

    public void setExecucao(String[] execucao) {
        this.execucao = execucao;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
