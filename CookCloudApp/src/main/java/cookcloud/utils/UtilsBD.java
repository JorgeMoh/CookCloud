package cookcloud.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UtilsBD {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("cookcloud_bd");

    /**
     * Metodo que devuelve un EntityManagerFactory de la base de datos
     * @return EntityManagerFactory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return factory;
    }

    /**
     * Metodo que devuelve un EntityManager del EntityManagerFactory
     * @return EntityManager del EntityManagerFactory
     */
    public static EntityManager getEntity() {
        return factory.createEntityManager();
    }

    /**
     * Cierra el EntityManagerFactory
     */
    public static void cerrar() {
        factory.close();
    }

}
