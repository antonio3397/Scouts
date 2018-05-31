/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Seccion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author anton
 */
@Local
public interface Seccionesb {
    
    public List<Seccion> getSecciones();
    public Seccion getSeccion(Long id) throws SeccionInexistenteException;
    
}
