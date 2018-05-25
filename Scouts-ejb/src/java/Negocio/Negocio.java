/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Usuario;
import javax.ejb.Local;

/**
 *
 * @author anton
 */
@Local
public interface Negocio {
    
    public void compruebaLogin(Usuario u) throws ScoutsException;
    public Usuario refrescarUsuario(Usuario u) throws ScoutsException;
    
}
