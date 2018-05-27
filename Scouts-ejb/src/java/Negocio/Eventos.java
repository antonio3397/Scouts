/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Evento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author franc
 */
@Local
public interface Eventos {
    public void insertar (Evento e);
    public void modificar (Evento e);
    public void eliminar (Evento e);
    public Evento obtenerEvento(Long id);
    public List<Evento> obtenerEventos();
}
