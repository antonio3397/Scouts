/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import javax.ejb.Local;
import clases.Documento;
import java.util.List;
/**
 *
 * @author migue
 */
@Local
public interface NegocioDocumentos {

    public void agnadirDocumento(Documento doc);
    public void eliminarDocumento(Documento doc);
    public Documento buscarDocumento(Documento doc);
    public void modificarDocumento(Documento doc);
    public List<Documento> verDocumentos();
     
}


