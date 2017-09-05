package rbc.gerenciadores.gerenciador_despesas;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by luis on 08/06/17.
 */
@Entity
public class Despesas {
    @Id
    private float documentacao;
    private float manutencao;
    private float combustivel;

    public float getDocumentacao(){
        return documentacao;
    }

    public void setDocumentacao(float documentacao){
        this.documentacao = documentacao;
    }

    public float getManutencao(){
        return manutencao;
    }

    public void setManutencao(float manutencao){
        this.manutencao = manutencao;
    }

    public float getCombustivel(){
        return combustivel;
    }

    public void setCombustivel(float combustivel){
        this.combustivel = combustivel;
    }
}
