
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Negocio.CuentaExistenteException;
import Negocio.Eventos;
import Negocio.NegocioDocumentos;
import Negocio.SeccionInexistenteException;
import Negocio.Seccionesb;
import Negocio.Usuarios;
import clases.Documento;
import clases.Evento;
import clases.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private Usuarios us;
    
    @EJB
    private Seccionesb seccion;
    
    @EJB
    private NegocioDocumentos nd;
    
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
    
    public String borrarEvento(Long id) throws EventoException, CuentaExistenteException{
        Evento b = evento.obtenerEvento(id);
        Evento auxs = evento.obtenerEvento(id);
        if(!b.getDocumentos().isEmpty()){
            for(Documento d : auxs.getDocumentos()){
                b.getDocumentos().remove(d);
                Usuario mod = d.getUsuario();
                mod.getDocumentos().remove(d);
                us.modificarUsuario(mod);
                d.setUsuario(null);
                d.setEvento(null);
                nd.eliminarDocumento(d);
            }
        }
        b.setDocumentos(null);
        evento.eliminar(b);
        return "Lista_eventos.xhtml";
    }

    public String necesitaDocumentos() throws SeccionInexistenteException, IOException, CuentaExistenteException{
        
        if(!arch.getArchivo().getFileName().equals("")){
            
            if(!arch.getImagen().getFileName().equals("")){
                crear.setNombreImagen(arch.getImagen().getFileName());
                crear.setImagen(arch.GuardarImagen());
            }
            
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
            
            return arch.crearEvento();
        } else {
            return CrearEvento();
        }
        
    }
    
    public String CrearEvento() throws SeccionInexistenteException, IOException {
        
        if(!arch.getImagen().getFileName().equals("")){
            crear.setNombreImagen(arch.getImagen().getFileName());
            crear.setImagen(arch.GuardarImagen());
        }
        
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
    
    public boolean isDocumentos(){
        return !event.getDocumentos().isEmpty();
    }
    
    public boolean isImagen(){
        return event.getImagen()!=null;
    }
    
    public String cambioFormato(Date fecha){
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        return formateador.format(fecha);
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
