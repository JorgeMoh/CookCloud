package cookcloud.servicios;

import cookcloud.modelo.Ingrediente;
import cookcloud.modelo.Receta;
import cookcloud.modelo.Usuario;
import cookcloud.utils.UtilsBD;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RecipeService {

    /**
     * Metodo que sube a la base de datos la receta que le pasamos
     * @param nuevaReceta
     */
    public void subirReceta(Receta nuevaReceta) {

        EntityManager em = UtilsBD.getEntity();

        try {

            // iniciamos la transacción
            em.getTransaction().begin();

            // subimos la receta
            em.persist(nuevaReceta);

            // confirmamos los cambios
            em.getTransaction().commit();

        } finally {
            em.close(); // cerramos la transacción
        }

    }

    /**
     * Metodo que crea una lista con las recetas creadas por el usuario al que corresponde la id que le pasamos
     * @param idUsuario id del usuario creador
     * @return lista de recetas
     */
    public List<Receta> cargarRecetasCreador(long idUsuario) {

        EntityManager em = UtilsBD.getEntity();

        try {

            return em.createQuery("FROM Receta r WHERE r.usuario.id_usuario = :id", Receta.class)
                    .setParameter("id",idUsuario).getResultList();

        } finally {
            em.close();
        }

    }

    /**
     * Metodo que borra una receta de la base de datos
     * @param idReceta id de la receta que vamos a borrar
     */
    public void eliminarReceta(Long idReceta) {


        EntityManager em = UtilsBD.getEntity();
        try {
            em.getTransaction().begin();
            Receta receta = em.find(Receta.class, idReceta);
            em.remove(receta);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    }

    /**
     * Metodo que actualiza una receta ya existente
     * @param recetaEditada receta con los campos ya editados
     * @param nuevosIngredientes lista de todos los ingredientes tanto nuevos como antiguos actualizados
     */
    public void actualizarReceta(Receta recetaEditada, List<Ingrediente> nuevosIngredientes) {

        EntityManager em = UtilsBD.getEntity();

        try {
            em.getTransaction().begin();

            // Buscamos la receta que queremos editar
            Receta recetaOriginal = em.find(Receta.class, recetaEditada.getId_receta());

            // Actualizamos los campos
            recetaOriginal.setTitulo(recetaEditada.getTitulo());
            recetaOriginal.setResumen(recetaEditada.getResumen());
            recetaOriginal.setPasos(recetaEditada.getPasos());
            recetaOriginal.setPublica(recetaEditada.isPublica());

            // Limpiamos los ingredientes actuales
            recetaOriginal.getIngredientes().clear();

            // Añadimos los nuevos ingredientes
            for (Ingrediente ingrediente : nuevosIngredientes) {
                recetaOriginal.addIngrediente(ingrediente);
            }

            // guardamos los cambios
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    /**
     * Metodo que carga todas las recetas públicas que no pertenezcan al usuario de la sesión
     * @param idUsuario id del usuario de la sesión
     * @return lista de recetas publicas
     */
    public List<Receta> cargarRecetasPublicas(long idUsuario) {

        EntityManager em = UtilsBD.getEntity();

        try {

            return em.createQuery("FROM Receta r WHERE r.usuario.id_usuario != :id AND r.publica = true " +
                            "order by r.id_receta desc", Receta.class).setParameter("id",idUsuario).getResultList();

        } finally {
            em.close();
        }

    }

    /**
     * Metodo que comprueba si una receta ya ha sido guardada por ese usuario
     * @param idUsuario ide del usuario de la sesión
     * @param idReceta id de la receta que queremos comprobar
     * @return Devuelve true si ya ha sido guardada
     */
    public boolean recetaYaGuardada(long idUsuario, long idReceta) {
        EntityManager em = UtilsBD.getEntity();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(r) FROM Usuario u JOIN u.recetasGuardadas r " +
                                    "WHERE u.id = :idUsuario AND r.id = :idReceta", Long.class)
                    .setParameter("idUsuario", idUsuario).setParameter("idReceta", idReceta)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    /**
     * Metodo que registra que el usuario ha guardado una receta pública
     * @param receta receta que vamos a guardar
     * @param idUsuario id del usuario de la sesión
     */
    public void guardarReceta(Receta receta, long idUsuario) {

        EntityManager em = UtilsBD.getEntity();

        try {

            // iniciamos la transacción
            em.getTransaction().begin();

            // buscamos al usuario
            Usuario user = em.find(Usuario.class, idUsuario);

            // Añadimos la receta como guardada
            user.getRecetasGuardadas().add(receta);

            // cerramos la transacción y guardamos los cambios
            em.getTransaction().commit();

        }finally {
            em.close();
        }


    }

    /**
     * Metodo deja una receta de estar guardada
     * @param idReceta receta que queremos dejar de guardar
     * @param idUsuario id del usuario de la sesión
     */
    public void quitarDeGuardadas(Long idReceta, long idUsuario) {

        EntityManager em = UtilsBD.getEntity();

        try {

            // iniciamos la transacción
            em.getTransaction().begin();

            // buscamos el usuario y la receta
            Receta receta = em.find(Receta.class, idReceta);
            Usuario usuario = em.find(Usuario.class, idUsuario);

            // quitamos la receta de guardada
            usuario.getRecetasGuardadas().remove(receta);

            // cerramos la transacción y guardamos los cambios
            em.getTransaction().commit();

        }finally {
            em.close();
        }


    }

    /**
     * Metodo que carga las recetas cargadas
     * @param idUsuario id del usuario de la sesión
     * @return lista de recetar guardadas
     */
    public List<Receta> cargarRecetasGuardadas(long idUsuario) {

        EntityManager em = UtilsBD.getEntity();

        try {

            // Iniciamos la transacción
            em.getTransaction().begin();

            // buscamos al usuario
            Usuario user = em.find(Usuario.class, idUsuario);

            // devolvemos la lista de recetas guardadas por el usuario
            return user.getRecetasGuardadas();

        }finally {
            em.close();
        }

    }
}
