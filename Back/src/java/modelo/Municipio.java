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
public class Municipio implements Serializable{
    
    private int id;
    private String nombre;
    private Departamento departamento_id;

    public Municipio() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Departamento getDepartamento_id() {
        return departamento_id;
    }

    public void setDepartamento(Departamento departamento_id) {
        this.departamento_id = departamento_id;
    }
    
}
