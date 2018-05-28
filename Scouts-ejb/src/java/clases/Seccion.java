/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/*
 *
 * @author Scuadrado
 */
@Entity
public class Seccion implements Serializable {

     public enum Secciones{
      Castores, Lobatos, Tropa_Scout, Escultas_Pioneros, Rovers_Compañeros, TODAS;
    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Secciones Nombre;
    private Integer Edad_minima;
    private Integer Edad_maxima;

    @OneToMany(mappedBy = "seccion")
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "seccion")
    private List<Evento> eventos;

    /**
     * @return the Edad_minima
     */
    public Integer getEdad_minima() {
        return Edad_minima;
    }

    /**
     * @param Edad_minima the Edad_minima to set
     */
    public void setEdad_minima(Integer Edad_minima) {
        this.Edad_minima = Edad_minima;
    }

    public Secciones getNombre() {
        return Nombre;
    }

    public void setNombre(Secciones Nombre) {
        this.Nombre = Nombre;
    }

    /**
     * @return the Edad_maxima
     */
    public Integer getEdad_maxima() {
        return Edad_maxima;
    }

    /**
     * @param Edad_maxima the Edad_maxima to set
     */
    public void setEdad_maxima(Integer Edad_maxima) {
        this.Edad_maxima = Edad_maxima;
    }

    /**
     * @return the usuarios
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the eventos
     */
    public List<Evento> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seccion)) {
            return false;
        }
        Seccion other = (Seccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trabajo.Seccion[ id=" + id + " ]";
    }

}
