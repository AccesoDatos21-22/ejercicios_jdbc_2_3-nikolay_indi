package org.iesinfantaelena.dao;

import org.iesinfantaelena.model.Asignatura;
import org.iesinfantaelena.utils.Utilidades;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCAsignaturaDAO implements AsignaturaDAO{
    /**
     * Variables a utilizar en la clase
     */
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement pstmt;
    private List<Asignatura> listaAsignaturas;
    private Asignatura asignatura;

    /**
     * Consultas a realizar en la base de datos
     */
    private static final String CREATE_TABLE_ASIGNATURAS = "create table asignaturas (" +
            "   id_asignatura integer not null," +
            "   tipo varchar(2) not null," +
            "   nombre varchar(60) not null," +
            "   creditos float not null," +
            "   constraint id_pk primary key (id_asignatura)" +
            ");";
    private static final String INSERT_ASIGNATURAS_QUERY = "insert into asignaturas values(31540,\"OB\",\"AMPLIACION DE SISTEMAS OPERATIVOS\",4.5);" +
            "insert into asignaturas values(32330,\"OP\",\"APLICACIONES DISTRIBUIDAS PARA BIOINGENIERIA\",3);" +
            "insert into asignaturas values(33033,\"OP\",\"APLICACIONES TELEMATICAS\",4.5);" +
            "insert into asignaturas values(20598,\"TR\",\"ARQUITECTURA DE COMPUTADORES\",7.5);" +
            "insert into asignaturas values(78200,\"OP\",\"ARQUITECTURA E INGENIERIA DE COMPUTADORES\",6);";
    private static final String SELECT_ASIGNATURA_QUERY = "SELECT * FROM asignaturas";
    private static final String INSERT_ASIGNATURA_QUERY = "INSERT INTO asignaturas VALUES (?,?,?,?)";
    private static final String DELETE_ASIGNATURA_QUERY = "DELETE FROM asignaturas WHERE id_asignatura = ?";

    /**
     * Constructor por defecto de la clase
     */
    public JDBCAsignaturaDAO() throws AccesoDatosException {
        try {
            // Obtenemos la conexión
            this.con = new Utilidades().getConnection();
            this.stmt = con.createStatement();
            this.rs = null;
            this.pstmt = null;
            stmt.executeUpdate(CREATE_TABLE_ASIGNATURAS);
            stmt.executeUpdate(INSERT_ASIGNATURAS_QUERY);
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

    @Override
    public List<Asignatura> buscar(String nombre) throws MatriculaException {
        try{
            listaAsignaturas = new ArrayList<>();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_ASIGNATURA_QUERY);

            while (rs.next()){
                if(rs.getString(3).equals(nombre)){
                    int id = rs.getInt(1);
                    String tipo = rs.getString(2);
                    String name = rs.getString(3);
                    float creditos = rs.getFloat(4);

                    asignatura = new Asignatura(id, name, tipo, creditos);
                    listaAsignaturas.add(asignatura);
                }
            }
        } catch(SQLException sqle){
            Utilidades.printSQLException(sqle);
        } finally {
            liberar();
        }
        return listaAsignaturas;
    }

    @Override
    public Asignatura buscar(int identificador) throws MatriculaException {
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_ASIGNATURA_QUERY);

            while (rs.next()){
                if(rs.getInt(1) == identificador){
                    int id = rs.getInt(1);
                    String tipo = rs.getString(2);
                    String name = rs.getString(3);
                    float creditos = rs.getFloat(4);

                    asignatura = new Asignatura(id, name, tipo, creditos);
                }
            }
        } catch(SQLException sqle){
            Utilidades.printSQLException(sqle);
        } finally {
            liberar();
        }

        return asignatura;
    }

    @Override
    public void insertar(Asignatura asignatura) throws MatriculaException {
        if(asignatura.getNombre() != null && buscar(asignatura.getNombre()).size() == 0){
            try {
                pstmt = con.prepareStatement(INSERT_ASIGNATURA_QUERY);
                pstmt.setInt(1, asignatura.getIdentificador());
                pstmt.setString(2, asignatura.getTipo());
                pstmt.setString(3, asignatura.getNombre());
                pstmt.setFloat(4, asignatura.getCreditos());

                System.out.println("Se ha insertado " + pstmt.executeUpdate() + " asignatura");
            } catch (SQLException sqle){
                Utilidades.printSQLException(sqle);
            } finally {
                liberar();
            }
        }
    }

    @Override
    public void borrar(Asignatura asignatura) throws MatriculaException {
        try{
            pstmt = con.prepareStatement(DELETE_ASIGNATURA_QUERY);
            pstmt.setInt(1, asignatura.getIdentificador());

            System.out.println("Se ha eliminado " + pstmt.executeUpdate() + " asignatura");
        } catch (SQLException sqle){
            Utilidades.printSQLException(sqle);
        } finally {
            liberar();
        }
    }

    @Override
    public void cerrar() {
        if (con != null) {
            Utilidades.closeConnection(con);
        }
    }

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
