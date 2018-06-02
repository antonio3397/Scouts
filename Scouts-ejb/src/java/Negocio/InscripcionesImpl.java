/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Evento;
import clases.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author DavidDR
 */
@Stateless
public class InscripcionesImpl implements Inscripciones {

    @PersistenceContext(unitName = "Scouts-EntidadesPU")
    private EntityManager em;

    @Override
    public List<Evento> inscripciones(Long id) {
        return em.createQuery("SELECT e FROM EVENTO e, INSCRIPCION i WHERE i.ID_EVENTO = e.id AND i.ID_USUARIO = '" + id + "'").getResultList();
    }

    @Override
    public void inscribirse(Evento ev, Usuario user) {
        List<Evento> inscripciones = user.getEventos();
        inscripciones.add(ev);
        em.persist(user);
        em.merge(user);
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
