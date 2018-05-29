/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Evento;
import clases.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author DavidDR
 */
@Local
public interface Inscripciones {

    public List<Evento> inscripciones(Long id);
}
