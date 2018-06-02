/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Responsable_Legal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author anton
 */
@Local
public interface Responsable {
    
    public Responsable_Legal getResponsable(Long id) throws ResponsableInexistenteException;
    public void registrarResponsable(Responsable_Legal r) throws ResponsableExistenteException;
    public boolean estaResponsable(Responsable_Legal r);
    public boolean estaResponsableNIF(Responsable_Legal r);
    public Responsable_Legal refrescarResponsable(Responsable_Legal r);
    public void modificarResponsable(Responsable_Legal r) throws ResponsableExistenteException;
    public List<Responsable_Legal> getResponsables();
    public void eliminarResponsable(Responsable_Legal r) throws ResponsableInexistenteException;
    
}
