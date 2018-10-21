/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.Login;
import modelo.Notificacion;
import modelo.Persona;

/**
 * REST Web Service
 *
 * @author 415-pc-profe
 */
@Path("login")
public class LoginResource {

    @Context
    private UriInfo context;
    
    private Connection con;
    
    /**
     * Constructor
     */
    public LoginResource() throws SQLException{
        con = Conexion.getConexion();
    }
    
    /**
     * Metodo que inicia sesion en la aplicacion
     * @param username, usuario con el que se iniciara sesion
     * @param contrasenia
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("login/{username}/{contrasenia}")
    public Login login(@PathParam("username") String username,
            @PathParam("contrasenia") String contrasenia) {
          try {
            Login login = new Login();
            //Llamo la conexion y le envio la consulta a la base de datos
            Connection con = Conexion.getConexion();
            String sql = "SELECT l.username,l.contrasenia,l.persona_cedula"
                    + " FROM login l WHERE username = ? and contrasenia = ? and activo = 1 ;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, contrasenia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                login.setUsername(rs.getString(1));
                login.setContrasenia(rs.getString(2));
                Persona p = new Persona();
                p.setCedula(rs.getString(3));
                login.setPersona_cedula(p);
            }
            con.close();
            return login;
            //return buildResponse(listaPersonas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * Metodo que busca un login por una persona
     * @param persona, cedula de la persona por la cual se buscara el login
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("usuario-by-persona/{persona}")
    public Login loginByPersona(@PathParam("persona") String persona) {
          try {
            Login login = new Login();
            //Llamo la conexion y le envio la consulta a la base de datos
            Connection con = Conexion.getConexion();
            String sql = "SELECT * FROM login WHERE persona_cedula = ? ;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, persona);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                login.setUsername(rs.getString(1));
                login.setContrasenia(rs.getString(2));
                Persona p = new Persona();
                p.setCedula(rs.getString(3));
                login.setPersona_cedula(p);
            }
            con.close();
            return login;
            //return buildResponse(listaPersonas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
  
}
