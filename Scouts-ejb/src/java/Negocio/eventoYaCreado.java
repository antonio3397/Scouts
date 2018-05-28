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
public class eventoYaCreado extends RuntimeException {

    public eventoYaCreado() {
    }
    public eventoYaCreado(String msg) {
        super(msg);
    }
}
