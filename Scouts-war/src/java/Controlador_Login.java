/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Negocio.ContraseniaInvalidaException;
import Negocio.CuentaInexistenteException;
import Negocio.CuentaNoVerificadaException;
import Negocio.Login;
import Negocio.Perfiles;
import Negocio.Responsable;
import Negocio.ScoutsException;
import Negocio.Seccionesb;
import Negocio.Usuarios;
import clases.Perfil;
import clases.Responsable_Legal;
import clases.Usuario;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
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

    //Email y contraseña dada al iniciar sesion
    private String password;
    private String email;

    @EJB
    private Login log;
    
    @EJB
    private Usuarios u;
    
    @EJB
    private Perfiles perfs;
    
    @EJB
    private Seccionesb secs;
    
    @EJB
    private Responsable r;
    
    @Inject
    private MiSesion ctrl;

    @Inject
    private Control_Eventos ctrle;

    public String autenticar() {

        try {
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setContrasenia(password);
            log.compruebaLogin(usuario);
            Usuario aux = log.refrescarUsuario(usuario);
            
            if(aux.getPerfiles().getRol().equals(Perfil.Rol.EDUCANDO)){
                Date fechaactual = new Date();
                Date fechanac = aux.getFecha_nacimiento();
                LocalDate fn = fechanac.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate fa = fechaactual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Period edad = Period.between(fn, fa);
                if(edad.getYears()<=21){
                    aux.setPerfiles(perfs.getPerfil(Perfil.Rol.EDUCANDO));
                    if(secs.getSeccion(1L).getEdad_minima()<=edad.getYears()&& edad.getYears()<secs.getSeccion(1L).getEdad_maxima()){
                        aux.setSeccion(secs.getSeccion(1L));
                    } else if(secs.getSeccion(2L).getEdad_minima()<=edad.getYears()&& edad.getYears()<secs.getSeccion(2L).getEdad_maxima()){
                        aux.setSeccion(secs.getSeccion(2L));
                    } else if(secs.getSeccion(3L).getEdad_minima()<=edad.getYears()&& edad.getYears()<secs.getSeccion(3L).getEdad_maxima()){
                        aux.setSeccion(secs.getSeccion(3L));
                    } else if(secs.getSeccion(4L).getEdad_minima()<=edad.getYears()&& edad.getYears()<secs.getSeccion(4L).getEdad_maxima()){
                        aux.setSeccion(secs.getSeccion(4L));
                    } else if(secs.getSeccion(5L).getEdad_minima()<=edad.getYears()&& edad.getYears()<=secs.getSeccion(5L).getEdad_maxima()){
                        aux.setSeccion(secs.getSeccion(5L));
                    }
                    if(edad.getYears()>=18){
                        Responsable_Legal respon = aux.getResponsable();
                        respon.getUsuarios().remove(aux);
                        aux.setResponsable(null);
                        if(respon.getUsuarios().isEmpty()){
                            r.eliminarResponsable(respon);
                        } else {
                            r.modificarResponsable(respon);
                        }
                    }
                } else {
                    aux.setPerfiles(perfs.getPerfil(Perfil.Rol.SCOUTER));
                    aux.setSeccion(secs.getSeccion(5L));
                }
                u.modificarUsuario(aux);
            }
            ctrl.setUser(aux);
            List<Usuario> users = u.getUsuarios();
            ctrl.setUsers(users);
            List<Usuario> auxs = new ArrayList<>();
            if (aux.getPerfiles().getRol().equals(Perfil.Rol.COORDSEC) || aux.getPerfiles().getRol().equals(Perfil.Rol.SCOUTER)) {
                for (Usuario us : users) {
                    if (!us.equals(aux) && us.getSeccion().equals(aux.getSeccion()) && us.isVerificado()) {
                        auxs.add(us);
                    }
                }
            } else if(aux.getPerfiles().getRol().equals(Perfil.Rol.COORDGEN)) {
                for (Usuario us : users) {
                    if (!us.equals(aux) && us.isVerificado()) {
                        auxs.add(us);
                    }
                }
            }
            ctrl.setUsers2(auxs);
            List<Usuario> auxs1 = new ArrayList<>();
            if (aux.getPerfiles().getRol().equals(Perfil.Rol.COORDSEC)) {
                for (Usuario us : users) {
                    if (!us.equals(aux) && us.getSeccion().equals(aux.getSeccion())&& !us.isVerificado()) {
                        auxs1.add(us);
                    }
                }
            } else if(aux.getPerfiles().getRol().equals(Perfil.Rol.COORDGEN)) {
                for (Usuario us : users) {
                    if (!us.equals(aux) && !us.isVerificado()) {
                        auxs1.add(us);
                    }
                }
            }
            ctrl.setUsers3(auxs1);
            
            return "Inicio.xhtml";

        } catch (CuentaInexistenteException e) {
            FacesMessage fm = new FacesMessage("La cuenta no existe");
            FacesContext.getCurrentInstance().addMessage("login:user", fm);
        } catch (ContraseniaInvalidaException e) {
            FacesMessage fm = new FacesMessage("La contraseña no es correcta");
            FacesContext.getCurrentInstance().addMessage("login:pass", fm);
        } catch (CuentaNoVerificadaException e) {
            FacesMessage fm = new FacesMessage("Cuenta no verificada");
            FacesContext.getCurrentInstance().addMessage(null, fm);
        } catch (ScoutsException e) {
            FacesMessage fm = new FacesMessage("Error: " + e);
            FacesContext.getCurrentInstance().addMessage(null, fm);
        } 
        return null;
        
        
        /*Iterator<Usuario> iter = users.iterator();

        boolean pV = false, exist = false;
        Usuario aux = null;

        while (iter.hasNext() && !exist) {
            aux = iter.next();

            if (aux.getEmail().equals(getEmail())) {
                exist = true;
                if (aux.getContrasenia().equals(getPassword())) {
                    pV = true;
                }
            }
        }

        if (!exist) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email no registrado", "Email no registrado"));
            return null;
        }
        if (!pV) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password incorrecto", "Password incorrecto"));
            return null;
        }
        ctrl.setUsers(users);
        ctrl.setUser(aux);

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
        ctrl.setUsers2(auxs);

        ctrle.setEventosj(events);
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

        ctrle.setEventosj2(events2);

        return "Inicio.xhtml";*/
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
