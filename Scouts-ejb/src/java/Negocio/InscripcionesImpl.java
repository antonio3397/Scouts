/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Evento;
import clases.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author DavidDR
 */
@Stateless
public class InscripcionesImpl implements Inscripciones {

    @PersistenceContext(unitName = "Scouts-EntidadesPU")
    private EntityManager em;

    @Override
    public List<Evento> inscripciones(Long id) {
        return em.createNativeQuery("SELECT inscripciones FROM Usuario u WHERE u.id = '" + id + "'").getResultList();
    }

}
