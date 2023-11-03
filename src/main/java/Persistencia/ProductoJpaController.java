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
import Modelo.Proveedor;
import Modelo.DetalleOrden;
import Modelo.Producto;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Bruno Sandoval
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public ProductoJpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaHaugPU");
    }
    public void create(Producto producto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor prov = producto.getProv();
            if (prov != null) {
                prov = em.getReference(prov.getClass(), prov.getId());
                producto.setProv(prov);
            }
            DetalleOrden ord = producto.getOrd();
            if (ord != null) {
                ord = em.getReference(ord.getClass(), ord.getId());
                producto.setOrd(ord);
            }
            em.persist(producto);
            if (prov != null) {
                prov.getListaProducto().add(producto);
                prov = em.merge(prov);
            }
            if (ord != null) {
                ord.getListaProducto().add(producto);
                ord = em.merge(ord);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            Proveedor provOld = persistentProducto.getProv();
            Proveedor provNew = producto.getProv();
            DetalleOrden ordOld = persistentProducto.getOrd();
            DetalleOrden ordNew = producto.getOrd();
            if (provNew != null) {
                provNew = em.getReference(provNew.getClass(), provNew.getId());
                producto.setProv(provNew);
            }
            if (ordNew != null) {
                ordNew = em.getReference(ordNew.getClass(), ordNew.getId());
                producto.setOrd(ordNew);
            }
            producto = em.merge(producto);
            if (provOld != null && !provOld.equals(provNew)) {
                provOld.getListaProducto().remove(producto);
                provOld = em.merge(provOld);
            }
            if (provNew != null && !provNew.equals(provOld)) {
                provNew.getListaProducto().add(producto);
                provNew = em.merge(provNew);
            }
            if (ordOld != null && !ordOld.equals(ordNew)) {
                ordOld.getListaProducto().remove(producto);
                ordOld = em.merge(ordOld);
            }
            if (ordNew != null && !ordNew.equals(ordOld)) {
                ordNew.getListaProducto().add(producto);
                ordNew = em.merge(ordNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            Proveedor prov = producto.getProv();
            if (prov != null) {
                prov.getListaProducto().remove(producto);
                prov = em.merge(prov);
            }
            DetalleOrden ord = producto.getOrd();
            if (ord != null) {
                ord.getListaProducto().remove(producto);
                ord = em.merge(ord);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public List<Producto> findAllProductosConProveedor() {
        // Obtiene una instancia de EntityManager para interactuar con la base de datos.
        EntityManager em = getEntityManager();

        try {
            // Crea una consulta JPA que selecciona todos los productos y realiza una unión izquierda con el atributo "prov".
            TypedQuery<Producto> query = em.createQuery(
                    "SELECT p FROM Producto p LEFT JOIN FETCH p.prov",
                    Producto.class
            );

            // Ejecuta la consulta y almacena los resultados en una lista de productos.
            List<Producto> productos = query.getResultList();

            // Verifica si la lista de productos está vacía y ajusta para devolver null si no hay resultados.
            return productos.isEmpty() ? null : productos;
        } finally {
            // Asegura que se cierre adecuadamente el EntityManager para liberar recursos.
            em.close();
        }
    }
    public void cambiarEstadoProducto(int id) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            // Buscar el proveedor por su ID
            Producto producto = em.find(Producto.class, id);
            
            if (producto != null) {
                // Cambiar el estado del proveedor (suponiendo que tienes un atributo 'estado' en tu clase Proveedor)
                if ("Activo".equals(producto.getEstado())) {
                    producto.setEstado("Desactivo");
                } else {
                    producto.setEstado("Activo");
                }
                
                // Realizar la actualización en la base de datos
                em.merge(producto);
                
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
    public void actualizarStock(int id, int cantidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Producto producto = em.find(Producto.class, id);

            if (producto != null) {
                int stockActual = producto.getCantidad();
                if (stockActual >= cantidad) {
                    producto.setCantidad(stockActual - cantidad);
                    em.merge(producto);
                    em.getTransaction().commit();
                } else {
                    throw new Exception("No hay suficiente stock disponible para realizar la compra.");
                }
            } else {
                throw new NonexistentEntityException("El producto con ID " + id + " no existe.");
            }
        } catch (Exception ex) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int obtenerStockPorId(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();

            // Buscar el producto por su ID
            Producto producto = em.find(Producto.class, id);

            if (producto != null) {
                return producto.getCantidad();
            } else {
                throw new NonexistentEntityException("El producto con ID " + id + " no existe.");
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


}
