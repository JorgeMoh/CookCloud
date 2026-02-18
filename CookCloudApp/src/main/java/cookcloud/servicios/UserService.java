package cookcloud.servicios;

import cookcloud.modelo.Usuario;
import cookcloud.utils.UtilsBD;
import jakarta.persistence.EntityManager;

public class UserService {

    /**
     * Metodo que comprueba si ya existe un usuario con ese nombre de usuario en la BDD
     * @param nombre nombre que queremos comprobar
     * @return devuelve true si ya existe y false si no
     */
    public boolean comprobarNombreUser(String nombre) {

        EntityManager em = UtilsBD.getEntity();

        try {
            Long coincNombre = em.createQuery("SELECT COUNT(s) FROM Usuario s WHERE s.usuario = :nombre", Long.class)
                                 .setParameter("nombre", nombre).getSingleResult();

            return coincNombre > 0;

        }finally {
            em.close();
        }

    }

    /**
     * Metodo que comprueba si ya existe un usuario con ese email de usuario en la BDD
     * @param email email que queremos comprobar
     * @return devuelve true si ya existe y false si no
     */
    public boolean comprobarEmailUser(String email) {

        EntityManager em = UtilsBD.getEntity();

        try {
            // Cargamos el resultado de la consulta
            Long coincEmail = em.createQuery("SELECT COUNT(s) FROM Usuario s WHERE s.email = :email", Long.class)
                                .setParameter("email", email).getSingleResult();

            // devolvemos true si el resultado es mayor que 0 y false si es menos
            return coincEmail > 0;

        }finally {
            em.close(); // cerramos el EntityManager
        }

    }

    /**
     * Metodo que sube a la base de datos un nuevo usuario usando los parámetros que le pasamos
     * @param user Usuario que queremos registrar
     */
    public void registrarUsuario(Usuario user) {

        EntityManager em = UtilsBD.getEntity();

        try {

            // Iniciamos la transacción
            em.getTransaction().begin();

            // Subimos a la BDD el usuario
            em.persist(user);

            // Confirmamos cambios
            em.getTransaction().commit();

        } finally {
            em.close();
        }

    }

    /**
     * Metodo que busca en la BDD un usuario por su nombre de usuario
     * @param usuario nombre del usuario a buscar
     * @return devuelve un objeto de la clase usuario
     */
    public Usuario buscarUsuarioPorNombre(String usuario) {

        EntityManager em = UtilsBD.getEntity();

        try{

            // buscamos y guardamos el usuario
            Usuario s = em.createQuery("FROM Usuario s WHERE s.usuario = :nombre", Usuario.class)
                    .setParameter("nombre", usuario).getSingleResult();

            return s; // devolvemos a ese usuario

        }finally {
            em.close();
        }

    }

}
