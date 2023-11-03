/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Cliente;
import Modelo.DetalleOrden;
import Modelo.Orden;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


/**
 *
 * @author Bruno Sandoval
 */
public class OrdenJpaController implements Serializable {

    public OrdenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public OrdenJpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaHaugPU");
    }

    public void create(Orden orden) {
        if (orden.getDetallesOrden() == null) {
            orden.setDetallesOrden(new ArrayList<DetalleOrden>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cli = orden.getCli();
            if (cli != null) {
                cli = em.getReference(cli.getClass(), cli.getId());
                orden.setCli(cli);
            }
            List<DetalleOrden> attachedDetallesOrden = new ArrayList<DetalleOrden>();
            for (DetalleOrden detallesOrdenDetalleOrdenToAttach : orden.getDetallesOrden()) {
                detallesOrdenDetalleOrdenToAttach = em.getReference(detallesOrdenDetalleOrdenToAttach.getClass(), detallesOrdenDetalleOrdenToAttach.getId());
                attachedDetallesOrden.add(detallesOrdenDetalleOrdenToAttach);
            }
            orden.setDetallesOrden(attachedDetallesOrden);
            em.persist(orden);
            if (cli != null) {
                cli.getListaOrdenes().add(orden);
                cli = em.merge(cli);
            }
            for (DetalleOrden detallesOrdenDetalleOrden : orden.getDetallesOrden()) {
                Orden oldOrdenOfDetallesOrdenDetalleOrden = detallesOrdenDetalleOrden.getOrden();
                if (oldOrdenOfDetallesOrdenDetalleOrden != null) {
                    oldOrdenOfDetallesOrdenDetalleOrden.setDetallesOrden(null);
                    oldOrdenOfDetallesOrdenDetalleOrden = em.merge(oldOrdenOfDetallesOrdenDetalleOrden);
                }
                detallesOrdenDetalleOrden.setOrden(orden);
                detallesOrdenDetalleOrden = em.merge(detallesOrdenDetalleOrden);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orden orden) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orden persistentOrden = em.find(Orden.class, orden.getId());
            Cliente cliOld = persistentOrden.getCli();
            Cliente cliNew = orden.getCli();
            List<DetalleOrden> detallesOrdenOld = persistentOrden.getDetallesOrden();
            List<DetalleOrden> detallesOrdenNew = orden.getDetallesOrden();
            if (cliNew != null) {
                cliNew = em.getReference(cliNew.getClass(), cliNew.getId());
                orden.setCli(cliNew);
            }
            List<DetalleOrden> attachedDetallesOrdenNew = new ArrayList<DetalleOrden>();
            for (DetalleOrden detallesOrdenNewDetalleOrdenToAttach : detallesOrdenNew) {
                detallesOrdenNewDetalleOrdenToAttach = em.getReference(detallesOrdenNewDetalleOrdenToAttach.getClass(), detallesOrdenNewDetalleOrdenToAttach.getId());
                attachedDetallesOrdenNew.add(detallesOrdenNewDetalleOrdenToAttach);
            }
            detallesOrdenNew = attachedDetallesOrdenNew;
            orden.setDetallesOrden(detallesOrdenNew);
            orden = em.merge(orden);
            if (cliOld != null && !cliOld.equals(cliNew)) {
                cliOld.getListaOrdenes().remove(orden);
                cliOld = em.merge(cliOld);
            }
            if (cliNew != null && !cliNew.equals(cliOld)) {
                cliNew.getListaOrdenes().add(orden);
                cliNew = em.merge(cliNew);
            }
            for (DetalleOrden detallesOrdenOldDetalleOrden : detallesOrdenOld) {
                if (!detallesOrdenNew.contains(detallesOrdenOldDetalleOrden)) {
                    detallesOrdenOldDetalleOrden.setOrden(null);
                    detallesOrdenOldDetalleOrden = em.merge(detallesOrdenOldDetalleOrden);
                }
            }
            for (DetalleOrden detallesOrdenNewDetalleOrden : detallesOrdenNew) {
                if (!detallesOrdenOld.contains(detallesOrdenNewDetalleOrden)) {
                    Orden oldOrdenOfDetallesOrdenNewDetalleOrden = detallesOrdenNewDetalleOrden.getOrden();
                    detallesOrdenNewDetalleOrden.setOrden(orden);
                    detallesOrdenNewDetalleOrden = em.merge(detallesOrdenNewDetalleOrden);
                    if (oldOrdenOfDetallesOrdenNewDetalleOrden != null && !oldOrdenOfDetallesOrdenNewDetalleOrden.equals(orden)) {
                        oldOrdenOfDetallesOrdenNewDetalleOrden.getDetallesOrden().remove(detallesOrdenNewDetalleOrden);
                        oldOrdenOfDetallesOrdenNewDetalleOrden = em.merge(oldOrdenOfDetallesOrdenNewDetalleOrden);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = orden.getId();
                if (findOrden(id) == null) {
                    throw new NonexistentEntityException("The orden with id " + id + " no longer exists.");
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
            Orden orden;
            try {
                orden = em.getReference(Orden.class, id);
                orden.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orden with id " + id + " no longer exists.", enfe);
            }
            Cliente cli = orden.getCli();
            if (cli != null) {
                cli.getListaOrdenes().remove(orden);
                cli = em.merge(cli);
            }
            List<DetalleOrden> detallesOrden = orden.getDetallesOrden();
            for (DetalleOrden detallesOrdenDetalleOrden : detallesOrden) {
                detallesOrdenDetalleOrden.setOrden(null);
                detallesOrdenDetalleOrden = em.merge(detallesOrdenDetalleOrden);
            }
            em.remove(orden);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orden> findOrdenEntities() {
        return findOrdenEntities(true, -1, -1);
    }

    public List<Orden> findOrdenEntities(int maxResults, int firstResult) {
        return findOrdenEntities(false, maxResults, firstResult);
    }

    private List<Orden> findOrdenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orden.class));
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

    public Orden findOrden(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orden.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orden> rt = cq.from(Orden.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Orden> findAllOrdenesConDetalles() {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Orden> query = em.createQuery(
                    "SELECT o FROM Orden o JOIN FETCH o.detallesOrden",
                    Orden.class
            );

            List<Orden> ordenes = query.getResultList();

            return ordenes.isEmpty() ? null : ordenes;
        } finally {
            em.close();
        }
    }

    public Orden findOrdenConDetallesById(int ordenId) {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Orden> query = em.createQuery(
                    "SELECT o FROM Orden o JOIN FETCH o.detallesOrden WHERE o.id = :ordenId",
                    Orden.class
            );
            query.setParameter("ordenId", ordenId);

            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

}
