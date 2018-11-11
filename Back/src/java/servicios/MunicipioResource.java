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
import modelo.Municipio;

/**
 * REST Web Service
 *
 * @author Vanegas
 */
@Path("municipio")
public class MunicipioResource {

    @Context
    private UriInfo context;
    
    private Connection con;
    
    /**
     * Constructor
     */
    public MunicipioResource() throws SQLException{
        con = Conexion.getConexion();
    }
    
    /**
     * Metodo que lista todos los municipios
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list/{id}")
    public List<Municipio> listarDepartamentos(@PathParam("id") int id) {
          try {
            List<Municipio> listaMunicipios = new ArrayList<Municipio>();
            //Llamo la conexion y le envio la consulta a la base de datos
            Connection con = Conexion.getConexion();
            String sql = "SELECT m.id,m.nombre FROM municipio m WHERE m.departamento_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Municipio muni = new Municipio();
                muni.setId(rs.getInt(1));
                muni.setNombre(rs.getString(2));
                listaMunicipios.add(muni);
            }
            con.close();
            return listaMunicipios;
            //return buildResponse(listaPersonas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * Metodo que busca un municipio por un id
     * @param id, id por el cual se buscara el municipio
     * @return el municipio si lo encuentra, en caso contrario null
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search/{id}")
    public Municipio buscarMunicipio(@PathParam("id") int id) {
          try {
            Municipio muni = new Municipio();
            //Llamo la conexion y le envio la consulta a la base de datos
            Connection con = Conexion.getConexion();
            String sql = "SELECT m.id,m.nombre FROM municipio m WHERE m.id = ? ;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                muni.setId(rs.getInt(1));
                muni.setNombre(rs.getString(2));
            }
            con.close();
            return muni;
            //return buildResponse(listaPersonas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
  
}
