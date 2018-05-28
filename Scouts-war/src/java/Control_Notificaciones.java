
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import clases.Notificacion;
import clases.NotificacionID;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author anton
 */
@Named(value="notificaciones")
@SessionScoped
public class Control_Notificaciones implements Serializable{

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

    private List<Notificacion> notificame;
    
    @Inject
    private MiSesion login;

    public List<Notificacion> buscarNotificaciones() throws NotificacionException{
        List<Notificacion> misNotificaciones = new ArrayList<>();
        String miEmail = getLogin().getUser().getEmail();
        for(Notificacion noti: notificame){
            if(noti.getId().getUsuario_email().equals(miEmail)){  // Va entrando las notificaciones de Eventos
                misNotificaciones.add(noti);
            } else if(noti.getId().getUsuario_email().equals(getLogin().getUser().getSeccion().getId())){  // Va entrando las notificaciones de la Seccion
                misNotificaciones.add(noti);
            }
        }
        return misNotificaciones;
    }
    
    public Notificacion buscarNotificacion(NotificacionID id) throws NotificacionException{
        Notificacion enc = null;
        for(Notificacion e : notificame){
            if(e.getId().equals(id)){
                enc=e;
            }
        }
        if(enc == null){
            throw new NotificacionException("Notificion no encontrada");
        }
        return enc;
    }
    
    public String borrarNotificacion(NotificacionID id) throws NotificacionException{
        Notificacion b = buscarNotificacion(id);
        notificame.remove(b);
        return "Lista_notificaciones.xhtml";
    }
    
    public List<Notificacion> getNotificame() {
        return notificame;
    }

    public void setNotificame(List<Notificacion> notificame) {
        this.notificame = notificame;
    }
    
    public void addNotificame(Notificacion n){
        notificame.add(n);
    }
}
