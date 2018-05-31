/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Seccion;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author anton
 */
@Stateless
public class SeccionesImpl implements Seccionesb {

    @PersistenceContext(unitName = "Scouts-EntidadesPU")
    private EntityManager em;
    
    @Override
    public List<Seccion> getSecciones(){
        List<Seccion> sec = new ArrayList<>();
        Query q = em.createQuery("SELECT s FROM Seccion s");
        sec = q.getResultList();
        return sec;
    } 
    
}
