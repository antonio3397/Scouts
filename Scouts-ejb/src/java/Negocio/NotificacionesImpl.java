/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Evento;
import clases.Notificacion;
import clases.NotificacionID;
import clases.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author migue
 */
@Stateless
public class NotificacionesImpl implements Notificaciones {

    @PersistenceContext(unitName = "Scouts-EntidadesPU")
    private EntityManager em;

    @Override
    public List<Notificacion> Notificaciones() {
        return em.createQuery("SELECT n from Notificacion n").getResultList();
    }

    @Override
    public List<Notificacion> buscarNotificaciones(Usuario user) throws NotificacionesException {
        return em.createQuery("SELECT n FROM Notificacion n WHERE n.usuario.id = '" + user.getId() + "'").getResultList();
    }

    @Override
    public Notificacion buscarNotificacion(NotificacionID id) {
        return em.find(Notificacion.class, id);
    }

    @Override
    public void crearNotificacion(Notificacion n) {
        em.persist(n);
    }

    @Override
    public void borrarNotificacion(Notificacion n) {
        em.remove(em.merge(n));
    }

}
