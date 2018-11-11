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
import modelo.Departamento;

/**
 * REST Web Service
 *
 * @author Vanegas
 */
@Path("departamento")
public class DepartamentoResource {

    @Context
    private UriInfo context;
    
    private Connection con;
    
    /**
     * Constructor
     */
    public DepartamentoResource() throws SQLException{
        con = Conexion.getConexion();
    }
    
    /**
     * Metodo que lista todos los departamentos
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public List<Departamento> listarDepartamentos() {
          try {
            List<Departamento> listaDepartamentos = new ArrayList<Departamento>();
            //Llamo la conexion y le envio la consulta a la base de datos
            Connection con = Conexion.getConexion();
            String sql = "SELECT d.id,d.nombre FROM departamento d";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Departamento depto = new Departamento();
                depto.setId(rs.getInt(1));
                depto.setNombre(rs.getString(2));
                listaDepartamentos.add(depto);
            }
            con.close();
            return listaDepartamentos;
            //return buildResponse(listaPersonas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * Metodo que busca un departamento por un id
     * @param id, id por el cual se buscara el departamento
     * @return el departamento si lo encuentra, en caso contrario null
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search/{id}")
    public Departamento buscarDepartamento(@PathParam("id") int id) {
          try {
            Departamento depto = new Departamento();
            //Llamo la conexion y le envio la consulta a la base de datos
            Connection con = Conexion.getConexion();
            String sql = "SELECT d.id,d.nombre FROM departamento d WHERE d.id = ? ;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                depto.setId(rs.getInt(1));
                depto.setNombre(rs.getString(2));
            }
            con.close();
            return depto;
            //return buildResponse(listaPersonas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
  
}
