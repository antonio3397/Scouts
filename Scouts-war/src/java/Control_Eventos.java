
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Negocio.Eventos;
import Negocio.SeccionInexistenteException;
import Negocio.Seccionesb;
import clases.Evento;
import clases.Notificacion;
import clases.NotificacionID;
import clases.Seccion;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author anton
 */
@SessionScoped
@Named(value = "Eventos")
public class Control_Eventos implements Serializable {
    
    private List<Evento> eventosj;
    private List<Evento> eventosj2;
    private Evento event;
    private Long idcrear;
    private Date fechacrear;
    private String titulocrear;
    private String localizacioncrear;
    private String descripcioncrear;
    private String preciocrear;
    private String seccioncrear;
    private Evento aux;
    
    private String seccionMod;
    
    @EJB
    private Eventos evento;
    
    @EJB
    private Seccionesb seccion;
    
    @Inject
            Control_Notificaciones CN;
    
    public Evento buscarEvento(Long id) throws EventoException {
        Evento enc = evento.obtenerEvento(id);
        
        if (enc == null) {
            throw new EventoException("Evento no encontrado");
        }
        return enc;
    }
    
    public String modificarEvento(Long id) throws EventoException {
        aux = evento.obtenerEvento(id);
        return "ModEvento.xhtml";
    }
    
    public String aceptarMod() throws EventoException, SeccionInexistenteException {
        Evento b = evento.obtenerEvento(aux.getId());
        b.setTitulo(aux.getTitulo());
        b.setFecha(aux.getFecha());
        b.setLocalizacion(aux.getLocalizacion());
        b.setPrecio(aux.getPrecio());
        
        switch (seccionMod) {
            case "Castores":
                b.setSeccion(seccion.getSeccion(1L));
                break;
            case "Lobatos":
                b.setSeccion(seccion.getSeccion(2L));
                break;
            case "Scouts":
                b.setSeccion(seccion.getSeccion(3L));
                break;
            case "Escultas":
                b.setSeccion(seccion.getSeccion(4L));
                break;
            case "Rovers":
                b.setSeccion(seccion.getSeccion(5L));
                break;
            default:
                break;
        }
        evento.modificar(b);
        seccionMod = null;
        
        return "Lista_eventos.xhtml";
    }
    
    public String cancelarMod() {
        return "Eventos.xhtml";
    }
    
    public String borrarEvento(Long id) throws EventoException {
        evento.eliminar(evento.obtenerEvento(id));
        return "Lista_eventos.xhtml";
    }
    
    public String CrearEvento() throws SeccionInexistenteException {
        
        Seccion sec = null;
        int precio = Integer.parseInt(preciocrear);
        
        switch (seccioncrear) {
            case "Castores":
                sec = seccion.getSeccion(1L);
                break;
            case "Lobatos":
                sec = seccion.getSeccion(2L);
                break;
            case "Scouts":
                sec = seccion.getSeccion(3L);
                break;
            case "Escultas":
                sec = seccion.getSeccion(4L);
                break;
            case "Rovers":
                sec = seccion.getSeccion(5L);
                break;
            default:
                break;
        }
        

        Evento ev = new Evento();
        ev.setId(evento.idMax()+1);
        ev.setTitulo(titulocrear);
        ev.setFecha(fechacrear);
        ev.setLocalizacion(localizacioncrear);
        ev.setDescripcion(descripcioncrear);
        ev.setPrecio(precio);
        ev.setSeccion(sec);
        evento.insertar(ev);
        
       // CN.addNotificame(ev);  
        
        fechacrear = null;
        idcrear = null;
        titulocrear = null;
        localizacioncrear = null;
        descripcioncrear = null;
        preciocrear = null;
        seccioncrear = null;

        return "Lista_eventos.xhtml";
    }

    public String cancelarcrear() {
        fechacrear = null;
        idcrear = null;
        titulocrear = null;
        localizacioncrear = null;
        descripcioncrear = null;
        preciocrear = null;
        seccioncrear = null;

        return "Lista_eventos.xhtml";
    }

    public String verEvento(Long id) throws EventoException {

        event=buscarEvento(id);

        return "Eventos.xhtml";
    }

    /**
     * @return the eventosj
     */
    public List<Evento> getEventosj() {
        return eventosj;
    }

    /**
     * @param eventosj the eventosj to set
     */
    public void setEventosj(List<Evento> eventosj) {
        this.eventosj = eventosj;
    }

    public Evento getEvent() {
        return event;
    }

    public void setEvent(Evento event) {
        this.event = event;
    }

    /**
     * @return the idcrear
     */
    public Long getIdcrear() {
        return idcrear;
    }

    /**
     * @param idcrear the idcrear to set
     */
    public void setIdcrear(Long idcrear) {
        this.idcrear = idcrear;
    }

    /**
     * @return the titulocrear
     */
    public String getTitulocrear() {
        return titulocrear;
    }

    /**
     * @param titulocrear the titulocrear to set
     */
    public void setTitulocrear(String titulocrear) {
        this.titulocrear = titulocrear;
    }

    /**
     * @return the localizacioncrear
     */
    public String getLocalizacioncrear() {
        return localizacioncrear;
    }

    /**
     * @param localizacioncrear the localizacioncrear to set
     */
    public void setLocalizacioncrear(String localizacioncrear) {
        this.localizacioncrear = localizacioncrear;
    }

    /**
     * @return the descripcioncrear
     */
    public String getDescripcioncrear() {
        return descripcioncrear;
    }

    /**
     * @param descripcioncrear the descripcioncrear to set
     */
    public void setDescripcioncrear(String descripcioncrear) {
        this.descripcioncrear = descripcioncrear;
    }

    /**
     * @return the preciocrear
     */
    public String getPreciocrear() {
        return preciocrear;
    }

    /**
     * @param preciocrear the preciocrear to set
     */
    public void setPreciocrear(String preciocrear) {
        this.preciocrear = preciocrear;
    }

    /**
     * @return the seccioncrear
     */
    public String getSeccioncrear() {
        return seccioncrear;
    }

    /**
     * @param seccioncrear the seccioncrear to set
     */
    public void setSeccioncrear(String seccioncrear) {
        this.seccioncrear = seccioncrear;
    }

    /**
     * @return the eventosj2
     */
    public List<Evento> getEventosj2() {
        return eventosj2;
    }

    /**
     * @param eventosj2 the eventosj2 to set
     */
    public void setEventosj2(List<Evento> eventosj2) {
        this.eventosj2 = eventosj2;
    }

    /**
     * @return the fechacrear
     */
    public Date getFechacrear() {
        return fechacrear;
    }

    /**
     * @param fechacrear the fechacrear to set
     */
    public void setFechacrear(Date fechacrear) {
        this.fechacrear = fechacrear;
    }

    /**
     * @return the aux
     */
    public Evento getAux() {
        return aux;
    }

    /**
     * @param aux the aux to set
     */
    public void setAux(Evento aux) {
        this.aux = aux;
    }

    /**
     * @return the seccionMod
     */
    public String getSeccionMod() {
        return seccionMod;
    }

    /**
     * @param seccionMod the seccionMod to set
     */
    public void setSeccionMod(String seccionMod) {
        this.seccionMod = seccionMod;
    }
}
