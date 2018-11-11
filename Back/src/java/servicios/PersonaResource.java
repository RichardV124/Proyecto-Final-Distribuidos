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
import modelo.Municipio;
import modelo.Notificacion;
import modelo.Persona;
import modelo.Rol;

/**
 * REST Web Service
 *
 * @author 415-pc-profe
 */
@Path("persona")
public class PersonaResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PersonaResource
     */
    public PersonaResource() {
    }

    
    /**
     * Metodo que busca una persona por una cedula
     * @param cedula, cedula por la cual se buscara la persona
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search/{cedula}")
    public Persona search(@PathParam("cedula") String cedula) {
          try {
            Persona persona = new Persona();
            //Llamo la conexion y le envio la consulta a la base de datos
            Connection con = Conexion.getConexion();
            String sql = "SELECT p.nombre, p.apellido,p.fecha_nacimiento,"
                    + "p.cedula,p.direccion,p.telefono,p.correo,p.rol_id,p.municipio_id,"
                    + "p.genero,p.activo FROM persona p WHERE p.cedula = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                persona.setNombre(rs.getString(1));
                persona.setApellido(rs.getString(2));
                persona.setFecha_nacimiento(rs.getDate(3));
                persona.setCedula(rs.getString(4));
                persona.setDireccion(rs.getString(5));
                persona.setTelefono(rs.getInt(6));
                persona.setCorreo(rs.getString(7));
                Rol rol = new Rol();
                rol.setId(rs.getInt(8));
                persona.setRol_id(rol);
                Municipio mun = new Municipio();
                mun.setId(rs.getInt(9));
                persona.setMunicipio_id(mun);
                persona.setGenero(rs.getInt(10));
                persona.setActivo(rs.getInt(11));
               
            }
            con.close();
            return persona;
            //return buildResponse(listaPersonas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Retrieves representation of an instance of servicios.PersonaResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Persona> obtenerPersonas() {
        List<Persona> personas=new ArrayList<Persona>();
        personas.add(new Persona());
        personas.add(new Persona());
        return personas;
    }

    /**
     * PUT method for updating or creating an instance of PersonaResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarPersona(Persona persona) {
        System.err.println(persona.getCedula());
        return buildResponse(new Notificacion("Put","objeto Persona actualizada"));
    }
    
    /**
     * PUT method for updating or creating an instance of PersonaResource
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearPersona(Persona persona) {
        System.err.println(persona.getCedula());
        return buildResponse(new Notificacion("Post","objeto Persona Creado"));
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminarPersona(Persona persona) {
        System.err.println(persona.getCedula());
        return buildResponse(new Notificacion("delete","objeto Persona eliminado"));
    }
    
     private static Response buildResponse(Object entity) {
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(entity)
                .build();
    }
}
