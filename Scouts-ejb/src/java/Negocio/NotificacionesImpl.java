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
    public List<Notificacion> buscarNotificaciones(Usuario user) throws NotificacionesException {
        return em.createQuery("SELECT n FROM Notificacion n WHERE n.usuario.id = '" + user.getId() + "'").getResultList();
    }

    @Override
    public Notificacion buscarNotificacion(NotificacionID id) {
        return em.find(Notificacion.class, id);
    }

    @Override
    public void borrarNotificacion(NotificacionID id) {
        Query q = em.createQuery("DELETE FROM Notificacion n WHERE n.id = " + id);
        q.executeUpdate();
        em.remove(em.merge(id));
    }

    @Override
    public void crearNotificacion(Evento ev) {
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.seccion = '" + ev.getSeccion() + "'");
        List<Usuario> users = q.getResultList();
        for (Usuario u : users) {
            NotificacionID id = new NotificacionID();
            id.setEvento_id(ev.getId());
            id.setEvento_id(u.getId());
            Notificacion n = new Notificacion();
            n.setEvento(ev);
            n.setFecha(ev.getFecha());
            n.setId(id);
            n.setTitulo("Nuevo evento");
            n.setUsuario(u);
            n.setTexto("Se ha creado el evento " + ev.getTitulo());
            em.persist(n);
        }
    }

}
