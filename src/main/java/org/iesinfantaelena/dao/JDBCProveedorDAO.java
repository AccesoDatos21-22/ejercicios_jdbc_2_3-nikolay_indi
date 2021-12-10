package org.iesinfantaelena.dao;

import org.iesinfantaelena.model.Proveedor;
import org.iesinfantaelena.utils.Utilidades;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCProveedorDAO implements  ProveedorDAO{

    /**
     * Consultas a realizar en la base de datos junto a la creación de la misma
     */
    public static final String CREATE_TABLE_QUERY = "CREATE TABLE proveedores (" +
            "id INT NOT NULL," +
            "nombre VARCHAR(50) NOT NULL," +
            "calle VARCHAR(50) NOT NULL," +
            "ciudad VARCHAR(50) NOT NULL," +
            "pais VARCHAR(50) NOT NULL," +
            "codPostal INT NOT NULL," +
            "CONSTRAINT pk_id PRIMARY KEY(id)" +
            ")";
    public static final String SELECT_PROVEEDORES_QUERY = "SELECT * FROM proveedores";
    public static final String INSERT_PROVEEDOR_QUERY = "INSERT INTO proveedores VALUES (?,?,?,?,?,?)";
    public static final String UPDATE_PROVEEDOR_QUERY = "UPDATE proveedores SET calle = ?, ciudad = ?, pais = ?, codPostal = ? WHERE id = ?";
    public static final String DELETE_PROVEEDOR_QUERY = "DELETE FROM proveedores WHERE id = ?";

    /**
     * Declaración de variables a utilizar en la clase
     */
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement pstmt;
    private List<Proveedor> listaProveedores;
    private Proveedor proveedorTemporal;

    /**
     * Constructor por defecto en el cual se inicia la conexión a la base de datos
     */
    public JDBCProveedorDAO() throws AccesoDatosException {
        try {
            // Obtenemos la conexión
            this.con = new Utilidades().getConnection();
            this.stmt = con.createStatement();
            this.rs = null;
            this.pstmt = null;
            stmt.executeUpdate(CREATE_TABLE_QUERY);
        } catch (IOException e) {
            // Error al leer propiedades
            // En una aplicación real, escribo en el log y delego
            System.err.println(e.getMessage());
            throw new AccesoDatosException(
                    "Ocurrió un error al acceder a los datos");
        } catch (SQLException sqle) {
            // En una aplicación real, escribo en el log y delego
            // System.err.println(sqle.getMessage());
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException(
                    "Ocurrió un error al acceder a los datos");
        }

    }

    /**
     * Métodos para realizar las consultas a la base de datos
     */
    @Override
    public List<Proveedor> buscar(String nombre) throws AccesoDatosException {
        try{
            listaProveedores = new ArrayList<>();
            stmt = con.createStatement();
            stmt.executeQuery(SELECT_PROVEEDORES_QUERY);
            rs = stmt.executeQuery(SELECT_PROVEEDORES_QUERY);

            while(rs.next()){
                if(rs.getString(2).equalsIgnoreCase(nombre)){
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String calle = rs.getString(3);
                    String ciudad = rs.getString(4);
                    String pais = rs.getString(5);
                    int codPostal = rs.getInt(6);

                    proveedorTemporal = new Proveedor(id, name, calle, ciudad, pais, codPostal);

                    listaProveedores.add(proveedorTemporal);
                }
            }
        } catch (SQLException sqle){
            Utilidades.printSQLException(sqle);
        } finally {
            liberar();
        }

        return listaProveedores;
    }

    @Override
    public Proveedor buscar(Proveedor proveedor) throws AccesoDatosException {
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_PROVEEDORES_QUERY);

            while (rs.next()){
                if(rs.getInt(1) == proveedor.getIdentificador()){
                    int id = rs.getInt(1);
                    String nombre = rs.getString(2);
                    String calle = rs.getString(3);
                    String ciudad = rs.getString(4);
                    String pais = rs.getString(5);
                    int codPostal = rs.getInt(6);

                    proveedorTemporal = new Proveedor(id, nombre, calle, ciudad, pais, codPostal);
                }
            }
        } catch (SQLException sqle){
            Utilidades.printSQLException(sqle);
        } finally {
            liberar();
        }

        return proveedorTemporal;
    }

    @Override
    public void insertar(Proveedor proveedor) throws AccesoDatosException {
        if(proveedor.getNombre() != null && buscar(proveedor.getNombre()).size() == 0){
            try{
                pstmt = con.prepareStatement(INSERT_PROVEEDOR_QUERY);
                pstmt.setInt(1, proveedor.getIdentificador());
                pstmt.setString(2, proveedor.getNombre());
                pstmt.setString(3, proveedor.getCalle());
                pstmt.setString(4, proveedor.getCiudad());
                pstmt.setString(5, proveedor.getPais());
                pstmt.setInt(6, proveedor.getCp());

                System.out.println("Se ha insertado " + pstmt.executeUpdate() + " proveedor");
            } catch(SQLException sqle){
                Utilidades.printSQLException(sqle);
            } finally {
                liberar();
            }
        }
    }

    @Override
    public void actualizar(Proveedor proveedor) throws AccesoDatosException {
        try{
            pstmt = con.prepareStatement(UPDATE_PROVEEDOR_QUERY);
            pstmt.setString(1, proveedor.getCalle());
            pstmt.setString(2, proveedor.getCiudad());
            pstmt.setString(3, proveedor.getPais());
            pstmt.setInt(4, proveedor.getCp());
            pstmt.setInt(5, proveedor.getIdentificador());

            System.out.println("Se ha actualizado " + pstmt.executeUpdate() + " proveedor");
        } catch (SQLException sqle){
            Utilidades.printSQLException(sqle);
        } finally {
            liberar();
        }
    }

    @Override
    public void eliminar(Proveedor proveedor) throws AccesoDatosException {
        try{
            pstmt = con.prepareStatement(DELETE_PROVEEDOR_QUERY);
            pstmt.setInt(1, proveedor.getIdentificador());

            System.out.println("Se ha eliminado " + pstmt.executeUpdate() + " proveedor");
        } catch(SQLException sqle){
            Utilidades.printSQLException(sqle);
        } finally {
            liberar();
        }
    }

    /**
     * Método para cerrar la conexión a la BBDD
     */
    @Override
    public void cerrar() {
        if (con != null) {
            Utilidades.closeConnection(con);
        }
    }

    /**
     * Método para liberar los recursos utilizados en la clase
     */
    @Override
    public void liberar() {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }
        } catch (SQLException sqle) {
            Utilidades.printSQLException(sqle);
        }
    }
}
