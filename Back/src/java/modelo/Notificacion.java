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
public class Notificacion implements Serializable{
    String metodo;
    String resultado;

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Notificacion(String metodo, String resultado) {
        this.metodo = metodo;
        this.resultado = resultado;
    }

    public Notificacion() {
    }
    
    
}
