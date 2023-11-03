/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.DetalleOrden;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Orden;
import Modelo.Producto;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Bruno Sandoval
 */
public class DetalleOrdenJpaController implements Serializable {

    public DetalleOrdenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public DetalleOrdenJpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaHaugPU");
    }

    /**
 * Crea un nuevo registro de DetalleOrden en la base de datos.
 *
 * @param detalleOrden El DetalleOrden que se va a crear.
     */
    public void create(DetalleOrden detalleOrden) {
        if (detalleOrden.getListaProducto() == null) {
            detalleOrden.setListaProducto(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            // Obtiene la Orden asociada al DetalleOrden
            Orden orden = detalleOrden.getOrden();
            if (orden != null) {
                // Obtiene la referencia a la Orden desde la base de datos
                orden = em.getReference(orden.getClass(), orden.getId());

                // Asigna la Orden al DetalleOrden
                detalleOrden.setOrden(orden);

                // Agrega el DetalleOrden a la lista de detalles de la Orden
                orden.getDetallesOrden().add(detalleOrden);
            }

            // Maneja la lista de Productos asociada al DetalleOrden
            List<Producto> attachedListaProducto = new ArrayList<Producto>();
            for (Producto listaProductoProductoToAttach : detalleOrden.getListaProducto()) {
                // Obtiene la referencia a cada Producto desde la base de datos
                listaProductoProductoToAttach = em.getReference(listaProductoProductoToAttach.getClass(), listaProductoProductoToAttach.getId());
                attachedListaProducto.add(listaProductoProductoToAttach);
            }

            // Asigna la lista de Productos al DetalleOrden
            detalleOrden.setListaProducto(attachedListaProducto);

            // Persiste el DetalleOrden en la base de datos
            em.persist(detalleOrden);

            // Confirma la transacción
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Edita un registro existente de DetalleOrden en la base de datos.
     *
     * @param detalleOrden El DetalleOrden que se va a editar.
     * @throws NonexistentEntityException Si el DetalleOrden no existe en la
     * base de datos.
     * @throws Exception Si ocurre un error durante la edición.
     */
    public void edit(DetalleOrden detalleOrden) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            // Busca el DetalleOrden persistente en la base de datos
            DetalleOrden persistentDetalleOrden = em.find(DetalleOrden.class, detalleOrden.getId());

            // Obtiene la Orden asociada al DetalleOrden
            Orden ordenOld = persistentDetalleOrden.getOrden();
            Orden ordenNew = detalleOrden.getOrden();

            // Obtiene las listas de Productos asociadas al DetalleOrden
            List<Producto> listaProductoOld = persistentDetalleOrden.getListaProducto();
            List<Producto> listaProductoNew = detalleOrden.getListaProducto();

            // Actualiza la referencia a la nueva Orden si ha cambiado
            if (ordenNew != null) {
                ordenNew = em.getReference(ordenNew.getClass(), ordenNew.getId());
                detalleOrden.setOrden(ordenNew);
            }

            // Maneja la lista de Productos asociada al DetalleOrden
            List<Producto> attachedListaProductoNew = new ArrayList<Producto>();
            for (Producto listaProductoNewProductoToAttach : listaProductoNew) {
                listaProductoNewProductoToAttach = em.getReference(listaProductoNewProductoToAttach.getClass(), listaProductoNewProductoToAttach.getId());
                attachedListaProductoNew.add(listaProductoNewProductoToAttach);
            }

            // Asigna la lista de Productos actualizada al DetalleOrden
            detalleOrden.setListaProducto(attachedListaProductoNew);

            // Realiza la actualización en la base de datos
            detalleOrden = em.merge(detalleOrden);

            // Actualiza la relación con la Orden anterior
            if (ordenOld != null && !ordenOld.equals(ordenNew)) {
                ordenOld.getDetallesOrden().remove(persistentDetalleOrden);
                ordenOld = em.merge(ordenOld);
            }

            // Actualiza la relación con la nueva Orden
            if (ordenNew != null && !ordenNew.equals(ordenOld)) {
                ordenNew.getDetallesOrden().add(detalleOrden);
                ordenNew = em.merge(ordenNew);
            }

            // Elimina los Productos que ya no están asociados al DetalleOrden
            for (Producto listaProductoOldProducto : listaProductoOld) {
                if (!listaProductoNew.contains(listaProductoOldProducto)) {
                    listaProductoOldProducto.setOrd(null);
                    listaProductoOldProducto = em.merge(listaProductoOldProducto);
                }
            }

            // Asigna el DetalleOrden a los Productos nuevos
            for (Producto listaProductoNewProducto : listaProductoNew) {
                if (!listaProductoOld.contains(listaProductoNewProducto)) {
                    listaProductoNewProducto.setOrd(detalleOrden);
                    listaProductoNewProducto = em.merge(listaProductoNewProducto);
                }
            }

            // Confirma la transacción
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = detalleOrden.getId();
                if (findDetalleOrden(id) == null) {
                    throw new NonexistentEntityException("The detalleOrden with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleOrden detalleOrden;
            try {
                detalleOrden = em.getReference(DetalleOrden.class, id);
                detalleOrden.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleOrden with id " + id + " no longer exists.", enfe);
            }
            Orden orden = detalleOrden.getOrden();
            if (orden != null) {
                orden.setDetallesOrden(null);
                orden = em.merge(orden);
            }
            List<Producto> listaProducto = detalleOrden.getListaProducto();
            for (Producto listaProductoProducto : listaProducto) {
                listaProductoProducto.setOrd(null);
                listaProductoProducto = em.merge(listaProductoProducto);
            }
            em.remove(detalleOrden);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleOrden> findDetalleOrdenEntities() {
        return findDetalleOrdenEntities(true, -1, -1);
    }

    public List<DetalleOrden> findDetalleOrdenEntities(int maxResults, int firstResult) {
        return findDetalleOrdenEntities(false, maxResults, firstResult);
    }

    private List<DetalleOrden> findDetalleOrdenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleOrden.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public DetalleOrden findDetalleOrden(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleOrden.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleOrdenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleOrden> rt = cq.from(DetalleOrden.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<DetalleOrden> listarDetallesDeOrden(int idOrden) {
        EntityManager em = getEntityManager();
        try {
            Orden orden = em.find(Orden.class, idOrden);
            if (orden != null) {
                return orden.getDetallesOrden();
            } else {
                return null; // Orden no encontrada
            }
        } finally {
            em.close();
        }
    }
    

}
