
import Negocio.CuentaExistenteException;
import Negocio.DocumentoNoExistenteException;
import Negocio.Eventos;
import Negocio.NegocioDocumentos;
import Negocio.Usuarios;
import clases.Documento;
import clases.Evento;
import clases.Usuario;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
 
@Named(value = "archivos")
@SessionScoped
public class Archivos implements Serializable{
     
    private Evento crear;
    private Documento doc = new Documento();
    private UploadedFile archivo;
    private UploadedFile imagen;
    private UploadedFile relleno;
    private List<Documento> listd;
    
    @EJB
    private NegocioDocumentos nd;
    
    @EJB
    private Eventos ev;
    
    @EJB
    private Usuarios us;
    
    @Inject
    private MiSesion ms;
    
    @Inject
    private Control_Notificaciones CN;
    
    public StreamedContent bajarArchivos(Evento e) throws DocumentoNoExistenteException{
        List<Documento> ld = nd.verDocumentos();
        StreamedContent docum = null;
        for(Documento d : ld){
            if(d.getEvento().equals(e) && d.getTipo().equals("Documento a rellenar")){
                docum = new DefaultStreamedContent(new ByteArrayInputStream(d.getDocumento()),"archivo de texto", d.getNombre());
                break;
            }
        }
        if(docum==null){
            throw new DocumentoNoExistenteException();
        }
        
        return docum;
    }
    
    public StreamedContent bajarArchivosDoc(Documento doc) throws DocumentoNoExistenteException{
        
        StreamedContent docum = null;
        docum = new DefaultStreamedContent(new ByteArrayInputStream(doc.getDocumento()),"archivo de texto", doc.getNombre());
        
        if(docum==null){
            throw new DocumentoNoExistenteException();
        }
        
        return docum;
    }
    
    public String crearEvento() throws CuentaExistenteException, IOException{
        
        InputStream is = archivo.getInputstream();
        byte[] buffer = new byte[(int) archivo.getSize()];
        is.read(buffer);
        
        Long idcrear;
        
        if(!nd.verDocumentos().isEmpty()){
            idcrear = nd.verDocumentos().get(nd.verDocumentos().size()-1).getId()+1;
        } else {
            idcrear = 10L;
        }
        //Crear Documento
        doc.setId(idcrear);
        doc.setNombre(archivo.getFileName());
        doc.setDocumento(buffer);
        doc.setTipo("Documento a rellenar");
        doc.setFecha_entrega(new Date());
        //Inserta el evento
        crear.setDocumentos(new ArrayList<>());
        ev.insertar(crear);
        //Inserta documento
        doc.setEvento(crear);
        doc.setUsuario(ms.getUser());
        nd.agnadirDocumento(doc);
        //Añade documento a la lista de documentos del usuario
        ms.getUser().getDocumentos().add(doc);
        us.modificarUsuario(ms.getUser());
        //Añade documento a la lista de documentos del evento
        crear.getDocumentos().add(doc);
        ev.modificar(crear);
        
        archivo=null;
        doc = new Documento();
        crear = new Evento();
        listd = nd.verDocumentos();
        
        CN.crearNotificacion(crear);
        
        return "Lista_eventos.xhtml";
    }
    
    public String cancelarEvento(){
        archivo = null;
        return "CrearEvento.xhtml";
    }
    
    public byte[] GuardarImagen() throws IOException{
        
        InputStream is = imagen.getInputstream();
        byte[] buffer = new byte[(int) imagen.getSize()];
        is.read(buffer);
        
        imagen = null;
        
        return buffer;
    }
    
    public StreamedContent verImagen(Evento e){
        StreamedContent img = new DefaultStreamedContent(new ByteArrayInputStream(e.getImagen()),"Imagen", e.getNombreImagen());
        
        return img;
    }
    
    public String subirArchivoRelleno(Evento e) throws IOException, CuentaExistenteException{
        
        InputStream is = relleno.getInputstream();
        byte[] buffer = new byte[(int) relleno.getSize()];
        is.read(buffer);
        Long idcrear;
        
        if(!nd.verDocumentos().isEmpty()){
            idcrear = nd.verDocumentos().get(nd.verDocumentos().size()-1).getId()+1;
        } else {
            idcrear = 10L;
        }
        
        //Crear Documento
        doc.setId(idcrear);
        doc.setNombre(relleno.getFileName());
        doc.setDocumento(buffer);
        doc.setTipo("Documento relleno");
        doc.setFecha_entrega(new Date());
        //Inserta documento
        doc.setEvento(e);
        doc.setUsuario(ms.getUser());
        nd.agnadirDocumento(doc);
        //Añade documento a la lista de documentos del usuario
        ms.getUser().getDocumentos().add(doc);
        us.modificarUsuario(ms.getUser());
        //Añade documento a la lista de documentos del evento
        e.getDocumentos().add(doc);
        ev.modificar(e);
        
        doc=new Documento();
        
        return "Eventos.xhtml";
    }
    
    public String borrarArchivo(Documento doc) throws CuentaExistenteException{
        
        Usuario user = doc.getUsuario();
        user.getDocumentos().remove(doc);
        us.modificarUsuario(user);
        Evento e = doc.getEvento();
        e.getDocumentos().remove(doc);
        ev.modificar(e);
        doc.setEvento(null);
        doc.setUsuario(null);
        
        nd.eliminarDocumento(doc);
        listd = nd.verDocumentos();
        
        return "Lista_documentos.xhtml";
    }

    public Evento getCrear() {
        return crear;
    }

    public void setCrear(Evento crear) {
        this.crear = crear;
    }

    public Documento getDoc() {
        return doc;
    }

    public void setDoc(Documento doc) {
        this.doc = doc;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public NegocioDocumentos getNd() {
        return nd;
    }

    public void setNd(NegocioDocumentos nd) {
        this.nd = nd;
    }

    public Eventos getEv() {
        return ev;
    }

    public void setEv(Eventos ev) {
        this.ev = ev;
    }

    public Usuarios getUs() {
        return us;
    }

    public void setUs(Usuarios us) {
        this.us = us;
    }

    public MiSesion getMs() {
        return ms;
    }

    public void setMs(MiSesion ms) {
        this.ms = ms;
    }

    public UploadedFile getImagen() {
        return imagen;
    }

    public void setImagen(UploadedFile imagen) {
        this.imagen = imagen;
    }

    public List<Documento> getListd() {
        return listd;
    }

    public void setListd(List<Documento> listd) {
        this.listd = listd;
    }

    public UploadedFile getRelleno() {
        return relleno;
    }

    public void setRelleno(UploadedFile relleno) {
        this.relleno = relleno;
    }
}