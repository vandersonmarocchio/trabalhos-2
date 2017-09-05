package rbc.gerenciadores.gerenciador_encargo;

import org.hibernate.Session;
import rbc.infraestrutura.DataController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 08/06/17.
 */

public class EncargoDAO extends DataController{

    @Override
    public void addDatabase(Object o) throws Exception {
        super.addDatabase(o);
    }

    public void delete(Object o) throws Exception {
        super.delete(o);
    }

    public static List<Encargo> getEncargo() {
        List<Encargo> encargos;

        try (Session sessao = DataController.getSession().openSession()) {
            encargos = fromList(sessao.createQuery("from Encargo ").getResultList());
        }
        return encargos;
    }

    static private List<Encargo> fromList(List list){
        List<Encargo> encargos = new ArrayList<>();

        for(Object o : list){
            encargos.add((Encargo)o);
        }

        return encargos;
    }

}
