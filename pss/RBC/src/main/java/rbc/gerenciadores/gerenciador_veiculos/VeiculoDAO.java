package rbc.gerenciadores.gerenciador_veiculos;

import org.hibernate.Session;
import rbc.infraestrutura.DataController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leopuglia on 07ss/06/17.
 */
public class VeiculoDAO extends DataController {

    @Override
    public void addDatabase(Object o) throws Exception {
        super.addDatabase(o);
    }

    public void update(Object o) throws Exception {
        super.update(o);
    }

    public void delete(Object o) throws Exception {
        super.delete(o);
    }

    public static List<Cavalo> getVeiculos() {
        List<Cavalo> cavalos;

        try (Session sessao = DataController.getSession().openSession()) {
            cavalos = fromList(sessao.createQuery("from Cavalo ").getResultList());
        }

        return cavalos;
    }


    static private List<Cavalo> fromList(List list) {
        List<Cavalo> cavalos = new ArrayList<>();

        for (Object o : list) {
            cavalos.add((Cavalo) o);
        }

        return cavalos;
    }
}