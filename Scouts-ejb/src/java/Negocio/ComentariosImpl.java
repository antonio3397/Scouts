/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

/**
 *
 * @author franc
 */

import clases.Comentario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Stateless
public class ComentariosImpl implements Comentarios{
    private EntityManager em;

    @Override
    public void insertar(Comentario c) {
        em.persist(c);
    }

    @Override
    public void modificar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Comentario> verComentarios() {
        Query list = em.createNativeQuery("SELECT c FROM Comentario c");
        return list.getResultList();
    }
    
}
