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

    /**
     * Metoodo que actualiza en la base de datos el nombre de un usuario
     * @param idUsuario id del usuario al que vamos a cambiar el usuario
     * @param nuevoNombre nuevo nombre de usuario
     */
    public void cambiarNombreDeUsuario(long idUsuario, String nuevoNombre) {

        EntityManager em = UtilsBD.getEntity();

        try {
            // Iniciamos la transacción
            em.getTransaction().begin();

            // Cambiamos el nombre de usuario
            Usuario s = em.find(Usuario.class, idUsuario);
            s.setUsuario(nuevoNombre);

            // terminamos la transaccion y confirmamos cambios
            em.getTransaction().commit();

        }finally {
            em.close();
        }

    }

    /**
     * Metodo que actualiza en la base de datos la contraseña de un usuario
     * @param idUsuario id del usuario al que se le va a cambiar la contraseña
     * @param pass contraseña nueva
     */
    public void cambiarContrasenia(long idUsuario, String pass) {

        EntityManager em = UtilsBD.getEntity();

        try {
            // Iniciamos la transacción
            em.getTransaction().begin();

            // Cambiamos la contraseña de usuario
            Usuario s = em.find(Usuario.class, idUsuario);
            s.setPassw(pass);

            // terminamos la transaccion y confirmamos cambios
            em.getTransaction().commit();

        }finally {
            em.close();
        }

    }

    /**
     * Metodo que elimina un usuario de la base de datos
     * @param idUsuario
     */
    public void eliminarCuenta(long idUsuario) {

        EntityManager em = UtilsBD.getEntity();

        try {

            // Iniciamos la transacción
            em.getTransaction().begin();

            // buscamos al usuario y lo eliminamos
            em.remove(em.find(Usuario.class, idUsuario));

            // terminamos la transaccion y confirmamos cambios
            em.getTransaction().commit();

        }finally {
            em.close();
        }

    }

    /**
     * Metodo que busca y devuelve un usuario buscandolo por su correo
     * @param emailUsuario correo del usuario que buscamos
     * @return usuario que buscábamos
     */
    public Usuario buscarUsuarioPorEmail(String emailUsuario) {

        EntityManager em = UtilsBD.getEntity();

        try{

            // buscamos y guardamos el usuario
            Usuario s = em.createQuery("FROM Usuario s WHERE s.email = :email", Usuario.class)
                    .setParameter("email", emailUsuario).getSingleResult();

            return s; // devolvemos a ese usuario

        }finally {
            em.close();
        }
    }
}
