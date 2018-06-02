/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Negocio.CuentaExistenteException;
import Negocio.PerfilInexistenteException;
import Negocio.Perfiles;
import Negocio.Responsable;
import Negocio.ScoutsException;
import Negocio.SeccionInexistenteException;
import Negocio.Seccionesb;
import Negocio.Usuarios;
import clases.Evento;
import clases.Perfil;
import clases.Responsable_Legal;
import clases.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/*
*/
/**
 *
 * @author DavidDR
 */
@Named(value = "miSesion")
@SessionScoped
public class MiSesion implements Serializable {

    //Usuario que ha iniciado sesion
    private Usuario user;
    //Lista de todos los usuarios en la base de datos
    private List<Usuario> users;
    //Lista con los usuarios de la seccion del usuario que ha iniciado sesion
    private List<Usuario> users2;
    //Usuario a modificar
    private Usuario otro;
    private String seccionmod;
    //Usuario que se quiere ver
    private Usuario auxiliar;
    //Mira si es necesario un responsable legal
    private boolean necesario;

    //Usuario que se va a crear
    private Usuario usercrear = new Usuario();
    private String seccioncrear;
    private String perfilcrear="";

    @EJB
    private Usuarios u;
    
    @EJB
    private Perfiles perfs;
    
    @EJB
    private Seccionesb secs;
    
    @EJB
    private Responsable r;
    
    @Inject
    private Control_Responsable res;

    public String logout() {
        // Destruye la sesión (y con ello, el ámbito de este bean)
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getExternalContext().invalidateSession();
        setUser(null);
        return "login.xhtml";
    }

    public String modificarboton() {

        otro = auxiliar;
        
        return "ModPerf.xhtml";
    }

    public String aceptarmod() throws ScoutsException {

        switch (seccionmod) {
            case "Castores":
                otro.setSeccion(secs.getSeccion(1L));
                break;
            case "Lobatos":
                otro.setSeccion(secs.getSeccion(2L));
                break;
            case "Scouts":
                otro.setSeccion(secs.getSeccion(3L));
                break;
            case "Escultas":
                otro.setSeccion(secs.getSeccion(4L));
                break;
            case "Rovers":
                otro.setSeccion(secs.getSeccion(5L));
                break;
            default:
                break;
        }
        seccionmod = null;

        u.modificarUsuario(otro);

        return "Lista_Usuarios.xhtml";
    }
    
    public String cancerlarMod () throws ScoutsException {
        seccionmod = null;
        return "Lista_Usuarios.xhtml";
    }

    public String borrarUsuario(Long id) throws ScoutsException {

        Usuario b = u.buscarUsuario(id);
        if(b.getResponsable()!=null){
            Responsable_Legal respon = b.getResponsable();
            respon.getUsuarios().remove(b);
            if(respon.getUsuarios().isEmpty()){
                u.eliminarUsuario(b);
                r.eliminarResponsable(respon);
                users=u.getUsuarios();
                refrescarUsers2();
            } else {
                u.eliminarUsuario(b);
                r.modificarResponsable(respon);
                users=u.getUsuarios();
                refrescarUsers2();
            }
        } else {
            u.eliminarUsuario(b);
            users=u.getUsuarios();
            refrescarUsers2();
        }
        
        return "Lista_Usuarios.xhtml";
    }

    public String verUsuario(Long id) {

        auxiliar=u.buscarUsuario(id);

        return "OtroPerfil.xhtml";
    }
    
    public String inscribirse(Evento e) throws EventoException{
        user.getEventos().add(e);
        e.getUsuarios().add(user);
        
        return "Eventos.xhtml";
    }

    public boolean isCoordGen() {
        return this.getUser().getPerfiles().getRol().equals(Perfil.Rol.COORDGEN);
    }

    public boolean isCordSec() {
        return this.getUser().getPerfiles().getRol().equals(Perfil.Rol.COORDSEC);
    }

