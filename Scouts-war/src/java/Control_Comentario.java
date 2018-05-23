/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import clases.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
        try {
            mensaje = "";
            Comentarios = new ArrayList<>();
            Comentarios.add(new Comentario(1L, "Ay que emoción, ojala ya poder ir allí", new Date(2018 - 1900, 3, 3, 20, 02, 00), getEv().buscarEvento(1L), getLg().buscarUsuario(121L)));
            Comentarios.add(new Comentario(2L, "Ay, espero poder ir a esta excursion. Me hace mucha ilu ver ardillitas >.< ", new Date(2018 - 1900, 8, 8, 15, 30, 30), getEv().buscarEvento(3L), getLg().buscarUsuario(121L)));
            Comentarios.add(new Comentario(3L, "Este evento está bien organizado? Es que hace una calor alli que nos vamos a morir", new Date(2018 - 1900, 3, 3, 15, 03, 26), getEv().buscarEvento(2L), getLg().buscarUsuario(122L)));
            Comentarios.add(new Comentario(4L, "Pero que tonteria de excursion. Es mas, es imposible conseguir ese dinero aunque vendiesemos magdalenas", new Date(2018 - 1900, 8, 8, 20, 20, 20), getEv().buscarEvento(3L), getLg().buscarUsuario(122L)));
            Comentarios.add(new Comentario(5L, "Ay, que negativo eres uwu. Yo casi tengo el dinero para ir", new Date(2018 - 1900, 10, 8, 10, 9, 59), getEv().buscarEvento(3L), getLg().buscarUsuario(121L)));
            Comentarios.add(new Comentario(6L, "Meh. Seguramente sea otra excursión igual que cuando fuimos a los montes de Malaga", new Date(2018 - 1900, 3, 3, 21, 03, 02), getEv().buscarEvento(1L), getLg().buscarUsuario(122L)));
        } catch (EventoException | UsuarioException ex) {
            Logger.getLogger(Control_Comentario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
