package cookcloud.servicios;

import cookcloud.modelo.Ingrediente;
import cookcloud.modelo.Receta;
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
}
