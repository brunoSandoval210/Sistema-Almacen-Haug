
package Modelo;

import Persistencia.PersistenceController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller {
    
    PersistenceController controlP = new PersistenceController();
    
    //Seccion Usuario
    public void crearUsuario(Usuario usu){
        controlP.crearUsuario(usu);
    }
    
    public List<Usuario> traerUsuarios(){
        return controlP.traerUsuarios();
    }
    
    public void borrarUsuario(int id_eliminar) {
        controlP.borrarUsuario(id_eliminar);
    }

    public Usuario traerUsuario(int id_editar) {
        return controlP.traerUsuario(id_editar);
    }

    public void editarUsuario(Usuario usu) {
        controlP.editarUsuario(usu);
    }
    public void cambiarEstadoUsuario(int id_editar) {
        controlP.cambiarEstadoUsuario(id_editar);
    }
    
    //Seccion Producto
    public void crearProducto(Producto prod){
        controlP.crearProducto(prod);
    }
    
    public List<Producto> traerProductos(){
        return controlP.traerProductos();
    }
    
    public void borrarProducto(int id_eliminar) {
        controlP.borrarProducto(id_eliminar);
    }
    
    public Producto traerProducto(int id_editar) {
        return controlP.traerProducto(id_editar);
    }
    
    public void editarProducto(Producto prod) {
        controlP.editarProducto(prod);
    }
    public List<Producto> encontrarTodosProductosConProveedor() {
        return controlP.encontrarTodosProductosConProveedor();
    }
    public void cambiarEstadoProducto(int id){
        controlP.cambiarEstadoProducto(id);
    }
    public void actualizarStock(int id, int cantidad) {
        controlP.actualizarStock(id, cantidad);

    }

    public int obtenerStockPorId(int id) {

        return controlP.obtenerStockPorId(id);

    }
 
    
    //Seccion Proveedor
    public void crearProveedor(Proveedor prove){
        controlP.crearProveedor(prove);
    }
    
    public List<Proveedor> traerProveedores(){
        return controlP.traerProveedores();
    }
    
    public void borrarProveedor(int id_eliminar) {
        controlP.borrarProveedor(id_eliminar);
    }
    
    public Proveedor traerProveedor(int id_editar) {
        return controlP.traerProveedor(id_editar);
    }
    
    public void editarProveedor(Proveedor prove) {
        controlP.editarProveedor(prove);
    }
    
    public Proveedor buscarProveedorPorNombre(String nombre) {
        return controlP.buscarProveedorPorNombre(nombre);
    }
    
    public void cambiarEstadoProveedor(int id_editar) {
        controlP.cambiarEstadoProveedor(id_editar);
    }
    //Seccion Cliente
    public void crearCliente(Cliente cli){
        controlP.crearCliente(cli);
    }
    
    public List<Cliente> traerClientes(){
        return controlP.traerClientes();
    }
    
    public void borrarCliente(int id_eliminar) {
        controlP.borrarCliente(id_eliminar);
    }
    
    public Cliente traerCliente(int id_editar) {
        return controlP.traerCliente(id_editar);
    }
    
    public void editarCliente(Cliente cli) {
        controlP.editarCliente(cli);
    }
     public void cambiarEstadoCliente(int id) {
         controlP.cambiarEstadoCliente(id);
     }
   //Seccion DetalleOrden
    public void crearDetalleOrden(DetalleOrden detalle){
        controlP.crearDetalleOrd(detalle);
    }
    
    public List<DetalleOrden> traerDetalleOrden(){
        return controlP.traerDetalleOrd();
    }
    
    public void borrarDetalleOrden(int id_eliminar) {
        controlP.borrarDetalleOrd(id_eliminar);
    }
    
    public DetalleOrden traerDetalleOrden(int id_editar) {
        return controlP.traerDetalleOrd(id_editar);
    }
    
    public void editarDetalleOrden(DetalleOrden detalle) {
        controlP.editarDetalleOrd(detalle);
    }
/*
    public List<DetalleOrden> listarDetallesDeOrden(int id) {
        return controlP.listarDetallesDeOrden(id);
    }*/
    
    //Seccion Orden
    public void crearOrden(Orden ord){
        controlP.crearOrden(ord);
    }
    
    public List<Orden> traerOrden(){
        return controlP.traerOrden();
    }
    
    public void borrarOrden(int id_eliminar) {
        controlP.borrarOrden(id_eliminar);
    }
    
    public Orden traerOrden(int id_editar) {
        return controlP.traerOrden(id_editar);
    }
    
    public void editarOrden(Orden ord) {
        controlP.editarOrden(ord);
    }
    
    public List<Orden> encontrarOrdenesConDetalleOrden() {
        return controlP.encontrarOrdenesConDetalleOrden();
    }
    public Orden encontrarOrdenesConDetalleOrdenPorId(int id) {
        return controlP.encontrarOrdenesConDetalleOrdenPorId(id);
    }
  
}
