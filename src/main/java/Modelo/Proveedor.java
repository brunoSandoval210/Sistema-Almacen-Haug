package Modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Proveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private int telefono;
    private int ruc;

    @Column(name = "FECHA_DE_ALTA")
    @Temporal(TemporalType.DATE)
    private Date createAt;
    
    @OneToMany(mappedBy = "prov")
    private List<Producto> listaProducto;
    
    private String estado;
    
    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }

    public Proveedor() {
    }

    public Proveedor(int id, String nombre, int telefono, int ruc, Date createAt, List<Producto> listaProducto, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.ruc = ruc;
        this.createAt = createAt;
        this.listaProducto = listaProducto;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getRuc() {
        return ruc;
    }

    public void setRuc(int ruc) {
        this.ruc = ruc;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
