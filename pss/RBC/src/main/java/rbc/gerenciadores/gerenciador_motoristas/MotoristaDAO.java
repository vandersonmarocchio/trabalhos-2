package rbc.gerenciadores.gerenciador_motoristas;


import org.hibernate.Session;
import rbc.infraestrutura.DataController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leopuglia on 07/06/17.
 */
public class MotoristaDAO extends DataController {

    @Override
    public void addDatabase(Object o) throws Exception {
        super.addDatabase(o);

    }

    public void delete(Object o) throws Exception {
        super.delete(o);
    }

    public void update(Object o) throws Exception {
        super.update(o);
    }

    public static List<Motorista> getMotoristas() {
        List<Motorista> motoristas;

        try (Session sessao = DataController.getSession().openSession()) {
            motoristas = fromList(sessao.createQuery("from Motorista ").getResultList());
        }
        return motoristas;
    }

    static private List<Motorista> fromList(List list){
        List<Motorista> motoristas = new ArrayList<>();

        for(Object o : list){
            motoristas.add((Motorista)o);
        }

        return motoristas;
    }

}
