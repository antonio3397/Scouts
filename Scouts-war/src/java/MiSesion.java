/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Negocio.CuentaExistenteException;
import Negocio.CuentaInexistenteException;
import Negocio.Perfiles;
import Negocio.Seccionesb;
import Negocio.Usuarios;
import clases.Evento;
import clases.Perfil;
import clases.Seccion;
import clases.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

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
    //Lista de las secciones guardadas en la base de datos
    private List<Seccion> secciones;
    //Lista de los perfiles guardados en la base de datos
    private List<Perfil> perfiles;
    private Usuario otro;
    private Usuario auxiliar;
    private String seccionmod;

    private Long idcrear;
    private String contraseniacrear;
    private String NIFcrear;
    private String emailcrear;
    private String nombrecrear;
    private String apellidoscrear;
    private String sexocrear;
    private Date fecha_nacimientocrear;
    private int cod_postalcrear;
    private String direccioncrear;
    private String provinciacrear;
    private String localidadcrear;
    private int cuotacrear;
    private int telefonocrear;
    private int movilcrear;
    private String metodopagocrear;
    private String perfilcrear = "";
    private String seccioncrear;

    @EJB
    private Usuarios u;
    
    @EJB
    private Perfiles perfs;
    
    @EJB
    private Seccionesb secs;
    
    @Inject
    private Controlador_Login ctr;
    
    @Inject
    private Control_Eventos ctre;

    public String logout() {
        // Destruye la sesión (y con ello, el ámbito de este bean)
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getExternalContext().invalidateSession();
        setUser(null);
        return "login.xhtml";
    }

    public Usuario buscarUsuario(Long id) throws UsuarioException {

        Usuario aux = null;

        Iterator<Usuario> iter = getUsers().iterator();
        while (iter.hasNext() && aux == null) {
            Usuario it = iter.next();
            if (it.getId().equals(id)) {
                aux = it;
            }
        }

        if (aux == null) {
            throw new UsuarioException("Usuarios no existente");
        }

        return aux;
    }

    public String modificarboton() {

        /*        Iterator<Usuario> iter = users.iterator();
        u = iter.next();
        while (iter.hasNext() && u.getId() == id) {
            u = iter.next();
        }
         */
       // setOtro(new Usuario(getAuxiliar().getId(), getAuxiliar().getContrasenia(), getAuxiliar().getNIF(), getAuxiliar().getEmail(), getAuxiliar().getNombre(), getAuxiliar().getApellidos(), getAuxiliar().getSexo(), getAuxiliar().getFecha_nacimiento(), getAuxiliar().getCodigo_postal(), getAuxiliar().getDireccion(), getAuxiliar().getProvincia(), getAuxiliar().getLocalidad(), getAuxiliar().getFecha_ingreso(), getAuxiliar().getCuota_total(), getAuxiliar().getTelefono(), getAuxiliar().getMovil(), getAuxiliar().getMetodo_pago(), getAuxiliar().getPerfiles(), getAuxiliar().getSeccion()));

        return "ModPerf.xhtml";
    }

    public String aceptarmod() {

        Seccion sec;
/*
        switch (getSeccionmod()) {
            case "Castores":
                sec = new Seccion(1L, Seccion.Secciones.Castores);
                break;
            case "Lobatos":
                sec = new Seccion(2L, Seccion.Secciones.Lobatos);
                break;
            case "Scouts":
                sec = new Seccion(4L, Seccion.Secciones.Tropa_Scout);
                break;
            case "Escultas":
                sec = new Seccion(5L, Seccion.Secciones.Escultas_Pioneros);
                break;
            case "Rovers":
                sec = new Seccion(3L, Seccion.Secciones.Rovers_Compañeros);
                break;
            default:
                sec = getOtro().getSeccion();
                break;
        }*/
        seccionmod = null;

       // getOtro().setSeccion(sec);

        int i = 0;
        while (i < getUsers().size() && getOtro().getId() != getUsers().get(i).getId()) {
            i++;
        }

        Usuario u = getUsers().get(i);
        u.setNombre(getOtro().getNombre());
        u.setApellidos(getOtro().getApellidos());
        u.setNIF(getOtro().getNIF());
        u.setSexo(getOtro().getSexo());
        u.setEmail(getOtro().getEmail());
        u.setFecha_nacimiento(getOtro().getFecha_nacimiento());
        u.setCodigo_postal(getOtro().getCodigo_postal());
        u.setDireccion(getOtro().getDireccion());
        u.setProvincia(getOtro().getProvincia());
        u.setLocalidad(getOtro().getLocalidad());
        u.setFecha_ingreso(getOtro().getFecha_ingreso());
        u.setCuota_total(getOtro().getCuota_total());
        u.setTelefono(getOtro().getTelefono());
        u.setMovil(getOtro().getMovil());
        u.setMetodo_pago(getOtro().getMetodo_pago());
        u.setSeccion(getOtro().getSeccion());

        //getCtr().setUsers(getUsers());

        return "Lista_Usuarios.xhtml";
    }
    
    public String cancerlarMod () {
        seccionmod = null;
        return "Lista_Usuarios.xhtml";
    }

    public String borrarUsuario(Long id) throws CuentaInexistenteException, UsuarioException {

        Usuario b = buscarUsuario(id);
        u.eliminarUsuario(b);
        users=u.getUsuarios();
        refrescarUsers2();

        return "Lista_Usuarios.xhtml";
    }

    public String verUsuario(Long id) {

        Iterator<Usuario> iter = getUsers().iterator();
        Usuario u = iter.next();
        while (iter.hasNext() && !Objects.equals(id, u.getId())) {
            u = iter.next();
        }
        if (Objects.equals(id, u.getId())) {
            setAuxiliar(u);
        }

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

    public String crearUsuario() throws CuentaExistenteException {
        Perfil p = null;
        Seccion s = null;
        
        perfiles = perfs.getPerfiles();
        secciones = secs.getSecciones();

        switch (perfilcrear) {
            case "CoordGen":
                p = perfiles.get(1);
                break;
            case "CoordSec":
                p = perfiles.get(2);
                break;
            case "Scouter":
                p = perfiles.get(3);
                break;
            case "Educando":
                p = perfiles.get(0);
                break;
            default:
                break;
        }

        if (perfilcrear.equals("CoordGen")) {
            s = secciones.get(5);
            
        } else {
            switch (seccioncrear) {
                case "Castores":
                    s = secciones.get(0);
                    break;
                case "Lobatos":
                    s = secciones.get(1);
                    break;
                case "Scouts":
                    s = secciones.get(2);
                    break;
                case "Escultas":
                    s = secciones.get(3);
                    break;
                case "Rovers":
                    s = secciones.get(4);
                    break;
                default:
                    break;
            }
        }

        Date fechaactual = new Date();
        Long idcrear = users.get(users.size()-1).getId()+1;
        
        Usuario us = new Usuario();
        us.setId(idcrear);
        us.setApellidos(apellidoscrear);
        us.setCodigo_postal(cod_postalcrear);
        us.setContrasenia(contraseniacrear);
        us.setCuota_total(cuotacrear);
        us.setDireccion(direccioncrear);
        us.setEmail(emailcrear);
        us.setFecha_ingreso(fechaactual);
        us.setFecha_nacimiento(fecha_nacimientocrear);
        us.setLocalidad(localidadcrear);
        us.setMetodo_pago(metodopagocrear);
        us.setMovil(movilcrear);
        us.setNIF(NIFcrear);
        us.setNombre(nombrecrear);
        us.setPerfiles(p);
        us.setProvincia(provinciacrear);
        us.setSeccion(s);
        us.setSexo(sexocrear);
        us.setTelefono(telefonocrear);

        u.registrarUsuario(us);
        users = u.getUsuarios();
        refrescarUsers2();
        
        contraseniacrear = null;
        NIFcrear = null;
        emailcrear = null;
        nombrecrear = null;
        apellidoscrear = null;
        sexocrear = null;
        fecha_nacimientocrear = null;
        cod_postalcrear = 0;
        direccioncrear = null;
        provinciacrear = null;
        localidadcrear = null;
        cuotacrear = 0;
        telefonocrear = 0;
        movilcrear = 0;
        metodopagocrear = null;
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
     * @return the ctre
     */
    public Control_Eventos getCtre() {
        return ctre;
    }

    /**
     * @param ctre the ctre to set
     */
    public void setCtre(Control_Eventos ctre) {
        this.ctre = ctre;
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
     * @return the ctr
     */
    public Controlador_Login getCtr() {
        return ctr;
    }

    /**
     * @param ctr the ctr to set
     */
    public void setCtr(Controlador_Login ctr) {
        this.ctr = ctr;
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

    /**
     * @return the idcrear
     */
    public Long getIdcrear() {
        return idcrear;
    }

    /**
     * @param idcrear the idcrear to set
     */
    public void setIdcrear(Long idcrear) {
        this.idcrear = idcrear;
    }

    /**
     * @return the contraseniacrear
     */
    public String getContraseniacrear() {
        return contraseniacrear;
    }

    /**
     * @param contraseniacrear the contraseniacrear to set
     */
    public void setContraseniacrear(String contraseniacrear) {
        this.contraseniacrear = contraseniacrear;
    }

    /**
     * @return the NIFcrear
     */
    public String getNIFcrear() {
        return NIFcrear;
    }

    /**
     * @param NIFcrear the NIFcrear to set
     */
    public void setNIFcrear(String NIFcrear) {
        this.NIFcrear = NIFcrear;
    }

    /**
     * @return the emailcrear
     */
    public String getEmailcrear() {
        return emailcrear;
    }

    /**
     * @param emailcrear the emailcrear to set
     */
    public void setEmailcrear(String emailcrear) {
        this.emailcrear = emailcrear;
    }

    /**
     * @return the nombrecrear
     */
    public String getNombrecrear() {
        return nombrecrear;
    }

    /**
     * @param nombrecrear the nombrecrear to set
     */
    public void setNombrecrear(String nombrecrear) {
        this.nombrecrear = nombrecrear;
    }

    /**
     * @return the apellidoscrear
     */
    public String getApellidoscrear() {
        return apellidoscrear;
    }

    /**
     * @param apellidoscrear the apellidoscrear to set
     */
    public void setApellidoscrear(String apellidoscrear) {
        this.apellidoscrear = apellidoscrear;
    }

    /**
     * @return the sexocrear
     */
    public String getSexocrear() {
        return sexocrear;
    }

    /**
     * @param sexocrear the sexocrear to set
     */
    public void setSexocrear(String sexocrear) {
        this.sexocrear = sexocrear;
    }

    /**
     * @return the fecha_nacimientocrear
     */
    public Date getFecha_nacimientocrear() {
        return fecha_nacimientocrear;
    }

    /**
     * @param fecha_nacimientocrear the fecha_nacimientocrear to set
     */
    public void setFecha_nacimientocrear(Date fecha_nacimientocrear) {
        this.fecha_nacimientocrear = fecha_nacimientocrear;
    }

    /**
     * @return the direccioncrear
     */
    public String getDireccioncrear() {
        return direccioncrear;
    }

    /**
     * @param direccioncrear the direccioncrear to set
     */
    public void setDireccioncrear(String direccioncrear) {
        this.direccioncrear = direccioncrear;
    }

    /**
     * @return the provinciacrear
     */
    public String getProvinciacrear() {
        return provinciacrear;
    }

    /**
     * @param provinciacrear the provinciacrear to set
     */
    public void setProvinciacrear(String provinciacrear) {
        this.provinciacrear = provinciacrear;
    }

    /**
     * @return the localidadcrear
     */
    public String getLocalidadcrear() {
        return localidadcrear;
    }

    /**
     * @param localidadcrear the localidadcrear to set
     */
    public void setLocalidadcrear(String localidadcrear) {
        this.localidadcrear = localidadcrear;
    }

    /**
     * @return the metodopagocrear
     */
    public String getMetodopagocrear() {
        return metodopagocrear;
    }

    /**
     * @param metodopagocrear the metodopagocrear to set
     */
    public void setMetodopagocrear(String metodopagocrear) {
        this.metodopagocrear = metodopagocrear;
    }

    /**
     * @return the perfilcrear
     */
    public String getPerfilcrear() {
        return perfilcrear;
    }

    /**
     * @param perfilcrear the perfilcrear to set
     */
    public void setPerfilcrear(String perfilcrear) {
        this.perfilcrear = perfilcrear;
    }

    /**
     * @return the seccioncrear
     */
    public String getSeccioncrear() {
        return seccioncrear;
    }

    /**
     * @param seccioncrear the seccioncrear to set
     */
    public void setSeccioncrear(String seccioncrear) {
        this.seccioncrear = seccioncrear;
    }

    public List<Seccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<Seccion> secciones) {
        this.secciones = secciones;
    }

    public List<Perfil> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<Perfil> perfiles) {
        this.perfiles = perfiles;
    }

    public int getCod_postalcrear() {
        return cod_postalcrear;
    }

    public void setCod_postalcrear(int cod_postalcrear) {
        this.cod_postalcrear = cod_postalcrear;
    }

    public int getCuotacrear() {
        return cuotacrear;
    }

    public void setCuotacrear(int cuotacrear) {
        this.cuotacrear = cuotacrear;
    }

    public int getTelefonocrear() {
        return telefonocrear;
    }

    public void setTelefonocrear(int telefonocrear) {
        this.telefonocrear = telefonocrear;
    }

    public int getMovilcrear() {
        return movilcrear;
    }

    public void setMovilcrear(int movilcrear) {
        this.movilcrear = movilcrear;
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

}
