/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author anton
 */
@Local
public interface Usuarios {
    
    public List<Usuario> getUsuarios();
    public void registrarUsuario(Usuario u) throws CuentaExistenteException;
    public boolean estaUsuario(Usuario u);
    public void eliminarUsuario(Usuario u) throws CuentaInexistenteException;
    public Usuario buscarUsuario(Long id);
    public void modificarUsuario(Usuario u) throws CuentaExistenteException;
    
}
