package cookcloud.servicios;

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
}
