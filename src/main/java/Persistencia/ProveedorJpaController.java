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
import Modelo.Producto;
import Modelo.Proveedor;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 *
 * @author Bruno Sandoval
 */
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public ProveedorJpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaHaugPU");
    }
    public void create(Proveedor proveedor) {
        if (proveedor.getListaProducto() == null) {
            proveedor.setListaProducto(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Producto> attachedListaProducto = new ArrayList<Producto>();
            for (Producto listaProductoProductoToAttach : proveedor.getListaProducto()) {
                listaProductoProductoToAttach = em.getReference(listaProductoProductoToAttach.getClass(), listaProductoProductoToAttach.getId());
                attachedListaProducto.add(listaProductoProductoToAttach);
            }
            proveedor.setListaProducto(attachedListaProducto);
            em.persist(proveedor);
            for (Producto listaProductoProducto : proveedor.getListaProducto()) {
                Proveedor oldProvOfListaProductoProducto = listaProductoProducto.getProv();
                listaProductoProducto.setProv(proveedor);
                listaProductoProducto = em.merge(listaProductoProducto);
                if (oldProvOfListaProductoProducto != null) {
                    oldProvOfListaProductoProducto.getListaProducto().remove(listaProductoProducto);
                    oldProvOfListaProductoProducto = em.merge(oldProvOfListaProductoProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getId());
            List<Producto> listaProductoOld = persistentProveedor.getListaProducto();
            List<Producto> listaProductoNew = proveedor.getListaProducto();
            List<Producto> attachedListaProductoNew = new ArrayList<Producto>();
            for (Producto listaProductoNewProductoToAttach : listaProductoNew) {
                listaProductoNewProductoToAttach = em.getReference(listaProductoNewProductoToAttach.getClass(), listaProductoNewProductoToAttach.getId());
                attachedListaProductoNew.add(listaProductoNewProductoToAttach);
            }
            listaProductoNew = attachedListaProductoNew;
            proveedor.setListaProducto(listaProductoNew);
            proveedor = em.merge(proveedor);
            for (Producto listaProductoOldProducto : listaProductoOld) {
                if (!listaProductoNew.contains(listaProductoOldProducto)) {
                    listaProductoOldProducto.setProv(null);
                    listaProductoOldProducto = em.merge(listaProductoOldProducto);
                }
            }
            for (Producto listaProductoNewProducto : listaProductoNew) {
                if (!listaProductoOld.contains(listaProductoNewProducto)) {
                    Proveedor oldProvOfListaProductoNewProducto = listaProductoNewProducto.getProv();
                    listaProductoNewProducto.setProv(proveedor);
                    listaProductoNewProducto = em.merge(listaProductoNewProducto);
                    if (oldProvOfListaProductoNewProducto != null && !oldProvOfListaProductoNewProducto.equals(proveedor)) {
                        oldProvOfListaProductoNewProducto.getListaProducto().remove(listaProductoNewProducto);
                        oldProvOfListaProductoNewProducto = em.merge(oldProvOfListaProductoNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = proveedor.getId();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
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
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            List<Producto> listaProducto = proveedor.getListaProducto();
            for (Producto listaProductoProducto : listaProducto) {
                listaProductoProducto.setProv(null);
                listaProductoProducto = em.merge(listaProductoProducto);
            }
            em.remove(proveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
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

    public Proveedor findProveedor(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
     public Proveedor findProveedorByName(String name) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Proveedor p WHERE p.nombre = :nombre");
            query.setParameter("nombre", name);
            return (Proveedor) query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null si no encuentra ningún proveedor con ese nombre
        } finally {
            em.close();
        }
    }
     public void cambiarEstadoProveedor(int idProveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            // Buscar el proveedor por su ID
            Proveedor proveedor = em.find(Proveedor.class, idProveedor);
            
            if (proveedor != null) {
                // Cambiar el estado del proveedor (suponiendo que tienes un atributo 'estado' en tu clase Proveedor)
                if ("Activo".equals(proveedor.getEstado())) {
                    proveedor.setEstado("Desactivado");
                } else {
                    proveedor.setEstado("Activo");
                }
                
                // Realizar la actualización en la base de datos
                em.merge(proveedor);
                
                em.getTransaction().commit();
            } else {
                throw new NonexistentEntityException("El proveedor con ID " + idProveedor + " no existe.");
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
