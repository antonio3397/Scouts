package controladores;
 
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class subirArchivo {
     
    private UploadedFile file;
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("¡Genial!", file.getFileName() + " lo hemos recibido correctamente.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}