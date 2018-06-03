
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Negocio.Notificaciones;
import Negocio.NotificacionesException;
import Negocio.Usuarios;
import clases.Evento;
import clases.Notificacion;
import clases.NotificacionID;
import clases.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author anton
 */
@Named(value = "notificaciones")
@SessionScoped
public class Control_Notificaciones implements Serializable {
    
    @Inject
    private MiSesion login;
    
    @EJB
    private Notificaciones notif;
    
    @EJB
    private Usuarios users;

    /**
     * @return the login
     */
    public MiSesion getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(MiSesion login) {
        this.login = login;
    }
    
    @PostConstruct
    public void init() {
    }
    
    public List<Notificacion> buscarNotificaciones() throws NotificacionException, NotificacionesException {
        return notif.buscarNotificaciones(login.getUser());
    }
    
    public Notificacion buscarNotificacion(NotificacionID id) throws NotificacionException {
        Notificacion n = notif.buscarNotificacion(id);
        if (n == null) {
            throw new NotificacionException("Notificacion no encontrada");
        }
        return n;
    }
    
    public String borrarNotificacion(NotificacionID id) throws NotificacionException {
        Notificacion b = buscarNotificacion(id);
        notif.borrarNotificacion(b);
        return "Lista_notificaciones.xhtml";
    }
    
    public void crearNotificacion(Evento ev) {
        for (Usuario u : users.getUsuarios()) {
            if (u.getSeccion().equals(ev.getSeccion())) {
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
                notif.crearNotificacion(n);
            }
        }
    }
    
    public void borrarNotificacionesEvento(Evento ev) {
        for (Notificacion n : notif.Notificaciones()) {
            if (n.getEvento().equals(ev)) {
                notif.borrarNotificacion(n);
            }
        }
    }
}
