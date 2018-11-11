/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import modelo.Login;
import modelo.Respuesta;

/**
 * REST Web Service
 *
 * @author Vanegas
 */
@Path("cliente")
public class ClienteResource {

    @Context
    private UriInfo context;

    private Connection con;

    private boolean registro = false;

    /**
     * Constructor
     */
    public ClienteResource() throws SQLException {
        con = Conexion.getConexion();
    }

    /**
     * Metodo que inserta un cliente en la base de datos
     *
     * @param cliente, cliente a registrar en la base de datos
     * @return mensaje indicando si se inserto o no correctamente
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("save")
    public Respuesta registrarCliente(Login cliente) {

        try {
            con = Conexion.getConexion();

            String sql = "INSERT INTO `persona` (nombre, apellido, fecha_nacimiento, cedula, "
                    + "direccion, telefono, correo, rol_id, municipio_id, genero, "
                    + "activo, latitud, longitud) VALUES (?,?,?,?,?,?,?,?,?,?,?,NULL,NULL);";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cliente.getPersona_cedula().getNombre());
            ps.setString(2, cliente.getPersona_cedula().getApellido());
            ps.setDate(3, (Date) cliente.getPersona_cedula().getFecha_nacimiento());
            ps.setString(4, cliente.getPersona_cedula().getCedula());
            ps.setString(5, cliente.getPersona_cedula().getDireccion());
            ps.setInt(6, cliente.getPersona_cedula().getTelefono());
            ps.setString(7, cliente.getPersona_cedula().getCorreo());
            ps.setInt(8, cliente.getPersona_cedula().getRol_id().getId());
            ps.setInt(9, cliente.getPersona_cedula().getMunicipio_id().getId());
            ps.setInt(10, cliente.getPersona_cedula().getGenero());
            ps.setInt(11, cliente.getPersona_cedula().getActivo());

            ps.execute();
            registro = true;

            String sql2 = "INSERT INTO login (username, contrasenia, persona_cedula, activo) "
                    + "VALUES (?, ?, ?, ?);";

            PreparedStatement ps2 = con.prepareStatement(sql2);

            ps2.setString(1, cliente.getUsername());
            ps2.setString(2, cliente.getContrasenia());
            ps2.setString(3, cliente.getPersona_cedula().getCedula());
            ps2.setInt(4, cliente.getPersona_cedula().getActivo());

            ps2.execute();

            if (registro) {
                con.close();
                return new Respuesta(505, "Se registro correctamente el cliente");
            }

        } catch (MySQLIntegrityConstraintViolationException exp) {
            try {
                if (!registro) {
                    con.close();
                    return new Respuesta(404, "La cédula ya se encuentra registrada");
                } else {
                    System.out.println(exp.getSQLState());
                    String sql3 = "DELETE FROM persona WHERE cedula = ? ;";
                    PreparedStatement ps3 = con.prepareStatement(sql3);

                    ps3.setString(1, cliente.getPersona_cedula().getCedula());

                    ps3.execute();
                    con.close();

                    return new Respuesta(404, "El nombre de usuario ya esta en uso");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Respuesta(404, "Hubo un error al crear la persona");
    }

}
