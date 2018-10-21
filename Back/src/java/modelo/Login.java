/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author Vanegas
 */
public class Login implements Serializable{
    
    private String username;
    private String contrasenia;
    private Persona persona_cedula;

    public Login() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Persona getPersona_cedula() {
        return persona_cedula;
    }

    public void setPersona_cedula(Persona persona_cedula) {
        this.persona_cedula = persona_cedula;
    }
    
    
    
}
