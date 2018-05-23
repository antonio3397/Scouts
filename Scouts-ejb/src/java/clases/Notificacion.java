/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

/**
 *
 * @author anton
 */
@Entity
public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private NotificacionID id;
    
    @Column(nullable=false)
    private String titulo;
    @Column(nullable=false)
    private String texto;
    @Column(nullable=false)
    private Date fecha;
    
    public Notificacion(NotificacionID nID,String tit, String text, Date fecha){
        id = nID;
        titulo = tit;
        texto = text;
        this.fecha = fecha;
    }
    
    @ManyToOne
    @MapsId("usuario_id")
    private Usuario usuario;
    
    @ManyToOne
    @MapsId("evento_id")
    private Evento evento;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificacion)) {
            return false;
        }
        Notificacion other = (Notificacion) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trabajo.Notificacion[ id=" + getId() + " ]";
    }

    /**
     * @return the id
     */
    public NotificacionID getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(NotificacionID id) {
        this.id = id;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the evento
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    
    public Date getFecha() { return fecha; }
    public void setEvento(Date fecha) { this.fecha = fecha; }
}
