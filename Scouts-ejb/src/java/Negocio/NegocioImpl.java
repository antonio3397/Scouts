/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Evento;
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
public class NegocioImpl implements Negocio {
    
    @PersistenceContext(unitName = "Scouts-Entidades")
    private EntityManager em;

    @Override
    public void compruebaLogin(Usuario u) throws ScoutsException {
        Usuario user = em.find(Usuario.class, u.getEmail());
        
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
        Usuario user = em.find(Usuario.class, u.getEmail());
        em.refresh(user);
        return user;
    }
    
    @Override
    public List<Usuario> getUsuarios(){
        List<Usuario> u = new ArrayList<>();
        Query q = em.createQuery("SELECT u FROM Usuario u");
        u=q.getResultList();
        return u;
    }
    
    @Override
    public void compruebaEvento(Evento e) throws ScoutsException {
        Evento event = em.find(Evento.class, e.getId());
        
        if (event == null) {
            throw new EventoInexistenteException();
        }
    }
    
    @Override
    public List<Evento> getEventos(){
        List<Evento> e = new ArrayList<>();
        Query q = em.createQuery("SELECT e FROM Evento e");
        e=q.getResultList();
        return e;
    }
    
    @Override
    public void eliminarEvento(Evento e) throws ScoutsException {
        compruebaEvento(e);
        em.remove(em.merge(e));
    }
    
    @Override
    public void crearEvento(Evento e) throws ScoutsException {
        compruebaEvento(e);
        em.persist(e);
    }
    
}
