package Modelo;

import Modelo.Orden;
import Modelo.Producto;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-10-11T19:01:08")
@StaticMetamodel(DetalleOrden.class)
public class DetalleOrden_ { 

    public static volatile SingularAttribute<DetalleOrden, String> descripcion;
    public static volatile SingularAttribute<DetalleOrden, Integer> item;
    public static volatile SingularAttribute<DetalleOrden, Double> subtotal;
    public static volatile ListAttribute<DetalleOrden, Producto> listaProducto;
    public static volatile SingularAttribute<DetalleOrden, String> nombreOrd;
    public static volatile SingularAttribute<DetalleOrden, String> imagen;
    public static volatile SingularAttribute<DetalleOrden, Double> precioOrden;
    public static volatile SingularAttribute<DetalleOrden, Integer> id;
    public static volatile SingularAttribute<DetalleOrden, Integer> cantidad;
    public static volatile SingularAttribute<DetalleOrden, Orden> orden;
    public static volatile SingularAttribute<DetalleOrden, Date> createAt;

}