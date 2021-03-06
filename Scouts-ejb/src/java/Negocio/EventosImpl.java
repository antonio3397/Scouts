package Negocio;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author franc
 */
import clases.Evento;
import clases.Seccion;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class EventosImpl implements Eventos{
    
    @PersistenceContext(unitName = "Scouts-EntidadesPU")
    private EntityManager em;
    
    @Override
    public void insertar(Evento e) {
        if(!estaEvento(e))em.persist(e);
        else throw new eventoYaCreadoException("El evento ya está creado");
    }

    @Override
    public void modificar(Evento e) {
        em.merge(e);
    }

    @Override
    public void eliminar(Evento e) {
        em.remove(em.merge(e));    
    }

    @Override
    public Evento obtenerEvento(Long id) {
        return em.find(Evento.class, id);
    }
    
    @Override
    public List<Evento> verEventos() {
        Query list = em.createQuery("SELECT e FROM Evento e");
        return list.getResultList();
    }
    
     @Override
    public List<Evento> verEventos(Seccion sc) {
        List<Evento> lista = verEventos();
        List<Evento> listaSeccion = new ArrayList<>();
        for(Evento event: lista)
            if(event.getSeccion().equals(sc))
                listaSeccion.add(event);
        return listaSeccion;
    }
    
    private boolean estaEvento(Evento e){
        List<Evento> lista = verEventos();
        Iterator it = lista.iterator();
        boolean esta = false;
        while(it.hasNext()&&!esta){
            Evento aux = (Evento) it.next();
            if(aux.getTitulo().equals(e.getTitulo())&&aux.getFecha().equals(e.getFecha())&&aux.getSeccion().equals(e.getSeccion()))esta=true;
        }
        return esta;
    }
}
