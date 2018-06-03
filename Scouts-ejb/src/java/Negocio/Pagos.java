/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;
import java.util.List;
import clases.Pago_cuota;
import javax.ejb.Local;

/**
 *
 * @author Miguel
 */
@Local
public interface Pagos {
    public List<Pago_cuota> getPagos(Long id);//lista de pagos del usuario id
    public void modificarPago(Long id);//cambiar de pagado a no pagado
    public void nuevoPago(Long usuario,int precio);
    public void eliminarPago(Long id);
}
