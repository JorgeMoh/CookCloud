package cookcloud.servicios;

import cookcloud.modelo.Receta;
import cookcloud.modelo.Usuario;
import cookcloud.utils.UtilsBD;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RecipeService {


    public void subirReceta(Receta nuevaReceta) {

        EntityManager em = UtilsBD.getEntity();

        try {

            em.getTransaction().begin();

            // Fusiona el usuario con el EntityManager activo
            Usuario usuarioManaged = em.merge(nuevaReceta.getUsuario());

            // Persiste la receta
            em.persist(nuevaReceta);

            // Sincroniza ambos lados de la relación
            usuarioManaged.getRecetas().add(nuevaReceta);

            em.getTransaction().commit();

        } finally {
            em.close();
        }

    }

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
