/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import clases.Documento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author migue
 */
@ManagedBean(name = "Documentos")
@SessionScoped
public class Control_Documentos {

    private List<Documento> documentosj;
    private String document;

    @PostConstruct

    public void init() {
        documentosj = new ArrayList<>();
        documentosj.add(new Documento(100L, "Completo", "pdf", new Date(2018 - 1900, 5, 2)));
        documentosj.add(new Documento(101L, "A completar", "pdf", new Date(2018 - 1900, 3, 1)));
        documentosj.add(new Documento(102L, "Modificado", "pdf", new Date(2018 - 1900, 2, 4)));
    }

    public List<Documento> getDocumentosj() {
        return documentosj;
    }

    public void setDocumentosj(List<Documento> documentosj) {
        this.documentosj = documentosj;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

}
