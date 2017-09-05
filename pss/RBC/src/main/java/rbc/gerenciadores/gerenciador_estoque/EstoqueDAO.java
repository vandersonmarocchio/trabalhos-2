package rbc.gerenciadores.gerenciador_estoque;

import org.hibernate.Session;
import rbc.infraestrutura.DataController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 08/06/17.
 */
public class EstoqueDAO extends DataController {
    @Override
    public void addDatabase(Object o) throws Exception {
        super.addDatabase(o);
    }

    public void delete(Object o) throws Exception {
        super.delete(o);
    }

    public void update(Object o) throws Exception{
        super.update(o);
    }

    public static List<Estoque> getEstoque() {
        List<Estoque> estoque;

        try (Session sessao = DataController.getSession().openSession()) {
            estoque = fromList(sessao.createQuery("from Estoque ").getResultList());
        }
        return estoque;
    }

    static private List<Estoque> fromList(List list){
        List<Estoque> estoque = new ArrayList<>();

        for(Object o : list){
            estoque.add((Estoque) o);
        }

        return estoque;
    }
}
