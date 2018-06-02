
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Negocio.Eventos;
import Negocio.SeccionInexistenteException;
import Negocio.Seccionesb;
import clases.Evento;
import java.io.Serializable;
import java.util.List;
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
    private Evento crear = new Evento();
    private String seccioncrear;
    private Evento aux;
    private boolean necdoc;
    
    private String seccionMod;
    
    @EJB
    private Eventos evento;
    
    @EJB
    private Seccionesb seccion;
    
    @Inject
    private Archivos arch;
    
    @Inject
    private Control_Notificaciones CN;
    
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

    public String necesitaDocumentos() throws SeccionInexistenteException{
        
        if(necdoc){
            switch (seccioncrear) {
            case "Castores":
                crear.setSeccion(seccion.getSeccion(1L));
                break;
            case "Lobatos":
                crear.setSeccion(seccion.getSeccion(2L));
                break;
            case "Scouts":
                crear.setSeccion(seccion.getSeccion(3L));
                break;
            case "Escultas":
                crear.setSeccion(seccion.getSeccion(4L));
                break;
            case "Rovers":
                crear.setSeccion(seccion.getSeccion(5L));
                break;
            default:
                break;
            }
        
            crear.setId(evento.idMax());
            arch.setCrear(crear);
            
            return "anadirDoc.xhtml";
        } else {
            return CrearEvento();
        }
        
    }
    
    public String CrearEvento() throws SeccionInexistenteException {
        
        switch (seccioncrear) {
            case "Castores":
                crear.setSeccion(seccion.getSeccion(1L));
                break;
            case "Lobatos":
                crear.setSeccion(seccion.getSeccion(2L));
                break;
            case "Scouts":
                crear.setSeccion(seccion.getSeccion(3L));
                break;
            case "Escultas":
                crear.setSeccion(seccion.getSeccion(4L));
                break;
            case "Rovers":
                crear.setSeccion(seccion.getSeccion(5L));
                break;
            default:
                break;
        }
        
        crear.setId(evento.idMax());
        evento.insertar(crear);
        
       // CN.addNotificame(crear);  
        
        crear = new Evento();
        seccioncrear = null;

        return "Lista_eventos.xhtml";
    }

    public String cancelarcrear() {
        
        crear = new Evento();
        seccioncrear = null;

        return "Lista_eventos.xhtml";
    }

    public String verEvento(Long id) throws EventoException {

        event=buscarEvento(id);

        return "Eventos.xhtml";
    }
    
    public List<Evento> verEventos(){
        return evento.verEventos();
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

    public boolean isNecdoc() {
        return necdoc;
    }

    public void setNecdoc(boolean necdoc) {
        this.necdoc = necdoc;
    }

    public Eventos getEvento() {
        return evento;
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }

    public Seccionesb getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccionesb seccion) {
        this.seccion = seccion;
    }

    public Control_Notificaciones getCN() {
        return CN;
    }

    public void setCN(Control_Notificaciones CN) {
        this.CN = CN;
    }

    public Evento getCrear() {
        return crear;
    }

    public void setCrear(Evento crear) {
        this.crear = crear;
    }

    public Archivos getArch() {
        return arch;
    }

    public void setArch(Archivos arch) {
        this.arch = arch;
    }
}
