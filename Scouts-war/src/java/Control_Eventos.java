
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Negocio.Comentarios;
import Negocio.CuentaExistenteException;
import Negocio.Eventos;
import Negocio.NegocioDocumentos;
import Negocio.SeccionInexistenteException;
import Negocio.Seccionesb;
import Negocio.Usuarios;
import clases.Comentario;
import clases.Documento;
import clases.Evento;
import clases.Perfil;
import clases.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private boolean necdoc;
    private Evento aux;
    private String seccionMod="a";
    
    @EJB
    private Eventos evento;
    
    @EJB
    private Usuarios us;
    
    @EJB
    private Seccionesb seccion;
    
    @EJB
    private NegocioDocumentos nd;
    
    @EJB
    private Comentarios comen;
    
    @Inject
    private Archivos arch;
    
    @Inject
    private MiSesion ms;
    
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
    
    public String aceptarMod() throws EventoException, SeccionInexistenteException, IOException, CuentaExistenteException {
        
        switch (seccionMod) {
        case "Castores":
            aux.setSeccion(seccion.getSeccion(1L));
            break;
        case "Lobatos":
            aux.setSeccion(seccion.getSeccion(2L));
            break;
        case "Scouts":
            aux.setSeccion(seccion.getSeccion(3L));
            break;
        case "Escultas":
            aux.setSeccion(seccion.getSeccion(4L));
            break;
        case "Rovers":
            aux.setSeccion(seccion.getSeccion(5L));
            break;
        default:
            break;
        }
            
        arch.setCrear(aux);
        return arch.modificarEvento();
        
    }
    
    public String cancelarMod() {
        
        aux=null;
        arch.setArchivo(null);
        arch.setImagen(null);
        
        return "Eventos.xhtml";
    }
    
    public String borrarEvento(Long id) throws EventoException, CuentaExistenteException{
        Evento b = evento.obtenerEvento(id);
        Evento auxs = evento.obtenerEvento(id);
        List<Comentario> com = new ArrayList<>(b.getComentarios());
        //Borra documentos
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
            arch.setListd(nd.verDocumentos());
        }
        //Borra comentarios
        if(!b.getComentarios().isEmpty()){
            for(Comentario c: com){
                comen.eliminar(c.getId());
                b.getComentarios().remove(c);
            }
        }
        b.setComentarios(com);
        //Borra inscripciones
        if(!b.getUsuarios().isEmpty()){
            for(Usuario u: b.getUsuarios()){
                u.getEventos().remove(b);
                us.modificarUsuario(u);
            }
        }
        CN.borrarNotificacionesEvento(b);
        //Borra evento
        b.setDocumentos(null);
        b.setComentarios(null);
        b.setUsuarios(null);
        evento.eliminar(b);
        eventosj = evento.verEventos();
        refrescarEventosj2();
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
        
            Long idcrear = evento.verEventos().get(evento.verEventos().size()-1).getId()+1;
            crear.setId(idcrear);
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
        
        Long idcrear;
        
        if(evento.verEventos().isEmpty()){
            idcrear=1L;
        } else {
            idcrear = evento.verEventos().get(evento.verEventos().size()-1).getId()+1;
        }
        
        crear.setId(idcrear);
        evento.insertar(crear);
        
        CN.crearNotificacion(crear);  
        
        crear = new Evento();
        seccioncrear = null;
        eventosj = evento.verEventos();
        refrescarEventosj2();

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
    
    public boolean tieneImagen(Evento e){
        return e.getImagen()!=null;
    }
    
    public boolean tieneDocumento(Evento e){
        return !e.getDocumentos().isEmpty();
    }
    
    public List<Evento> verEventos(){
        return eventosj2;
    }
    
    public void refrescarEventosj2(){
        
        List<Evento> events2 = new ArrayList<>();
        
        if (ms.getUser().getPerfiles().getRol().equals(Perfil.Rol.COORDGEN)) {
                for (Evento e : eventosj) {
                        events2.add(e);
                }
            } else {
                for (Evento e : eventosj) {
                    if (ms.getUser().getSeccion().equals(e.getSeccion())) {
                        events2.add(e);
                    }
                }
            }

            eventosj2 = events2;
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
