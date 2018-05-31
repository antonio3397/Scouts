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
import javax.ejb.Local;

/**
 *
 * @author DavidDR
 */
@Local
public interface Notificaciones {

    public List<Notificacion> buscarNotificaciones(Usuario user) throws NotificacionesException;
    public Notificacion buscarNotificacion (NotificacionID id);
    public void borrarNotificacion(NotificacionID id);
    public void crearNotificacion (Evento ev);
}
