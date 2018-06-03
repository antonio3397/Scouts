
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import clases.Pago_cuota;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import Negocio.Pagos;
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
    
    public void modificarPago(Long id){
        
        
    }
    public String verPagos(Long id){//id del usuario
        pagos=p2.getPagos(id);
        return "pagos.xhtml";
    }
    
}
