/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Usuario;
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
public class LoginImpl implements Login {

    @PersistenceContext(unitName = "Scouts-EntidadesPU")
    private EntityManager em;
    
    @Override
    public void compruebaLogin(Usuario u) throws ScoutsException {
        
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email LIKE '"+u.getEmail()+"'");
        List<Usuario> users = q.getResultList();
        Usuario user = users.get(0);
        
        if (user == null) {
            throw new CuentaInexistenteException();
        }

        if (!user.getContrasenia().equals(u.getContrasenia())) {
            throw new ContraseniaInvalidaException();
        }
    }
    
    @Override
    public Usuario refrescarUsuario(Usuario u) throws ScoutsException {
        compruebaLogin(u);
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email LIKE '"+u.getEmail()+"'");
        List<Usuario> users = q.getResultList();
        Usuario user = users.get(0);
        em.refresh(user);
        return user;

    }
}
