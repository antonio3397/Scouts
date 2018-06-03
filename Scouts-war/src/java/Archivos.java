
import Negocio.CuentaExistenteException;
import Negocio.DocumentoNoExistenteException;
import Negocio.Eventos;
import Negocio.NegocioDocumentos;
import Negocio.Usuarios;
import clases.Documento;
import clases.Evento;
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
    private UploadedFile archivo=null;
    private UploadedFile imagen=null;
    
    @EJB
    private NegocioDocumentos nd;
    
    @EJB
    private Eventos ev;
    
    @EJB
    private Usuarios us;
    
    @Inject
    private MiSesion ms;
    
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
        
        return "Lista_eventos.xhtml";
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
    
    public String cancelarEvento(){
        archivo = null;
        return "CrearEvento.xhtml";
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
}