/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import clases.Comentario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author franc
 */
@Local
public interface Comentarios {
    public void insertar(Comentario c);
    public void modificar();
    public void eliminar();
    public List<Comentario> verComentarios();
}
