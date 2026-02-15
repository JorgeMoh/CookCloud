package cookcloud.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UtilsBD {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("tienda");

    public static EntityManager Entity() {
        return factory.createEntityManager();
    }

    public static void cerrar() {
        factory.close();
    }

}