    public boolean isScouter() {
        return this.getUser().getPerfiles().getRol().equals(Perfil.Rol.SCOUTER);
    }

    public boolean isEducando() {
        return this.getUser().getPerfiles().getRol().equals(Perfil.Rol.EDUCANDO);
    }

    public String getSeccion() {
        String salida = "";
        switch (getUser().getSeccion().getNombre()) {
            case Castores:
                salida = "Castores";
                break;
            case Escultas_Pioneros:
                salida = "Escultas";
                break;
            case Lobatos:
                salida = "Lobatos";
                break;
            case Rovers_Compañeros:
                salida = "Rovers";
                break;
            case Tropa_Scout:
                salida = "Scouts";
                break;
            default:
                break;
        }

        return salida;
    }
    
    public String necesarioResponsable() throws CuentaExistenteException, PerfilInexistenteException, SeccionInexistenteException{
        if(necesario){
            switch (perfilcrear) {
            case "CoordGen":
                usercrear.setPerfiles(perfs.getPerfil(Perfil.Rol.COORDGEN));
                break;
            case "CoordSec":
                usercrear.setPerfiles(perfs.getPerfil(Perfil.Rol.COORDSEC));
                break;
            case "Scouter":
                usercrear.setPerfiles(perfs.getPerfil(Perfil.Rol.SCOUTER));
                break;
            case "Educando":
                usercrear.setPerfiles(perfs.getPerfil(Perfil.Rol.EDUCANDO));
                break;
            default:
                break;
            }

            if (perfilcrear.equals("CoordGen")) {
                usercrear.setSeccion(secs.getSeccion(0L));
            } else {
                switch (seccioncrear) {
                    case "Castores":
                        usercrear.setSeccion(secs.getSeccion(1L));
                        break;
                    case "Lobatos":
                        usercrear.setSeccion(secs.getSeccion(2L));
                        break;
                    case "Scouts":
                        usercrear.setSeccion(secs.getSeccion(3L));
                        break;
                    case "Escultas":
                        usercrear.setSeccion(secs.getSeccion(4L));
                        break;
                    case "Rovers":
                        usercrear.setSeccion(secs.getSeccion(5L));
                        break;
                    default:
                        break;
                }
            }

            Date fechaactual = new Date();
            usercrear.setFecha_ingreso(fechaactual);
            Long idcrear = users.get(users.size()-1).getId()+1;
            usercrear.setId(idcrear);
            res.setCrearuser(usercrear);
            return "crearResponsable.xhtml";
        } else {
            return crearUsuario();
        }
    }

    public String crearUsuario() throws CuentaExistenteException, PerfilInexistenteException, SeccionInexistenteException {

        switch (perfilcrear) {
            case "CoordGen":
                usercrear.setPerfiles(perfs.getPerfil(Perfil.Rol.COORDGEN));
                break;
            case "CoordSec":
                usercrear.setPerfiles(perfs.getPerfil(Perfil.Rol.COORDSEC));
                break;
            case "Scouter":
                usercrear.setPerfiles(perfs.getPerfil(Perfil.Rol.SCOUTER));
                break;
            case "Educando":
                usercrear.setPerfiles(perfs.getPerfil(Perfil.Rol.EDUCANDO));
                break;
            default:
                break;
        }

        if (perfilcrear.equals("CoordGen")) {
            usercrear.setSeccion(secs.getSeccion(0L));
            
        } else {
            switch (seccioncrear) {
                case "Castores":
                    usercrear.setSeccion(secs.getSeccion(1L));
                    break;
                case "Lobatos":
                    usercrear.setSeccion(secs.getSeccion(2L));
                    break;
                case "Scouts":
                    usercrear.setSeccion(secs.getSeccion(3L));
                    break;
                case "Escultas":
                    usercrear.setSeccion(secs.getSeccion(4L));
                    break;
                case "Rovers":
                    usercrear.setSeccion(secs.getSeccion(5L));
                    break;
                default:
                    break;
            }
        }

        Date fechaactual = new Date();
        usercrear.setFecha_ingreso(fechaactual);
        Long idcrear = users.get(users.size()-1).getId()+1;
        usercrear.setId(idcrear);

        u.registrarUsuario(usercrear);
        users = u.getUsuarios();
        refrescarUsers2();
        usercrear=new Usuario();
        perfilcrear = "";
        seccioncrear = null;

        return "Lista_Usuarios.xhtml";
    }

