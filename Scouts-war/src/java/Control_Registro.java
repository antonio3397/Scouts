/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Negocio.CuentaExistenteException;
import Negocio.PerfilInexistenteException;
import Negocio.Perfiles;
import Negocio.Responsable;
import Negocio.SeccionInexistenteException;
import Negocio.Seccionesb;
import Negocio.Usuarios;
import clases.Perfil;
import clases.Usuario;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author josealonso
 */
@Named(value = "control_Registro")
@SessionScoped
public class Control_Registro implements Serializable{

    
    
    private Usuario registrar = new Usuario();
    
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
            
            registrar = new Usuario();
            
            return "registrarResponsable.xhtml";
        } else {
            return registrarUsuario();
        }
    }

    public String registrarUsuario() throws CuentaExistenteException, PerfilInexistenteException, SeccionInexistenteException {

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

        u.registrarUsuario(registrar);
        
        registrar = new Usuario();

        return "exitoRegistro.xhtml";
    }
    
    public String cancelarRegistro(){
        
        registrar = new Usuario();
        
        return "login.xhtml";
    }
    
    public Usuario getRegistrar() {
        return registrar;
    }

    public void setRegistrar(Usuario registrar) {
        this.registrar = registrar;
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

    public Responsable getR() {
        return r;
    }

    public void setR(Responsable r) {
        this.r = r;
    }

    public MiSesion getMs() {
        return ms;
    }

    public void setMs(MiSesion ms) {
        this.ms = ms;
    }

    public Control_Responsable getRes() {
        return res;
    }

    public void setRes(Control_Responsable res) {
        this.res = res;
    }
    
    
}
