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
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Stateless
public class EventosImpl implements Eventos{
    private EntityManager em;
    @Override
    public void insertar(Evento e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(Evento e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(Evento e) {
        em.remove(e);    
    }

    @Override
    public Evento obtenerEvento(Long id) {
        return em.find(Evento.class, id);
    }
    
    @Override
    public List<Evento> obtenerEventos() {
        Query list = em.createNativeQuery("SELECT e FROM Evento e");
        return list.getResultList();
    }
    
    private boolean estaEvento(Evento e){
        List<Evento> lista = obtenerEventos();
        Iterator it = lista.iterator();
        boolean esta = false;
        while(it.hasNext()&&!esta){
            Evento aux = (Evento) it.next();
            if(aux.getTitulo().equals(e.getTitulo()))esta=true;
        }
        return false;
    }
}
