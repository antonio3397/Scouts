/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Responsable_Legal;
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
public class ResponsableImpl implements Responsable {

    @PersistenceContext(unitName = "Scouts-EntidadesPU")
    private EntityManager em;
    
    @Override
    public Responsable_Legal getResponsable(Long id) throws ResponsableInexistenteException{
        Responsable_Legal res = em.find(Responsable_Legal.class, id);
        if(res==null){
            throw new ResponsableInexistenteException();
        }
        return res;
    }
    
    @Override
    public List<Responsable_Legal> getResponsables(){
        List<Responsable_Legal> resps = new ArrayList<>();
        Query q = em.createQuery("SELECT r FROM Responsable_Legal r");
        resps = q.getResultList();
        return resps;
    }
    
    @Override
    public void registrarResponsable(Responsable_Legal r) throws ResponsableExistenteException{
        if(!estaResponsable(r)){
            em.persist(r);
        } else {
            throw new ResponsableExistenteException();
        }
    }
    
    @Override
    public boolean estaResponsable(Responsable_Legal r){
        return em.find(Responsable_Legal.class, r.getId())!=null;
    }
    
    @Override
    public boolean estaResponsableNIF(Responsable_Legal r){
        Query q = em.createQuery("SELECT r FROM Responsable_Legal r WHERE r.NIF LIKE '"+r.getNIF()+"'");
        List<Responsable_Legal> resps = q.getResultList();
        return resps.size()!=0;
    }
    
    @Override
    public Responsable_Legal refrescarResponsable(Responsable_Legal r){
        Query q = em.createQuery("SELECT r FROM Responsable_Legal r WHERE r.NIF LIKE '"+r.getNIF()+"'");
        List<Responsable_Legal> resps = q.getResultList();
        Responsable_Legal res = resps.get(0);
        em.refresh(res);
        return res;
    }
    
    @Override
    public void modificarResponsable(Responsable_Legal r) throws ResponsableExistenteException{
        if(estaResponsable(r)){
            em.merge(r);
        } else {
            throw new ResponsableExistenteException();
        }
    }
    
    @Override
    public void eliminarResponsable(Responsable_Legal r) throws ResponsableInexistenteException{
        if(estaResponsable(r)){
            em.remove(em.merge(r));
        } else {
            throw new ResponsableInexistenteException();
        }
        
    }
    
}
