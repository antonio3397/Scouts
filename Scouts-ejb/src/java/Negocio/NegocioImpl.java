/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author anton
 */
@Stateless
public class NegocioImpl implements Negocio {
    
    @PersistenceContext(unitName = "Scouts-Entidades")
    private EntityManager em;

    @Override
    public void compruebaLogin(Usuario u) throws ScoutsException {
        Usuario user = em.find(Usuario.class, u.getId());
        
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
        Usuario user = em.find(Usuario.class, u.getId());
        em.refresh(user);
        return user;

    }
    
}
