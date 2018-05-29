
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

    @PostConstruct
    public void init() {/*
        notificame = new ArrayList<>();            
        notificame.add(new Notificacion(new NotificacionID(121L,123L), "Recordatorio","¡Ya mismo se realiza la excursión! Prepararos bien con ropa cómoda, comida para la merienda y agua. La paella la ponemos nosotros ;)",new Date(2018-1900,3,25,8,30)));
        notificame.add(new Notificacion(new NotificacionID(125L,124L), "Viaje al monte 2","Segundo viaje a Códoba",new Date(2018-1900,3,22,10,30)));
        notificame.add(new Notificacion(new NotificacionID(121L,125L), "Salvamento en EEUU suspendido","El viaje a EEUU para salvar a las ardillas ha sido suspendido debido al mal tiempo. ¡Lo sentimos muchísimo! Estamos preparando otro día para realizar este viaje.",new Date(2018-1900,3,24,9,30)));
        
        // Para secciones
        notificame.add(new Notificacion(new NotificacionID(1L,125L), "Comunicación para educandos de una sección","Mensaje de prueba para los educandos de la sección: Castores.",new Date(2018-1900,3,24,9,30)));
    */}

    public List<Notificacion> buscarNotificaciones() throws NotificacionException{
        List<Notificacion> misNotificaciones = new ArrayList<>();
        Long miID = getLogin().getUser().getId();
        for(Notificacion noti: notificame){
            if(noti.getId().getUsuario_id().equals(miID)){  // Va entrando las notificaciones de Eventos
                misNotificaciones.add(noti);
            } else if(noti.getId().getUsuario_id().equals(getLogin().getUser().getSeccion().getId())){  // Va entrando las notificaciones de la Seccion
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
