/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author 415-pc-profe
 */
public class Respuesta implements Serializable{
    
   private int id;
   private String msj;

   
    public Respuesta() {
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public Respuesta(int id, String msj) {
        this.id = id;
        this.msj = msj;
    }    
    
}
