/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import clases.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author franc
 */
@SessionScoped
@Named(value="Comentarios")
public class Control_Comentario implements Serializable {

    /**
     * @return the ev
     */
    public Control_Eventos getEv() {
        return ev;
    }

    /**
     * @param ev the ev to set
     */
    public void setEv(Control_Eventos ev) {
        this.ev = ev;
    }

    /**
     * @return the lg
     */
    public MiSesion getLg() {
        return lg;
    }

    /**
     * @param lg the lg to set
     */
    public void setLg(MiSesion lg) {
        this.lg = lg;
    }

    private String mensaje;
    private List<Comentario> Comentarios;
    @Inject
    private Control_Eventos ev;
    @Inject
    private MiSesion lg;

    public List<Comentario> getComentarios() {
        return Comentarios;
    }

    public void setComentarios(List<Comentario> Comentarios) {
        this.Comentarios = Comentarios;
    }

    public List<Comentario> buscarComentarios(Evento event) throws EventoException {
        List<Comentario> aux = new ArrayList<>();
        for (Comentario comment : Comentarios) {
            if (comment.getEvento().equals(event)) {
                aux.add(comment);
            }
        }
        return aux;
    }

    public boolean hayComentarios(Evento event) throws EventoException {
        return buscarComentarios(event).isEmpty();
    }

    public Comentario buscarComentario(Long id) throws ComentarioException {
        Comentario c = null;
        Iterator<Comentario> iter = Comentarios.iterator();
        while (iter.hasNext() && c == null) {
            Comentario aux = iter.next();
            if (aux.getId().equals(id)) {
                c = aux;
            }
        }

        if (c == null) {
            throw new ComentarioException("Comentario no encontrado");
        }
        
        return c;
        
    }

    public String borrarComentario(Long id) throws ComentarioException {
        Comentario c = buscarComentario (id);
        Comentarios.remove(c);
        return "Eventos.html";
    }

    public void agnadirComentario(Evento event, Usuario user) throws EventoException {
        if (!"".equals(mensaje)) {
            long tam = Comentarios.size();
            Comentario coment = new Comentario(tam, mensaje, new Date(), event, user);
            Comentarios.add(coment);
            mensaje = "";
        }
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;

    }
}
