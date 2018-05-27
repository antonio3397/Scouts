/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Negocio.ContraseniaInvalidaException;
import Negocio.CuentaInexistenteException;
import Negocio.Negocio;
import Negocio.ScoutsException;
import clases.Evento;
import clases.Perfil;
import clases.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author DavidDR
 */
@Named(value = "controlador_Login")
@RequestScoped
public class Controlador_Login implements Serializable {

    //Lista de usuarios
    private List<Usuario> users;
    //Lista de eventos
    private List<Evento> events;
    //Usuario que se quiere ver o editar
    private Usuario otro;
    //Usuario que inicia la sesión
    private Usuario usuario;

    @Inject
    private Negocio negocio;
    
    @Inject
    private MiSesion ctrl;

    @Inject
    private Control_Eventos ctrle;
    
    public Controlador_Login(){
        usuario= new Usuario();
        otro= new Usuario();
        users= new ArrayList<>();
        events= new ArrayList<>();
    }
    
    //Inicio de sesion
    public String autenticar() {

        try {
            negocio.compruebaLogin(usuario);
            Usuario aux = negocio.refrescarUsuario(usuario);
            ctrl.setUser(aux);
            users=negocio.getUsuarios();
            ctrl.setUsers(users);
            events=negocio.getEventos();
            ctrle.setEventosj(events);
            List<Usuario> auxs = new ArrayList<>();
            if (aux.getPerfiles().getRol().equals(Perfil.Rol.COORDSEC) || aux.getPerfiles().getRol().equals(Perfil.Rol.SCOUTER)) {
                for (Usuario u : users) {
                    if (!u.equals(aux) && u.getSeccion().equals(aux.getSeccion())) {
                        auxs.add(u);
                    }
                }
            } else {
                for (Usuario u : users) {
                    if (!u.equals(aux)) {
                        auxs.add(u);
                    }
                }
            }
            //Lista de usuarios que puede ver el usuario conectado dada su seccion
            ctrl.setUsers2(auxs);
            
            List<Evento> events2 = new ArrayList<>();
            if (aux.getPerfiles().getRol().equals(Perfil.Rol.COORDGEN)) {
                for (Evento e : events) {
                    events2.add(e);
                }
            } else {
                for (Evento e : events) {
                    if (aux.getSeccion().equals(e.getSeccion())) {
                        events2.add(e);
                    }
                }
            }
            //Lista de eventos que puede ver el usuario conectado dada su seccion
            ctrle.setEventosj2(events2);
            
            return "Inicio.xhtml";
        } catch (CuentaInexistenteException e) {
            FacesMessage fm = new FacesMessage("Email no registrado");
            FacesContext.getCurrentInstance().addMessage("login:user", fm);
        } catch (ContraseniaInvalidaException e) {
            FacesMessage fm = new FacesMessage("La contraseña no es correcta");
            FacesContext.getCurrentInstance().addMessage("login:pass", fm);
        } catch (ScoutsException e) {
            FacesMessage fm = new FacesMessage("Error: " + e);
            FacesContext.getCurrentInstance().addMessage(null, fm);
        }
        return null;
    }

    //Devuelve el usuario que quieres ver
    public String verUsuario(String email) {

        Iterator<Usuario> iter = users.iterator();
        Usuario u = iter.next();
        while(iter.hasNext() && !email.equals(u.getEmail())) {
            u = iter.next();
        }
        if (email.equals(u.getEmail())) {
            otro = u;
        }

        return "OtroPerfil.xhtml";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    /**
     * @return the users
     */
    public List<Usuario> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<Usuario> users) {
        this.users = users;
    }

    /**
     * @return the otro
     */
    public Usuario getOtro() {
        return otro;
    }

    /**
     * @param otro the otro to set
     */
    public void setOtro(Usuario otro) {
        this.otro = otro;
    }

    /**
     * @return the ctrl
     */
    public MiSesion getCtrl() {
        return ctrl;
    }

    /**
     * @param ctrl the ctrl to set
     */
    public void setCtrl(MiSesion ctrl) {
        this.ctrl = ctrl;
    }

    /**
     * @return the events
     */
    public List<Evento> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(List<Evento> events) {
        this.events = events;
    }

    /**
     * @return the ctrle
     */
    public Control_Eventos getCtrle() {
        return ctrle;
    }

    /**
     * @param ctrle the ctrle to set
     */
    public void setCtrle(Control_Eventos ctrle) {
        this.ctrle = ctrle;
    }
}
