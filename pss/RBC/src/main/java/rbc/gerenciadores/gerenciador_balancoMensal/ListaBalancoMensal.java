package rbc.gerenciadores.gerenciador_balancoMensal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by diogo on 20/07/17.hibernate.cfg.xml/Hibernate
 */
@Entity
public class ListaBalancoMensal {
    @Id
    private Timestamp data_inicial;
    private Timestamp data_final;
    private String RGFuncionario;
    @OneToMany
    private List<BalancoMensal> balancoMensal =  new ArrayList<BalancoMensal>();

    public List<BalancoMensal> getBalancoMensal() {
        return balancoMensal;
    }

    public void setBalancoMensal(List<BalancoMensal> balancoMensal) {
        this.balancoMensal = balancoMensal;
    }

    public Timestamp getData_inicial() {
        return data_inicial;
    }

    public void setData_inicial(Timestamp data_inicial) {
        this.data_inicial = data_inicial;
    }

    public Timestamp getData_final() {
        return data_final;
    }

    public void setData_final(Timestamp data_final) {
        this.data_final = data_final;
    }

    public String getRGFuncionario() {
        return RGFuncionario;
    }

    public void setRGFuncionario(String RGFuncionario) {
        this.RGFuncionario = RGFuncionario;
    }
}
