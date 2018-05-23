/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

/**
 *
 * @author josealonso
 */
public class Registro_Excepcion extends Exception {
    
    public Registro_Excepcion(){
        
    }
    
    public Registro_Excepcion(String msg){
        super(msg);
    }
    
}