    public boolean perfCrearGeneral() {
        return perfilcrear.equals("CoordGen");
    }
    
    public void refrescarUsers2(){
        List<Usuario> auxs = new ArrayList<>();
        if (user.getPerfiles().getRol().equals(Perfil.Rol.COORDSEC) || user.getPerfiles().getRol().equals(Perfil.Rol.SCOUTER)) {
            for (Usuario u : users) {
                if (!u.equals(user) && u.getSeccion().equals(user.getSeccion())) {
                    auxs.add(u);
                }
            }
        } else {
            for (Usuario u : users) {
                if (!u.equals(user)) {
                    auxs.add(u);
                }
            }
        }
        users2 = auxs;
    }
    
    public String verSexo(){
        if(otro.getSexo().equals("Masc")){
            return "Fem";
        } else {
            return "Masc";
        }
    }
    
    public int comprobarMetodo(){
        if(otro.getMetodo_pago().equals("Tarjeta")){
            return 1;
        } else if (otro.getMetodo_pago().equals("Efectivo")){
            return 2;
        } else {
            return 3;
        }
    }
    public boolean tieneResponsable(Usuario us){
        return us.getResponsable()!=null;
    }
    
    public String salidaResponsable(Usuario us){
        if(tieneResponsable(us)){
            return us.getNIF();
        } else {
            return "No tiene responsable legal";
        }
    }
    
    public String cambioFormato(Date fecha){
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        return formateador.format(fecha);
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

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    /**
     * @return the users2
     */
    public List<Usuario> getUsers2() {
        return users2;
    }

    /**
     * @param users2 the users2 to set
     */
    public void setUsers2(List<Usuario> users2) {
        this.users2 = users2;
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
     * @return the seccionmod
     */
    public String getSeccionmod() {
        return seccionmod;
    }

    /**
     * @param seccionmod the seccionmod to set
     */
    public void setSeccionmod(String seccionmod) {
        this.seccionmod = seccionmod;
    }
    
    /**
     * @return the auxiliar
     */
    public Usuario getAuxiliar() {
        return auxiliar;
    }

    /**
     * @param auxiliar the auxiliar to set
     */
    public void setAuxiliar(Usuario auxiliar) {
        this.auxiliar = auxiliar;
    }

    public Usuarios getU() {
        return u;
    }

    public void setU(Usuarios u) {
        this.u = u;
    }

    public Perfiles getPerfs() {
        return perfs;
    }

    public void setPerfs(Perfiles perfs) {
        this.perfs = perfs;
    }

    public Seccionesb getSecs() {
        return secs;
    }

    public void setSecs(Seccionesb secs) {
        this.secs = secs;
    }

    public Usuario getUsercrear() {
        return usercrear;
    }

    public void setUsercrear(Usuario usercrear) {
        this.usercrear = usercrear;
    }

    public String getSeccioncrear() {
        return seccioncrear;
    }

    public void setSeccioncrear(String seccioncrear) {
        this.seccioncrear = seccioncrear;
    }

    public String getPerfilcrear() {
        return perfilcrear;
    }

    public void setPerfilcrear(String perfilcrear) {
        this.perfilcrear = perfilcrear;
    }

    public boolean isNecesario() {
        return necesario;
    }

    public void setNecesario(boolean necesario) {
        this.necesario = necesario;
    }

}
