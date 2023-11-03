package Modelo;

import Modelo.Cliente;
import Modelo.DetalleOrden;
import Modelo.Pago;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-10-11T19:01:08")
@StaticMetamodel(Orden.class)
public class Orden_ { 

    public static volatile SingularAttribute<Orden, Cliente> cli;
    public static volatile SingularAttribute<Orden, String> estado;
    public static volatile SingularAttribute<Orden, Double> monto;
    public static volatile SingularAttribute<Orden, Pago> idpago;
    public static volatile ListAttribute<Orden, DetalleOrden> detallesOrden;
    public static volatile SingularAttribute<Orden, Integer> id;
    public static volatile SingularAttribute<Orden, Date> createAt;

}