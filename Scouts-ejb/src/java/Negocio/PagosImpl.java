/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Pago_cuota;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Miguel
 */
public class PagosImpl implements Pagos {
    @PersistenceContext(unitName = "Scouts-EntidadesPU")
    private EntityManager em;
    private static Long id=0L;
    @Override
    public List<Pago_cuota> getPagos(Long id) {
        List<Pago_cuota> pgs=new ArrayList<>();
        Query q=em.createQuery("select p from Pago_cuota p where usuarios_id ="+id);
        pgs=q.getResultList();
        return pgs;
    }

    @Override
    public void modificarPago(Long id) {
        Pago_cuota p;
        Query q=em.createQuery("select p from pago_cuota where id="+id);
        p=(Pago_cuota)q.getResultList().toArray()[0];
        //no esta terminado
    }

    @Override
    public void nuevoPago(Pago_cuota p) {
        em.persist(p);
    }

    @Override
    public void eliminarPago(Long id) {
        //Suponemos que existe
        Pago_cuota p=buscarPago(id);
        em.remove(em.merge(p));
    }
    @Override
    public Pago_cuota buscarPago(Long id){
        return em.find(Pago_cuota.class, id);
    }
    @Override
    public Long idMax(){
        increase();
        return id;

    }
    private void increase() {
        id+=1L;
    }
}
