/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Orden;
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
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public ClienteJpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaHaugPU");
    }

    public void create(Cliente cliente) {
        if (cliente.getListaOrdenes() == null) {
            cliente.setListaOrdenes(new ArrayList<Orden>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Orden> attachedListaOrdenes = new ArrayList<Orden>();
            for (Orden listaOrdenesOrdenToAttach : cliente.getListaOrdenes()) {
                listaOrdenesOrdenToAttach = em.getReference(listaOrdenesOrdenToAttach.getClass(), listaOrdenesOrdenToAttach.getId());
                attachedListaOrdenes.add(listaOrdenesOrdenToAttach);
            }
            cliente.setListaOrdenes(attachedListaOrdenes);
            em.persist(cliente);
            for (Orden listaOrdenesOrden : cliente.getListaOrdenes()) {
                Cliente oldCliOfListaOrdenesOrden = listaOrdenesOrden.getCli();
                listaOrdenesOrden.setCli(cliente);
                listaOrdenesOrden = em.merge(listaOrdenesOrden);
                if (oldCliOfListaOrdenesOrden != null) {
                    oldCliOfListaOrdenesOrden.getListaOrdenes().remove(listaOrdenesOrden);
                    oldCliOfListaOrdenesOrden = em.merge(oldCliOfListaOrdenesOrden);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            List<Orden> listaOrdenesOld = persistentCliente.getListaOrdenes();
            List<Orden> listaOrdenesNew = cliente.getListaOrdenes();
            List<Orden> attachedListaOrdenesNew = new ArrayList<Orden>();
            for (Orden listaOrdenesNewOrdenToAttach : listaOrdenesNew) {
                listaOrdenesNewOrdenToAttach = em.getReference(listaOrdenesNewOrdenToAttach.getClass(), listaOrdenesNewOrdenToAttach.getId());
                attachedListaOrdenesNew.add(listaOrdenesNewOrdenToAttach);
            }
            listaOrdenesNew = attachedListaOrdenesNew;
            cliente.setListaOrdenes(listaOrdenesNew);
            cliente = em.merge(cliente);
            for (Orden listaOrdenesOldOrden : listaOrdenesOld) {
                if (!listaOrdenesNew.contains(listaOrdenesOldOrden)) {
                    listaOrdenesOldOrden.setCli(null);
                    listaOrdenesOldOrden = em.merge(listaOrdenesOldOrden);
                }
            }
            for (Orden listaOrdenesNewOrden : listaOrdenesNew) {
                if (!listaOrdenesOld.contains(listaOrdenesNewOrden)) {
                    Cliente oldCliOfListaOrdenesNewOrden = listaOrdenesNewOrden.getCli();
                    listaOrdenesNewOrden.setCli(cliente);
                    listaOrdenesNewOrden = em.merge(listaOrdenesNewOrden);
                    if (oldCliOfListaOrdenesNewOrden != null && !oldCliOfListaOrdenesNewOrden.equals(cliente)) {
                        oldCliOfListaOrdenesNewOrden.getListaOrdenes().remove(listaOrdenesNewOrden);
                        oldCliOfListaOrdenesNewOrden = em.merge(oldCliOfListaOrdenesNewOrden);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<Orden> listaOrdenes = cliente.getListaOrdenes();
            for (Orden listaOrdenesOrden : listaOrdenes) {
                listaOrdenesOrden.setCli(null);
                listaOrdenesOrden = em.merge(listaOrdenesOrden);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public void cambiarEstadoCliente(int id) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            // Buscar el proveedor por su ID
            Cliente cliente = em.find(Cliente.class, id);
            
            if (cliente != null) {
                // Cambiar el estado del proveedor (suponiendo que tienes un atributo 'estado' en tu clase Proveedor)
                if ("Activo".equals(cliente.getEstado())) {
                    cliente.setEstado("Desactivo");
                } else {
                    cliente.setEstado("Activo");
                }
                
                // Realizar la actualización en la base de datos
                em.merge(cliente);
                
                em.getTransaction().commit();
            } else {
                throw new NonexistentEntityException("El proveedor con ID " + id + " no existe.");
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                throw new NonexistentEntityException("Error al cambiar el estado del proveedor.");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
}
