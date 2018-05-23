/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

/**
 *
 * @author migue
 */
public class Perfil_Excepcion extends Exception {
    
    public Perfil_Excepcion(){
        
    }
    
    public Perfil_Excepcion(String msg){
        super(msg);
    }
}
