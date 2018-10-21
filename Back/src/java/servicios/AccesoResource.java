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
import modelo.Acceso;
import modelo.AccesoRol;
import modelo.Login;
import modelo.Persona;
import modelo.Rol;

/**
 * REST Web Service
 *
 * @author 415-pc-profe
 */
@Path("acceso")
public class AccesoResource {

    @Context
    private UriInfo context;
    
    private Connection con;
    
    /**
     * Constructor
     */
    public AccesoResource() throws SQLException{
        con = Conexion.getConexion();
    }
    
    /**
     * Metodo que lista todos los roles
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("listar")
    public List<Acceso> listarAccesos() {
          try {
            List<Acceso> listaAccesos = new ArrayList<Acceso>();
            //Llamo la conexion y le envio la consulta a la base de datos
            Connection con = Conexion.getConexion();
            String sql = "SELECT a.id,a.nombre,a.url FROM acceso a";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Acceso acceso = new Acceso();
                acceso.setId(rs.getInt(1));
                acceso.setNombre(rs.getString(2));
                acceso.setUrl(rs.getString(3));
                listaAccesos.add(acceso);
            }
            con.close();
            return listaAccesos;
            //return buildResponse(listaPersonas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * Metodo que busca un acceso por un id
     * @param id, id por el cual se buscara el acceso
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("acceso-by-id/{id}")
    public Acceso rolById(@PathParam("id") int id) {
          try {
            Acceso acceso = new Acceso();
            //Llamo la conexion y le envio la consulta a la base de datos
            Connection con = Conexion.getConexion();
            String sql = "SELECT a.id,a.nombre,a.url FROM acceso WHERE id = ? ;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                acceso.setId(rs.getInt(1));
                acceso.setNombre(rs.getString(2));
                acceso.setUrl(rs.getString(3));
            }
            con.close();
            return acceso;
            //return buildResponse(listaPersonas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * Metodo que lista todos accesos de un rol
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("por-rol/{rol}")
    public List<Acceso> accesosPorRol(@PathParam("rol") int rol) {
          try {
            List<Acceso> listaAccesos = new ArrayList<Acceso>();
            //Llamo la conexion y le envio la consulta a la base de datos
            Connection con = Conexion.getConexion();
            String sql = "SELECT a.id,a.nombre,a.url FROM acceso_rol ar JOIN acceso a "
                    + "ON a.id = ar.acceso_id WHERE ar.rol_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
             ps.setInt(1, rol);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Acceso acceso = new Acceso();
                acceso.setId(rs.getInt(1));
                acceso.setNombre(rs.getString(2));
                acceso.setUrl(rs.getString(3));
                listaAccesos.add(acceso);;
            }
            con.close();
            return listaAccesos;
            //return buildResponse(listaPersonas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
  
}
