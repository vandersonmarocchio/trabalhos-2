package rbc.infraestrutura;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by leopuglia on 07/06/17.
 */

public class DataController {
    private static final SessionFactory ourSessionFactory;

    static {
        Configuration configuration = new Configuration();
        File file =  new File("verifica_bd");

        try {
            if(!file.exists()) {
                try {
                    new FileWriter(new File("verifica_bd"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ourSessionFactory = configuration.configure().setProperty("hibernate.hbm2ddl.auto", "update").buildSessionFactory();
            }
            else {
                ourSessionFactory = configuration.configure().addResource("/hibernate.cfg.xml").buildSessionFactory();
            }

        } catch (Throwable ex) {
            System.err.println("Failed to create Session Factory");
            throw new ExceptionInInitializerError(ex);
        }
    }




    public static SessionFactory getSession() throws HibernateException {
        return ourSessionFactory;
    }


    public void addDatabase(Object o) throws Exception {

        Session session = getSession().openSession();
        Transaction s = null;
        try {
            s = session.beginTransaction();
            session.save(o);
            s.commit();
        }
        catch (Exception e) {
            if (s!=null) s.rollback();
            throw new RGExistsException();
        } finally {
           session.close();
        }
    }

    public void update(Object o) throws Exception{
        Session session = getSession().openSession();
        Transaction s = session.beginTransaction();
        session.update(o);
        s.commit();
        session.clear();
        session.close();
    }

    public void delete(Object o) throws Exception {
        Session session = getSession().openSession();
        Transaction s = session.beginTransaction();
        session.delete(o);
        s.commit();
        session.close();
    }
}
