package org.iesinfantaelena.dao;

import org.iesinfantaelena.model.Cafe;
import org.iesinfantaelena.utils.Utilidades;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCCafeDAO implements CafeDAO{
    /**
     * Declaración de variables a utilizar en la clase
     */
    private Cafe cafeTemporal;
    private List <Cafe> listaCafes;

    /**
     * Declaración de variable con la ruta del fichero que contiene las propiedades para la conexión a la base de datos
     */
    //private static final String PROPERTIES_FILE = "src/main/resources/h2-properties.xml";

    /**
     * Consultas a realizar en la base de datos
     */
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE cafes (" +
            "nombre_cafe VARCHAR(50) NOT NULL," +
            "proveedorId VARCHAR(50) NOT NULL," +
            "precio FLOAT NOT NULL," +
            "ventas INT NOT NULL," +
            "total INT NOT NULL," +
            "CONSTRAINT pk_cafes PRIMARY KEY(nombre_cafe))";
    private static final String SELECT_CAFES_QUERY = "SELECT * FROM cafes";
    private static final String SEARCH_CAFE_QUERY = "SELECT * FROM cafes WHERE nombre_cafe = ?";
    private static final String SEARCH_CAFE_PROV_QUERY = "SELECT * FROM cafes WHERE proveedorId = ?";
    private static final String INSERT_CAFE_QUERY = "INSERT INTO cafes VALUES(?,?,?,?,?)";
    private static final String DELETE_CAFE_QUERY = "DELETE FROM cafes WHERE nombre_cafe = ?";
    //private static final String UPDATE_VENTAS_CAFE = "UPDATE cafes SET ventas = ? WHERE nombre_cafe = ?";
    //private static final String UPDATE_INC_VENTAS_CAFE = "UPDATE cafes SET ventas = ((SELECT ventas FROM cafes WHERE nombre_cafe = ?) + ?) WHERE nombre_cafe = ?";
    //private static final String UPDATE_TOTAL_CAFE = "UPDATE cafes SET total = ? WHERE nombre_cafe = ?";

    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement pstmt;

    /**
     * Constructor: inicializa conexión
     *
     * @throws AccesoDatosException
     */
    public JDBCCafeDAO() throws AccesoDatosException {
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
     * Métodos para hacer las consultas a la base de datos
     * @throws AccesoDatosException
     */
    @Override
    public void verTabla() throws AccesoDatosException {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_CAFES_QUERY);

            while (rs.next()) {
                String coffeeName = rs.getString("nombre_cafe");
                int supplierID = rs.getInt("proveedorId");
                float precio = rs.getFloat("precio");
                int ventas = rs.getInt("ventas");
                int total = rs.getInt("total");
                System.out.println(coffeeName + ", " + supplierID + ", " + precio + "€, " + ventas + ", " + total);
            }
        } catch (SQLException sqle) {
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException(
                    "Ocurrió un error al acceder a los datos");
        } finally {
            liberar();
        }
    }

    @Override
    public List<Cafe> buscar(String nombre) throws AccesoDatosException {
        try {
            listaCafes = new ArrayList<>();
            pstmt = con.prepareStatement(SEARCH_CAFE_QUERY);
            pstmt.setString(1, nombre);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String nombreCafe = rs.getString("nombre_cafe");
                int id_proveedor = rs.getInt("proveedorId");
                float precio = rs.getFloat("precio");
                int ventas = rs.getInt("ventas");
                int total = rs.getInt("total");
                
                cafeTemporal = new Cafe(nombreCafe, id_proveedor,precio, ventas,total);
                listaCafes.add(cafeTemporal);
            }

        } catch (SQLException sqle) {
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException(
                    "Ocurrió un error al acceder a los datos");
        } finally {
            liberar();
        }
        return listaCafes;
    }

    @Override
    public void insertar(String nombre, int provid, float precio, int ventas, int total) throws AccesoDatosException {
        if(nombre != null && buscar(nombre) == null){
            try {
                pstmt = con.prepareStatement(INSERT_CAFE_QUERY);

                pstmt.setString(1, nombre);
                pstmt.setInt(2, provid);
                pstmt.setFloat(3, precio);
                pstmt.setInt(4, ventas);
                pstmt.setInt(5, total);

                pstmt.executeUpdate();
            } catch (SQLException sqle) {
                Utilidades.printSQLException(sqle);
                throw new AccesoDatosException(
                        "Ocurrió un error al acceder a los datos");
            } finally {
                liberar();
            }
        }
    }

    @Override
    public void borrar(String nombre) throws AccesoDatosException {
        try {
            pstmt = con.prepareStatement(DELETE_CAFE_QUERY);
            pstmt.setString(1, nombre);

            if(pstmt.executeUpdate() > 0){
                System.out.println("El café " + nombre + " ha sido borrado.");
            }
        } catch (SQLException sqle) {
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException(
                    "Ocurrió un error al acceder a los datos");
        } finally {
            liberar();
        }
    }

    @Override
    public List<Cafe> cafesPorProveedor(int provid) throws AccesoDatosException {
        try {
            listaCafes = new ArrayList<>();
            pstmt = con.prepareStatement(SEARCH_CAFE_PROV_QUERY);
            pstmt.setInt(1, provid);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                String nombreCafe = rs.getString("nombre_cafe");
                int idProveedor = rs.getInt("proveedorId");
                float precio = rs.getFloat("precio");
                int ventas = rs.getInt("ventas");
                int total = rs.getInt("total");

                cafeTemporal = new Cafe(nombreCafe, idProveedor, precio, ventas, total);

                listaCafes.add(cafeTemporal);
            }
        } catch (SQLException sqle) {
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException("Ocurrió un error al acceder a los datos");
        } finally {
            liberar();
        }
        return listaCafes;
    }

    @Override
    public void transferencia(String cafe1, String cafe2) throws AccesoDatosException {
        try {
            con.setAutoCommit(false);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(SELECT_CAFES_QUERY);

            int ventas = 0;

            while(rs.next()){
                if(rs.getString("nombre_cafe").equals(cafe1)){
                    ventas = rs.getInt("ventas");
                }
            }

            rs.beforeFirst();

            while(rs.next()){
                if(rs.getString("nombre_cafe").equals(cafe2)){
                    rs.updateInt("ventas", rs.getInt("ventas")+ventas);
                    rs.updateRow();
                }
            }

            rs.beforeFirst();

            while(rs.next()){
                if(rs.getString("nombre_cafe").equals(cafe1)){
                    rs.updateInt("ventas", 0);
                    rs.updateRow();
                }
            }

            con.commit();
        } catch( SQLException sqle){
            try {
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Utilidades.printSQLException(sqle);
        }
    }

    @Override
    public Cafe obtener(Cafe cafe) throws AccesoDatosException {
        boolean encontrado = false;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_CAFES_QUERY);

            while(rs.next()){
                if(cafe.getNombre().equals(rs.getString(1))){
                    encontrado = true;
                }
            }
        } catch(SQLException sqle){
            Utilidades.printSQLException(sqle);
        } finally {
            liberar();
        }
        if(encontrado){
            return cafe;
        } else{
            return null;
        }
    }

    @Override
    public void insertar(Cafe cafe) throws AccesoDatosException {
        if(cafe.getNombre() != null){
            try {
                pstmt = con.prepareStatement(INSERT_CAFE_QUERY);

                pstmt.setString(1, cafe.getNombre());
                pstmt.setInt(2, cafe.getProvid());
                pstmt.setFloat(3, cafe.getPrecio());
                pstmt.setInt(4, cafe.getVentas());
                pstmt.setInt(5, cafe.getTotal());

                pstmt.executeUpdate();
            } catch (SQLException sqle) {
                Utilidades.printSQLException(sqle);
                throw new AccesoDatosException(
                        "Ocurrió un error al acceder a los datos");
            } finally {
                liberar();
            }
        }
    }

    @Override
    public void borrar(Cafe cafe) throws AccesoDatosException {
        try {
            pstmt = con.prepareStatement(DELETE_CAFE_QUERY);
            pstmt.setString(1, cafe.getNombre());
            pstmt.executeUpdate();

            System.out.println("El café " + cafe.getNombre() + " ha sido borrado.");
        } catch (SQLException sqle) {
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException(
                    "Ocurrió un error al acceder a los datos");
        } finally {
            liberar();
        }
    }

    @Override
    public void actualizar(Cafe cafe) throws AccesoDatosException {
        try{
            con.setAutoCommit(false);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(SELECT_CAFES_QUERY);

            while(rs.next()){
                if(rs.getString("nombre_cafe").equals(cafe.getNombre())){
                    rs.updateInt("ventas", rs.getInt("ventas") + cafe.getVentas());
                    rs.updateRow();
                }
            }

        }catch(SQLException sqle){
            Utilidades.printSQLException(sqle);
        }
    }

    /**
     * Método para cerrar la conexión
     */
    @Override
    public void cerrar() {
        if (con != null) {
            Utilidades.closeConnection(con);
        }
    }

    /**
     * Método para vaciar todos los recursos utilizados en el programa
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
