package org.iesinfantaelena.dao;

import org.iesinfantaelena.model.Alumno;
import org.iesinfantaelena.model.Asignatura;
import org.iesinfantaelena.utils.Utilidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCAlumnoDAO implements AlumnoDAO{
    /**
     * Declaración de las varibles a utilizar en la clase
     */
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement pstmt;
    private List<Alumno> listaAlumnos;
    private Alumno alumnoTemporal;

    /**
     * Consultas a realizar en la base de datos
     */
    private static final String CREATE_TABLE_ALUMNOS = "create table alumnos (" +
            "   id_alumno integer not null," +
            "   apellidos varchar(24) not null," +
            "   nombre varchar(18) not null," +
            "   curso integer not null," +
            "   titulacion integer not null," +
            "   constraint id_pk primary key (id_alumno)" +
            ");";
    private static final String CREATE_TABLE_ALUMNOS_ASIGNATURAS = "create table alumnos_asignaturas (" +
            "   id_alumno integer not null," +
            "   id_asignatura integer not null," +
            "   cursada bool not null," +
            "   constraint as_pk foreign key (id_asignatura) references asignaturas (id_asignatura) on delete cascade," +
            "constraint al_pk foreign key (id_alumno) references alumnos (id_alumno) on delete cascade" +
            ");";
    private static final String INSERT_ALUMNOS_QUERY = "insert into alumnos values(9119705,\"JIMENEZ ALONSO\",\"DIEGO\",4,3);" +
            "insert into alumnos values(4338289,\"MANGAS SANZ\",\"CESAR\",1,12);" +
            "insert into alumnos values(5345629,\"BARRIGA ASENJO\",\"JOSE\",2,7);" +
            "insert into alumnos values(5198695,\"RODRIGUEZ ROBLEDO\",\"FCO. JAVIER\",3,5);" +
            "insert into alumnos values(5434159,\"BLAZQUEZ BLANCO\",\"SONIA\",2,7);";
    private static final String INSERT_ALUMNOS_ASIGNATURAS_QUERY = "insert into alumnos_asignaturas values(9119705,31540,0);" +
            "insert into alumnos_asignaturas values(9119705,32330,0);" +
            "insert into alumnos_asignaturas values(4338289,31540,1);" +
            "insert into alumnos_asignaturas values(4338289,32330,0);" +
            "insert into alumnos_asignaturas values(5345629,33033,0);" +
            "insert into alumnos_asignaturas values(5345629,32330,1);" +
            "insert into alumnos_asignaturas values(5345629,20598,1);" +
            "insert into alumnos_asignaturas values(5198695,31540,1);" +
            "insert into alumnos_asignaturas values(5198695,32330,0);" +
            "insert into alumnos_asignaturas values(5198695,33033,0);" +
            "insert into alumnos_asignaturas values(5198695,20598,0);" +
            "insert into alumnos_asignaturas values(5434159,32330,0);" +
            "insert into alumnos_asignaturas values(5434159,33033,1);" +
            "insert into alumnos_asignaturas values(5434159,20598,0);" +
            "insert into alumnos_asignaturas values(5434159,78200,1);" +
            "insert into alumnos_asignaturas values(4338289,20598,0);" +
            "insert into alumnos_asignaturas values(9119705,20598,0);" +
            "insert into alumnos_asignaturas values(9119705,78200,1);";
    private static final String SELECT_ALUMNOS_QUERY = "SELECT * FROM alumnos";
    private static final String INSERT_ALUMNO_QUERY = "INSERT INTO alumnos VALUES (?,?,?,?,?)";
    private static final String DELETE_ALUMNO_QUERY = "DELETE FROM alumnos WHERE id_alumno = ?";
    private static final String DELETE_ALUMNO_ASIGNATURA_QUERY = "DELETE FROM alumnos_asignaturas WHERE id_alumno = ?";
    private static final String INSERT_ALUMNO_ASIGNATURA_QUERY = "INSERT INTO alumnos_asignaturas VALUES (?,?,?)";

    /**
     * Declaración de constructor por defecto
     */
    public JDBCAlumnoDAO() throws AccesoDatosException {
        try {
            // Obtenemos la conexión
            this.con = new Utilidades().getConnection();
            this.stmt = con.createStatement();
            this.rs = null;
            this.pstmt = null;
            stmt.executeUpdate(CREATE_TABLE_ALUMNOS);
            stmt.executeUpdate(CREATE_TABLE_ALUMNOS_ASIGNATURAS);
            stmt.executeUpdate(INSERT_ALUMNOS_QUERY);
            stmt.executeUpdate(INSERT_ALUMNOS_ASIGNATURAS_QUERY);
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
    public List<Alumno> buscar(String nombre) throws MatriculaException {
        try{
            listaAlumnos = new ArrayList<>();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_ALUMNOS_QUERY);

            while(rs.next()){
                if(rs.getString(3).equals(nombre)) {
                    int id = rs.getInt(1);
                    String apellidos = rs.getString(2);
                    String name = rs.getString(3);
                    int curso = rs.getInt(4);
                    int titulacion = rs.getInt(5);
                    alumnoTemporal = new Alumno(name, id, apellidos, curso, titulacion);
                    listaAlumnos.add(alumnoTemporal);
                }
            }
        } catch (SQLException sqle){
            Utilidades.printSQLException(sqle);
        } finally {
            liberar();
        }
        return listaAlumnos;
    }

    @Override
    public Alumno buscar(int id) throws MatriculaException {
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_ALUMNOS_QUERY);

            while(rs.next()){
                if(rs.getInt(1) == id){
                    int idAlumno = rs.getInt(1);
                    String apellidos = rs.getString(2);
                    String nombre = rs.getString(3);
                    int curso = rs.getInt(4);
                    int titulacion = rs.getInt(5);

                    alumnoTemporal = new Alumno(nombre, idAlumno, apellidos, curso, titulacion);
                }
            }
        } catch(SQLException sqle){
            Utilidades.printSQLException(sqle);
        } finally {
            liberar();
        }
        return alumnoTemporal;
    }

    @Override
    public void insertar(Alumno alumno) throws MatriculaException {
        if(alumno.getNombre() != null && buscar(alumno.getNombre()).size() == 0){
            try{
                pstmt = con.prepareStatement(INSERT_ALUMNO_QUERY);
                pstmt.setInt(1, alumno.getId());
                pstmt.setString(2, alumno.getApellidos());
                pstmt.setString(3, alumno.getNombre());
                pstmt.setInt(4, alumno.getCurso());
                pstmt.setInt(5, alumno.getTitulacion());

                System.out.println("Se ha insertado " + pstmt.executeUpdate()+ " alumno");

            } catch(SQLException sqle){
                Utilidades.printSQLException(sqle);
            } finally {
                liberar();
            }
        }
    }

    @Override
    public void borrar(int id) throws MatriculaException {
        try{
            pstmt = con.prepareStatement(DELETE_ALUMNO_ASIGNATURA_QUERY);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt = con.prepareStatement(DELETE_ALUMNO_QUERY);
            pstmt.setInt(1, id);

            System.out.println("Se ha borrado " + pstmt.executeUpdate() + " alumno");
        } catch (SQLException sqle){
            Utilidades.printSQLException(sqle);
        } finally {
            liberar();
        }
    }

    @Override
    public void matricular(Alumno alumno, Asignatura asignatura) throws MatriculaException {
        try{
            pstmt = con.prepareStatement(INSERT_ALUMNO_ASIGNATURA_QUERY);
            pstmt.setInt(1, alumno.getId());
            pstmt.setInt(2, asignatura.getIdentificador());
            pstmt.setBoolean(3, true);

            System.out.println("Se ha matriculado " + pstmt.executeUpdate() + " alumno");


        } catch(SQLException sqle){
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
