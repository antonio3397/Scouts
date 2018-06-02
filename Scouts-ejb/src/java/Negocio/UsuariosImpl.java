/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Seccion;
import clases.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author anton
 */
@Stateless
public class UsuariosImpl implements Usuarios {

    @PersistenceContext(unitName = "Scouts-EntidadesPU")
    private EntityManager em;
    
    @Override
    public List<Usuario> getUsuarios(){
        List<Usuario> users = new ArrayList<>();
        Query q = em.createQuery("SELECT u FROM Usuario u");
        users = q.getResultList();
        return users;
    }
    
    @Override
    public void registrarUsuario(Usuario u) throws CuentaExistenteException{
        if(!estaUsuario(u)){
            em.persist(u);
        } else {
            throw new CuentaExistenteException();
        }
    }
    
    @Override
    public void modificarUsuario(Usuario u) throws CuentaExistenteException{
        if(estaUsuario(u)){
            em.merge(u);
        } else {
            throw new CuentaExistenteException();
        }
    }
    
    @Override
    public void eliminarUsuario(Usuario u) throws CuentaInexistenteException{
        if(estaUsuario(u)){
            em.remove(em.merge(u));
        } else {
            throw new CuentaInexistenteException();
        }
        
    }
    
    @Override
    public boolean estaUsuario(Usuario u){
        return em.find(Usuario.class, u.getId())!=null;
    }
    
    @Override
    public Usuario buscarUsuario(Long id){
        return em.find(Usuario.class, id);
    }
    
    @Override
    public Usuario refrescarUsuario(Usuario u) throws ScoutsException {
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email LIKE '"+u.getEmail()+"'");
        List<Usuario> users = q.getResultList();
        Usuario user = users.get(0);
        em.refresh(user);
        return user;
    }
    
}
