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
import clases.Evento;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ComentariosImpl implements Comentarios{
    
    @PersistenceContext(unitName = "Scouts-EntidadesPU")
    private EntityManager em;

    @Override
    public void insertar(Comentario c) {
        em.persist(c);
    }

    @Override
    public void modificar(Comentario c) {
        em.merge(c);
    }

    @Override
    public void eliminar(Long id) {
        Comentario c = buscarComentario(id);
        em.remove(c);
    }

    @Override
    public List<Comentario> verComentarios(Evento event) {
        Query list = em.createNativeQuery("SELECT c FROM Comentario c");
        List<Comentario> comments = new ArrayList<>();
        for(Comentario aux : (List<Comentario>)list.getResultList()){
            if(aux.getEvento().equals(event))comments.add(aux);
        }
        return comments;
    }

    @Override
    public Comentario buscarComentario(Long id) {
        return em.find(Comentario.class, id);
    }
    
}
