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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelo.Login;
import modelo.Persona;
import modelo.Rol;

/**
 * REST Web Service
 *
 * @author 415-pc-profe
 */
@Path("rol")
public class RolResource {

    @Context
    private UriInfo context;
    
    private Connection con;
    
    /**
     * Constructor
     */
    public RolResource() throws SQLException{
        con = Conexion.getConexion();
    }
    
    /**
     * Metodo que lista todos los roles
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("listar")
    public List<Rol> listarRoles() {
          try {
            List<Rol> listaRoles = new ArrayList<Rol>();
            //Llamo la conexion y le envio la consulta a la base de datos
            Connection con = Conexion.getConexion();
            String sql = "SELECT r.id,r.nombre,r.descripcion FROM rol r";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rol rol = new Rol();
                rol.setId(rs.getInt(1));
                rol.setNombre(rs.getString(2));
                rol.setDescripcion(rs.getString(3));
                listaRoles.add(rol);
            }
            con.close();
            return listaRoles;
            //return buildResponse(listaPersonas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * Metodo que busca un rol por un id
     * @param id, id por el cual se buscara el rol
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("rol-by-id/{id}")
    public Rol rolById(@PathParam("id") int id) {
          try {
            Rol rol = new Rol();
            //Llamo la conexion y le envio la consulta a la base de datos
            Connection con = Conexion.getConexion();
            String sql = "SELECT * FROM rol WHERE id = ? ;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rol.setId(rs.getInt(1));
                rol.setNombre(rs.getString(2));
                rol.setDescripcion(rs.getString(3));
            }
            con.close();
            return rol;
            //return buildResponse(listaPersonas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
  
}
