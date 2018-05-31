
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.model.UploadedFile;
 




@ManagedBean
public class subirArchivo {
     
   
    
    private byte[] archivo;
    private UploadedFile file;
    private String[] extensions= {"pdf","img","doc","jpg","png","docx"};
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() throws IOException {
        if(file != null) {
            FacesMessage message = new FacesMessage("¡Genial!", file.getFileName() + " lo hemos recibido correctamente.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            int post = file.getFileName().indexOf(".");
            String extension=file.getFileName().substring(post);
            
            List<String> extens=Arrays.asList(extensions);
       
            if(extens.contains(extension)){
                 InputStream filee=file.getInputstream();
            byte[] buffer= new byte[(int) file.getSize()];
            int readers = filee.read(buffer);
            archivo=buffer;
                
            }
            else throw new IOException();

        }else throw new IOException();
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
}