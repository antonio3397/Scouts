/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Evento;
import clases.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author anton
 */
@Local
public interface Negocio {
    
    public void compruebaLogin(Usuario u) throws ScoutsException;
    public Usuario refrescarUsuario(Usuario u) throws ScoutsException;
    public void compruebaEvento(Evento e) throws ScoutsException;
    public List<Evento> getEventos();
    public void eliminarEvento(Evento e) throws ScoutsException;
    public List<Usuario> getUsuarios();
    public void crearEvento(Evento e) throws ScoutsException;
    
}
