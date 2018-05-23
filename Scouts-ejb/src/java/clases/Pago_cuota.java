/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author anton
 */
@Entity
public class Pago_cuota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable=false)
    private Integer cantidad_pagada;
    @Temporal(TemporalType.DATE)
    @Column(nullable=false)
    private Date fecha_del_pago;
    @Column(nullable=false)
    private String Tipo_pago;
    
    @ManyToOne
    private Usuario usuarios;
    
     /**
     * @return the fecha_del_pago
     */
    public Date getFecha_del_pago() {
        return fecha_del_pago;
    }

    /**
     * @param fecha_del_pago the fecha_del_pago to set
     */
    public void setFecha_del_pago(Date fecha_del_pago) {
        this.fecha_del_pago = fecha_del_pago;
    }
    
    public void setTipo_pago(String Tipo_pago) {
        this.Tipo_pago = Tipo_pago;
    }

    public String getTipo_pago() {
        return Tipo_pago;
    }
        /**
     * @return the usuarios
     */
    public Usuario getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(Usuario usuarios) {
        this.usuarios = usuarios;
    }
    
    public void setCantidad_pagada(Integer cantidad_pagada) {
        this.cantidad_pagada = cantidad_pagada;
    }

    public Integer getCantidad_pagada() {
        return cantidad_pagada;
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
        if (!(object instanceof Pago_cuota)) {
            return false;
        }
        Pago_cuota other = (Pago_cuota) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "trabajo.Pago_cuota[ id=" + id + " ]";
    }
    
}
