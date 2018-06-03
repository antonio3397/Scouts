
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import clases.Pago_cuota;
import clases.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import Negocio.Pagos;
import java.util.Date;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Miguel
 */

@Named(value = "control_Pagos")
@SessionScoped
public class Control_Pagos implements Serializable{
    private List <Pago_cuota> pagos;
    @EJB
    private Pagos p2;
    private String tipoCrear;
    private Integer precioCrear;
    private Date fechaCrear;

    public void setTipoCrear(String tipoCrear) {
        this.tipoCrear = tipoCrear;
    }

    public void setPrecioCrear(Integer precioCrear) {
        this.precioCrear = precioCrear;
    }

    public void setFechaCrear(Date fechaCrear) {
        this.fechaCrear = fechaCrear;
    }
    private Usuario usCrear;
    
    public void nuevoPago(){
        Pago_cuota p=new Pago_cuota();
        p.setId(p2.idMax());
        p.setCantidad_pagada(precioCrear);
        p.setFecha_del_pago(fechaCrear);
        p.setTipo_pago(tipoCrear);
        p.setUsuarios(usCrear);
        p2.nuevoPago(p);
        
    }
    public void modificarPago(Long id){
        
        
    }
    public String verPagos(Long id){//id del usuario
        pagos=p2.getPagos(id);
        return "pagos.xhtml";
    }
    
}
