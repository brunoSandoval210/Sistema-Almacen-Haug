package Modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DetalleOrden implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int item;
    @OneToMany(mappedBy = "ord")
    private List<Producto> listaProducto;
    private String nombreOrd;
    private String imagen;
    private String descripcion;
    private double precioOrden;
    private int cantidad;
    private double subtotal;

    @Column(name = "FECHA_DE_ALTA")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }
    @ManyToOne
    @JoinColumn(name = "ID_ORDEN")
    private Orden orden;
    

    public DetalleOrden() {
    }

    
    public DetalleOrden(int id, int item, List<Producto> listaProducto, String nombreOrd, String imagen, String descripcion, double precioOrden, int cantidad, double subtotal, Date createAt, Orden orden) {
        this.id = id;
        this.item = item;
        this.listaProducto = listaProducto;
        this.nombreOrd = nombreOrd;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precioOrden = precioOrden;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.createAt = createAt;
        this.orden = orden;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public String getNombreOrd() {
        return nombreOrd;
    }

    public void setNombreOrd(String nombreOrd) {
        this.nombreOrd = nombreOrd;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioOrden() {
        return precioOrden;
    }

    public void setPrecioOrden(double precioOrden) {
        this.precioOrden = precioOrden;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    

}
