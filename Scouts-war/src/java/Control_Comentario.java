/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Negocio.Comentarios;
import clases.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author franc
 */
@ManagedBean(name = "Comentarios")
@SessionScoped
public class Control_Comentario {

    private String mensaje;
    private List<Comentario> Comentarios;
    @Inject
    private MiSesion lg;
    @EJB
    private Comentarios comment;

    public List<Comentario> getComentarios() {
        return Comentarios;
    }

    public void setComentarios(List<Comentario> Comentarios) {
        this.Comentarios = Comentarios;
    }

    public List<Comentario> verComentarios(Evento event) {
        List<Comentario> aux = comment.verComentarios(event);
        return aux;
    }

    public boolean hayComentarios(Evento event){
        return verComentarios(event).isEmpty();
    }

    public Comentario buscarComentario(Long id) throws ComentarioException {
        Comentario c = comment.buscarComentario(id);

        if (c == null) {
            throw new ComentarioException("Comentario no encontrado");
        }
        
        return c;
        
    }

    public String borrarComentario(Long id) {
        comment.eliminar(id);
        return "Eventos.html";
    }

    public void agnadirComentario(Evento event, Usuario user) {
        if (!"".equals(mensaje)) {
            Comentario comentario = new Comentario();
            comentario.setId(comment.idMax());
            comentario.setTexto(mensaje);
            comentario.setFecha_creacion(new Date());
            comentario.setEvento(event);
            comentario.setUsuario(user);
            comment.insertar(comentario);
            mensaje = "";
        }
    }
    public String cambioFormato(Date fecha){
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return formateador.format(fecha);
    }
    
    public boolean isUser(Usuario u){
        return lg.getUser().equals(u);
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;

    }
}
