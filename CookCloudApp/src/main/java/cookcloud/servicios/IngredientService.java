package cookcloud.servicios;

import cookcloud.modelo.Ingrediente;
import cookcloud.modelo.Receta;
import cookcloud.utils.UtilsBD;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class IngredientService {

    /**
     * Metodo que recoje los ingredientes de una receta buscada por su id
     * @param idReceta id de la receta de la que buscamos sus ingredientes
     * @return lista con los ingredientes de la receta
     */
    public List<Ingrediente> listarRecetas(Long idReceta) {

        EntityManager em = UtilsBD.getEntity();

        try {

            return em.createQuery("FROM Ingrediente i WHERE i.receta.id_receta = :id", Ingrediente.class)
                    .setParameter("id",idReceta).getResultList();

        } finally {
            em.close();
        }

    }
}
