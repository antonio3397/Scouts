/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import clases.Evento;
import clases.Perfil;
import clases.Seccion;
import clases.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/*
Tipos de id que tiene cada sección:
TODAS -> 0L
CASTORES -> 1L
LOBATOS -> 2L
ROVERS COMPAÑEROS -> 3L
TROPA SCOUT -> 4L
ESCULTAS PIONEROS -> 5L
 */
/**
 *
 * @author DavidDR
 */
@Named(value = "miSesion")
@SessionScoped
public class MiSesion implements Serializable {

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

    private Usuario user;
    private List<Usuario> users;
    private List<Usuario> users2;
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
    private String cod_postalcrear;
    private String direccioncrear;
    private String provinciacrear;
    private String localidadcrear;
    private Date fecha_ingresocrear;
    private String cuotacrear;
    private String telefonocrear;
    private String movilcrear;
    private String metodopagocrear;
    private String perfilcrear = "";
    private String seccioncrear;

    @Inject
    private Controlador_Login ctr;
    
    @Inject
    private Control_Eventos ctre;

    /**
     * Creates a new instance of MiSesion
     */
    public MiSesion() {
    }

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
        setOtro(new Usuario(getAuxiliar().getId(), getAuxiliar().getContrasenia(), getAuxiliar().getNIF(), getAuxiliar().getEmail(), getAuxiliar().getNombre(), getAuxiliar().getApellidos(), getAuxiliar().getSexo(), getAuxiliar().getFecha_nacimiento(), getAuxiliar().getCodigo_postal(), getAuxiliar().getDireccion(), getAuxiliar().getProvincia(), getAuxiliar().getLocalidad(), getAuxiliar().getFecha_ingreso(), getAuxiliar().getCuota_total(), getAuxiliar().getTelefono(), getAuxiliar().getMovil(), getAuxiliar().getMetodo_pago(), getAuxiliar().getPerfiles(), getAuxiliar().getSeccion()));

        return "ModPerf.xhtml";
    }

    public String aceptarmod() {

        Seccion sec;

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
        }
        seccionmod = null;

        getOtro().setSeccion(sec);

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

        getCtr().setUsers(getUsers());

        return "Lista_Usuarios.xhtml";
    }
    
    public String cancerlarMod () {
        seccionmod = null;
        return "Lista_Usuarios.xhtml";
    }

    public String borrarUsuario(Long id) throws UsuarioException {

        Usuario b = buscarUsuario(id);

        getUsers().remove(b);
        getUsers2().remove(b);

        return "Lista_Usuarios.xhtml";
    }

    /**
     * @return the users
     */
    public List<Usuario> getUsers() {
        return users;
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

    public String crearUsuario() {
        Perfil p = null;
        Seccion s = null;

        switch (perfilcrear) {
            case "CoordGen":
                p = new Perfil(Perfil.Rol.COORDGEN);
                break;
            case "CoordSec":
                p = new Perfil(Perfil.Rol.COORDSEC);
                break;
            case "Scouter":
                p = new Perfil(Perfil.Rol.SCOUTER);
                break;
            case "Educando":
                p = new Perfil(Perfil.Rol.EDUCANDO);
                break;
            default:
                break;
        }

        if (perfilcrear.equals("CoordGen")) {
            s = new Seccion(0L, Seccion.Secciones.TODAS);
        } else {
            switch (seccioncrear) {
                case "Castores":
                    s = new Seccion(1L, Seccion.Secciones.Castores);
                    break;
                case "Lobatos":
                    s = new Seccion(2L, Seccion.Secciones.Lobatos);
                    break;
                case "Scouts":
                    s = new Seccion(4L, Seccion.Secciones.Tropa_Scout);
                    break;
                case "Escultas":
                    s = new Seccion(5L, Seccion.Secciones.Escultas_Pioneros);
                    break;
                case "Rovers":
                    s = new Seccion(3L, Seccion.Secciones.Rovers_Compañeros);
                    break;
                default:
                    break;
            }
        }

        idcrear = users.get(users.size() - 1).getId() + 1;

        Usuario u = new Usuario(idcrear, contraseniacrear, NIFcrear, emailcrear, nombrecrear, apellidoscrear, sexocrear, fecha_nacimientocrear, Integer.parseInt(cod_postalcrear), direccioncrear, provinciacrear, localidadcrear, fecha_ingresocrear, Integer.parseInt(cuotacrear), Integer.parseInt(telefonocrear), Integer.parseInt(movilcrear), metodopagocrear, p, s);

        idcrear = null;
        contraseniacrear = null;
        NIFcrear = null;
        emailcrear = null;
        nombrecrear = null;
        apellidoscrear = null;
        sexocrear = null;
        fecha_nacimientocrear = null;
        cod_postalcrear = null;
        direccioncrear = null;
        provinciacrear = null;
        localidadcrear = null;
        fecha_ingresocrear = null;
        cuotacrear = null;
        telefonocrear = null;
        movilcrear = null;
        metodopagocrear = null;
        perfilcrear = "";
        seccioncrear = null;

        users.add(u);
        users2.add(u);

        return "Lista_Usuarios.xhtml";
    }

    public boolean perfCrearGeneral() {
        return perfilcrear.equals("CoordGen");
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
     * @return the cod_postalcrear
     */
    public String getCod_postalcrear() {
        return cod_postalcrear;
    }

    /**
     * @param cod_postalcrear the cod_postalcrear to set
     */
    public void setCod_postalcrear(String cod_postalcrear) {
        this.cod_postalcrear = cod_postalcrear;
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
     * @return the fecha_ingresocrear
     */
    public Date getFecha_ingresocrear() {
        return fecha_ingresocrear;
    }

    /**
     * @param fecha_ingresocrear the fecha_ingresocrear to set
     */
    public void setFecha_ingresocrear(Date fecha_ingresocrear) {
        this.fecha_ingresocrear = fecha_ingresocrear;
    }

    /**
     * @return the cuotacrear
     */
    public String getCuotacrear() {
        return cuotacrear;
    }

    /**
     * @param cuotacrear the cuotacrear to set
     */
    public void setCuotacrear(String cuotacrear) {
        this.cuotacrear = cuotacrear;
    }

    /**
     * @return the telefonocrear
     */
    public String getTelefonocrear() {
        return telefonocrear;
    }

    /**
     * @param telefonocrear the telefonocrear to set
     */
    public void setTelefonocrear(String telefonocrear) {
        this.telefonocrear = telefonocrear;
    }

    /**
     * @return the movilcrear
     */
    public String getMovilcrear() {
        return movilcrear;
    }

    /**
     * @param movilcrear the movilcrear to set
     */
    public void setMovilcrear(String movilcrear) {
        this.movilcrear = movilcrear;
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

}
