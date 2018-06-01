/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Negocio.Responsable;
import Negocio.ResponsableInexistenteException;
import Negocio.ScoutsException;
import Negocio.Usuarios;
import clases.Responsable_Legal;
import clases.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author anton
 */
@Named(value = "control_Responsable")
@SessionScoped
public class Control_Responsable implements Serializable {

    //Responsable que se va a ver
    private Responsable_Legal ver;
    //Responsable que se va a modificar
    private Responsable_Legal modificar;
    //Responsable y usuario que se van crear
    private Responsable_Legal crear = new Responsable_Legal();
    private Usuario crearuser;
    
    @EJB
    private Responsable res;
    
    @EJB
    private Usuarios u;
    
    @Inject
    private MiSesion ms;
    
    public String verResponsable(Long id) throws ResponsableInexistenteException{
        
        ver = res.getResponsable(id);
        
        return "verResponsable.xhtml";
        
    }
    
    public String crearResponsable() throws ScoutsException{
        
        Responsable_Legal crear1;
        
        u.registrarUsuario(crearuser);
        Usuario user = u.refrescarUsuario(crearuser);
        
        if(res.estaResponsableNIF(crear)){
            System.out.println("aqui");
            crear1 = res.refrescarResponsable(crear);
            crear1.getUsuarios().add(u.buscarUsuario(user.getId()));
            res.modificarResponsable(crear1);
            user.setResponsable(crear1);
            u.modificarUsuario(user);
        } else {
            List<Usuario> users = new ArrayList<>();
            users.add(user);
            crear.setUsuarios(users);
            Long idcrear = res.getResponsables().get(res.getResponsables().size()-1).getId()+1;
            crear.setId(idcrear);
            res.registrarResponsable(crear);
            user.setResponsable(crear);
            u.modificarUsuario(user);
        }
        
        ms.setUsercrear(new Usuario());
        crear = new Responsable_Legal();
        ms.setUsers(u.getUsuarios());
        ms.refrescarUsers2();
        
        return "Lista_Usuarios.xhtml";
    }
    
    public String modificarBoton(){
        
        modificar = ver;
        
        return "ModResponsable.xhtml";
    }
    
    public String aceptarmod() throws ScoutsException{
        
        res.modificarResponsable(modificar);
        ms.setUsers(u.getUsuarios());
        ms.refrescarUsers2();
        
        return "OtroPerfil.xhtml";
    }

    public Responsable_Legal getVer() {
        return ver;
    }

    public void setVer(Responsable_Legal ver) {
        this.ver = ver;
    }

    public Responsable getRes() {
        return res;
    }

    public void setRes(Responsable res) {
        this.res = res;
    }

    public Responsable_Legal getCrear() {
        return crear;
    }

    public void setCrear(Responsable_Legal crear) {
        this.crear = crear;
    }

    public Usuario getCrearuser() {
        return crearuser;
    }

    public void setCrearuser(Usuario crearuser) {
        this.crearuser = crearuser;
    }

    public Usuarios getU() {
        return u;
    }

    public void setU(Usuarios u) {
        this.u = u;
    }

    public MiSesion getMs() {
        return ms;
    }

    public void setMs(MiSesion ms) {
        this.ms = ms;
    }

    public Responsable_Legal getModificar() {
        return modificar;
    }

    public void setModificar(Responsable_Legal modificar) {
        this.modificar = modificar;
    }
    
}
