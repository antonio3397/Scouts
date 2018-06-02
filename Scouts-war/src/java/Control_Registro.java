/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import clases.Perfil;
import clases.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.inject.Inject;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author josealonso
 */
@Named(value = "control_Registro")
@ManagedBean
@RequestScoped
public class Control_Registro {

    
    
    Usuario user;
    //private Usuario user=new Usuario();
    private List<Usuario> list_User=new ArrayList<>();
    private boolean tieneLegal;
    private String rol;
    
    @EJB
    private Usuarios u;
    
    @EJB
    private Perfiles perfs;
    
    @EJB
    private Seccionesb secs;
    
    @EJB
    private Responsable r;
    
    @Inject
    private MiSesion ms;
    
    @Inject
    private Control_Responsable res;
    
    public String necesarioResponsable() throws CuentaExistenteException, PerfilInexistenteException, SeccionInexistenteException{
        
        Date fechaactual = new Date();
        Date fechanac = registrar.getFecha_nacimiento();
        LocalDate fn = fechanac.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fa = fechaactual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period edad = Period.between(fn, fa);
        
        if(edad.getYears()<18){
            if(edad.getYears()<=21){
                registrar.setPerfiles(perfs.getPerfil(Perfil.Rol.EDUCANDO));
            if(secs.getSeccion(1L).getEdad_minima()<=edad.getYears()&& edad.getYears()<secs.getSeccion(1L).getEdad_maxima()){
                registrar.setSeccion(secs.getSeccion(1L));
            } else if(secs.getSeccion(2L).getEdad_minima()<=edad.getYears()&& edad.getYears()<secs.getSeccion(2L).getEdad_maxima()){
                registrar.setSeccion(secs.getSeccion(2L));
            } else if(secs.getSeccion(3L).getEdad_minima()<=edad.getYears()&& edad.getYears()<secs.getSeccion(3L).getEdad_maxima()){
                registrar.setSeccion(secs.getSeccion(3L));
            } else if(secs.getSeccion(4L).getEdad_minima()<=edad.getYears()&& edad.getYears()<secs.getSeccion(4L).getEdad_maxima()){
                registrar.setSeccion(secs.getSeccion(4L));
            } else if(secs.getSeccion(5L).getEdad_minima()<=edad.getYears()&& edad.getYears()<=secs.getSeccion(5L).getEdad_maxima()){
                registrar.setSeccion(secs.getSeccion(5L));
            }
        } else {
            registrar.setPerfiles(perfs.getPerfil(Perfil.Rol.SCOUTER));
            registrar.setSeccion(secs.getSeccion(5L));
        }
            
            if(registrar.getPerfiles().equals(perfs.getPerfil(Perfil.Rol.EDUCANDO))){
                registrar.setCuota_total(15);
            } else if (registrar.getPerfiles().equals(perfs.getPerfil(Perfil.Rol.SCOUTER))){
                registrar.setCuota_total(20);
            } else {
                registrar.setCuota_total(0);
            }

            registrar.setFecha_ingreso(fechaactual);
            Long idcrear = u.getUsuarios().get(u.getUsuarios().size()-1).getId()+1;
            registrar.setId(idcrear);
            registrar.setVerificado(false);
            String password = registrar.getContrasenia();
            String cifrado = DigestUtils.sha256Hex(password);
            registrar.setContrasenia(cifrado);
            
            res.setCrearuser(registrar);
            
            return "registrarResponsable.xhtml";
        } else {
            return registrarUsuario();
        }
    }

    /**
     * @return the user
     */
    public Usuario getUser() {
        return user;
    }

        Date fechaactual = new Date();
        Date fechanac = registrar.getFecha_nacimiento();
        LocalDate fn = fechanac.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fa = fechaactual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period edad = Period.between(fn, fa);
        
        if(edad.getYears()<=21){
            registrar.setPerfiles(perfs.getPerfil(Perfil.Rol.EDUCANDO));
            if(secs.getSeccion(1L).getEdad_minima()<=edad.getYears()&& edad.getYears()<secs.getSeccion(1L).getEdad_maxima()){
                registrar.setSeccion(secs.getSeccion(1L));
            } else if(secs.getSeccion(2L).getEdad_minima()<=edad.getYears()&& edad.getYears()<secs.getSeccion(2L).getEdad_maxima()){
                registrar.setSeccion(secs.getSeccion(2L));
            } else if(secs.getSeccion(3L).getEdad_minima()<=edad.getYears()&& edad.getYears()<secs.getSeccion(3L).getEdad_maxima()){
                registrar.setSeccion(secs.getSeccion(3L));
            } else if(secs.getSeccion(4L).getEdad_minima()<=edad.getYears()&& edad.getYears()<secs.getSeccion(4L).getEdad_maxima()){
                registrar.setSeccion(secs.getSeccion(4L));
            } else if(secs.getSeccion(5L).getEdad_minima()<=edad.getYears()&& edad.getYears()<=secs.getSeccion(5L).getEdad_maxima()){
                registrar.setSeccion(secs.getSeccion(5L));
            }
        } else {
            registrar.setPerfiles(perfs.getPerfil(Perfil.Rol.SCOUTER));
            registrar.setSeccion(secs.getSeccion(5L));
        }
        
        if(registrar.getPerfiles().equals(perfs.getPerfil(Perfil.Rol.EDUCANDO))){
            registrar.setCuota_total(15);
        } else if (registrar.getPerfiles().equals(perfs.getPerfil(Perfil.Rol.SCOUTER))){
            registrar.setCuota_total(20);
        } else {
            registrar.setCuota_total(0);
        }
        
        registrar.setFecha_ingreso(fechaactual);
        Long idcrear = u.getUsuarios().get(u.getUsuarios().size()-1).getId()+1;
        registrar.setId(idcrear);
        registrar.setVerificado(false);
        String password = registrar.getContrasenia();
        String cifrado = DigestUtils.sha256Hex(password);
        registrar.setContrasenia(cifrado);

    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * @return the tieneLegal
     */
    public boolean isTieneLegal() {
        return tieneLegal;
    }

    /**
     * @param tieneLegal the tieneLegal to set
     */
    public void setTieneLegal(boolean tieneLegal) {
        this.tieneLegal = tieneLegal;
    }
    /**
     * @param user the user to set
     */
    public void setUser(Usuario user) {
        this.user = user;
    }

    /**
     * @return the list_User
     */
    public List<Usuario> getList_User() {
        return list_User;
    }

    /**
     * @param list_User the list_User to set
     */
    public void setList_User(List<Usuario> list_User) {
        this.list_User = list_User;
    }
    
    public String agregarPersona (){
        this.list_User.add(user);
        return "exitoRegistro.xhtml";
    }
   public String quien(){
       if(rol.equals(Perfil.Rol.SCOUTER.toString()) && !tieneLegal){
           return "registroSCOUTER.xhtml";
       } else if(rol.equals(Perfil.Rol.EDUCANDO.toString()) && tieneLegal){
           return "registroEDUCANDO_PADRES.xhtml";
       } else if(rol.equalsIgnoreCase(Perfil.Rol.EDUCANDO.toString()) && !tieneLegal){
           return "registroEDUCANDO_NO_PADRES.xhtml";
       } else {
          FacesContext fm = FacesContext.getCurrentInstance();
           fm.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El scouter no puede ser menor", "El scouter no puede ser menor"));
       }
       return null;
   }
}
