/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author anton
 */
@Embeddable
public class NotificacionID implements Serializable {

    private static final long serialVersionUID = 1L;
    private String usuario_email;
    private Long evento_id;

    @Override
    public String toString() {
        return "NotificacionID{" + "usuario_email=" + usuario_email + ", evento_id=" + evento_id + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.usuario_email);
        hash = 53 * hash + Objects.hashCode(this.evento_id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NotificacionID other = (NotificacionID) obj;
        if (!Objects.equals(this.usuario_email, other.usuario_email)) {
            return false;
        }
        if (!Objects.equals(this.evento_id, other.evento_id)) {
            return false;
        }
        return true;
    }

    public String getUsuario_email() {
        return usuario_email;
    }

    public void setUsuario_email(String usuario_email) {
        this.usuario_email = usuario_email;
    }
    
    /**
     * @return the evento_id
     */
    public Long getEvento_id() {
        return evento_id;
    }

    /**
     * @param evento_id the evento_id to set
     */
    public void setEvento_id(Long evento_id) {
        this.evento_id = evento_id;
    }
    
}
