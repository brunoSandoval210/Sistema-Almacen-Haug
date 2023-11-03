package Persistencia;

import Modelo.*;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenceController {

    UsuarioJpaController usuJPA = new UsuarioJpaController();

    public void crearUsuario(Usuario usu) {
        usuJPA.create(usu);
    }
    
    public List<Usuario> traerUsuarios() {
        return usuJPA.findUsuarioEntities();
    }
    
    public void borrarUsuario(int id_eliminar) {
        try {
            usuJPA.destroy(id_eliminar);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Usuario traerUsuario(int id_editar) {
        return usuJPA.findUsuario(id_editar);
    }

    public void editarUsuario(Usuario usu) {
        try {
            usuJPA.edit(usu);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cambiarEstadoUsuario(int id) {
        try {
            usuJPA.cambiarEstadoUsuario(id);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    ProductoJpaController prodJPA = new ProductoJpaController();
    
    public void crearProducto(Producto prod){
        prodJPA.create(prod);
    }
    
    public List<Producto> traerProductos(){
        return prodJPA.findProductoEntities();
    }
    
    public void borrarProducto(int id_eliminar) {
        try {
            prodJPA.destroy(id_eliminar);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Producto traerProducto(int id_editar) {
        return prodJPA.findProducto(id_editar);
    }
    
    public void editarProducto(Producto prod){
        try {
            prodJPA.edit(prod);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Producto> encontrarTodosProductosConProveedor() {
        return prodJPA.findAllProductosConProveedor();
    }
    public void cambiarEstadoProducto(int id){
        try {
            prodJPA.cambiarEstadoProducto(id);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void actualizarStock(int id, int cantidad){
        try {
            prodJPA.actualizarStock(id,cantidad);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int obtenerStockPorId(int id) {

        try {
            return prodJPA.obtenerStockPorId(id);
        } catch (Exception ex) {
            // Manejar la excepción aquí o relanzarla como RuntimeException si es apropiado
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    
    ProveedorJpaController proveJPA = new ProveedorJpaController();
    
    public void crearProveedor(Proveedor prove){
        proveJPA.create(prove);
    }
    
    public List<Proveedor> traerProveedores(){
        return proveJPA.findProveedorEntities();
    }
    
    public void borrarProveedor(int id_eliminar) {
        try {
            proveJPA.destroy(id_eliminar);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Proveedor traerProveedor(int id_editar) {
        return proveJPA.findProveedor(id_editar);
    }
    
    public void editarProveedor(Proveedor prove){
        try {
            proveJPA.edit(prove);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Proveedor buscarProveedorPorNombre(String nombre) {
        return proveJPA.findProveedorByName(nombre);
    }
    
    public void cambiarEstadoProveedor(int id) {
        try {
            proveJPA.cambiarEstadoProveedor(id);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    ClienteJpaController cliJPA = new ClienteJpaController();
    
    public void crearCliente(Cliente cli) {
        cliJPA.create(cli);
    }

    public List<Cliente> traerClientes() {
        return cliJPA.findClienteEntities();
    }
    

    public void borrarCliente(int id_eliminar) {
        try {
            cliJPA.destroy(id_eliminar);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cliente traerCliente(int id_editar) {
        return cliJPA.findCliente(id_editar);
    }

    public void editarCliente(Cliente cli) {
        try {
            cliJPA.edit(cli);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void cambiarEstadoCliente(int id) {
        try {
            cliJPA.cambiarEstadoCliente(id);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    DetalleOrdenJpaController detalleJPA = new DetalleOrdenJpaController();
    
    public void crearDetalleOrd(DetalleOrden detalle) {
        detalleJPA.create(detalle);
    }

    public List<DetalleOrden> traerDetalleOrd() {
        return detalleJPA.findDetalleOrdenEntities();
    }
    
    

    public void borrarDetalleOrd(int id_eliminar) {
        try {
            detalleJPA.destroy(id_eliminar);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DetalleOrden traerDetalleOrd(int id_editar) {
        return detalleJPA.findDetalleOrden(id_editar);
    }

    public void editarDetalleOrd(DetalleOrden detalle) {
        try {
            detalleJPA.edit(detalle);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   /* public List<DetalleOrden> listarDetallesDeOrden(int id_editar) {
        return detalleJPA.listarDetallesDeOrden(id_editar);
    }*/
    
    OrdenJpaController OrdenJPA = new OrdenJpaController();
    
    public void crearOrden(Orden ord) {
        OrdenJPA.create(ord);
    }

    public List<Orden> traerOrden() {
        return OrdenJPA.findOrdenEntities();
    }
    

    public void borrarOrden(int id_eliminar) {
        try {
            OrdenJPA.destroy(id_eliminar);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Orden traerOrden(int id_editar) {
        return OrdenJPA.findOrden(id_editar);
    }

    public void editarOrden(Orden ord) {
        try {
            OrdenJPA.edit(ord);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<Orden> encontrarOrdenesConDetalleOrden() {
        return OrdenJPA.findAllOrdenesConDetalles();
    }
    public Orden encontrarOrdenesConDetalleOrdenPorId(int id) {
        return OrdenJPA.findOrdenConDetallesById(id);
    }
    
}
