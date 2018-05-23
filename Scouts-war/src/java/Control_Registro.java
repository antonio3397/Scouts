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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

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
    
    /**
     * Creates a new instance of Control_Registro
     */
    public Control_Registro() {
    }

    /**
     * @return the user
     */
    public Usuario getUser() {
        return user;
    }

    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

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
