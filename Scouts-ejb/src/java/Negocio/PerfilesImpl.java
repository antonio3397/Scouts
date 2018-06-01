/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Perfil;
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
public class PerfilesImpl implements Perfiles {

    @PersistenceContext(unitName = "Scouts-EntidadesPU")
    private EntityManager em;
    
    @Override
    public List<Perfil> getPerfiles(){
        List<Perfil> p = new ArrayList<>();
        Query q = em.createQuery("SELECT p FROM Perfil p");
        p = q.getResultList();
        return p;
    } 
    
    @Override
    public Perfil getPerfil(Perfil.Rol id) throws PerfilInexistenteException{
        Perfil perf = em.find(Perfil.class, id);
        if(perf==null){
            throw new PerfilInexistenteException();
        }
        return perf;
    } 
}
