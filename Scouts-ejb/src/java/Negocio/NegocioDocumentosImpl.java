/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Documento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author migue
 */
@Stateless
public class NegocioDocumentosImpl implements NegocioDocumentos{

    @PersistenceContext(unitName = "Scouts-EntidadesPU")
    private EntityManager em;
    
   
    @Override
    public void agnadirDocumento(Documento doc) {
        em.persist(doc);
    }

    @Override
    public void eliminarDocumento(Documento doc) {
        if(em.contains(doc))
            em.remove(em.merge(doc));
    }
    
    @Override
    public void modificarDocumento(Documento doc) {
        if(em.contains(doc))
            em.merge(doc);
    }

    @Override
    public Documento buscarDocumento(Documento doc) {
        Documento docc = em.find(Documento.class, doc.getId());
        return docc;
    }
    
    @Override
    public List<Documento> verDocumentos(){
        Query list = em.createQuery("SELECT doc FROM Documento doc");
        return list.getResultList();
    }
    
    
    
    
    
}
