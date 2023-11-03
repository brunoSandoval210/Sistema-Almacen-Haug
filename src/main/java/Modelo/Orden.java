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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Orden implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="ID_ORDEN_CLIENTE")
    private Cliente cli;
    @OneToOne
    @JoinColumn(name="ID_ORDEN_PAGO")
    private Pago idpago;
    @Column(name="FECHA_DE_ORDEN")
    @Temporal(TemporalType.DATE)
    private Date createAt;
    private double monto;
    private String estado;
    @OneToOne
    
    @OneToMany(mappedBy = "orden")
    private List<DetalleOrden> detallesOrden; 
    
    @PrePersist
    public void prePersist() {
	createAt = new Date();
    }

    public Orden() {
    }

    public Orden(int id, Cliente cli, Pago idpago, Date createAt, double monto, String estado, List<DetalleOrden> detallesOrden) {
        this.id = id;
        this.cli = cli;
        this.idpago = idpago;
        this.createAt = createAt;
        this.monto = monto;
        this.estado = estado;
        this.detallesOrden = detallesOrden;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCli() {
        return cli;
    }

    public void setCli(Cliente cli) {
        this.cli = cli;
    }

    public Pago getIdpago() {
        return idpago;
    }

    public void setIdpago(Pago idpago) {
        this.idpago = idpago;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetalleOrden> getDetallesOrden() {
        return detallesOrden;
    }

    public void setDetallesOrden(List<DetalleOrden> detallesOrden) {
        this.detallesOrden = detallesOrden;
    }

    

    
    
}
