package org.iesinfantaelena.dao;

public class  FactoriaDAO {

    private static FactoriaDAO instance;
    private static final String alumnoDAO = "JDBCAlumnoDAO";
    private static final String asignaturaDAO = "JDBCAsignaturaDAO";
    private static final String cafeDAO = "JDBCCafeDAO";
    private static final String proveedorDAO = "JDBCProveedorDAO";

    // Patrón Singleton
    // Garantiza que una clase sólo tenga una instancia y proporciona un punto
    // de acceso global a ella.
    // Si un objeto no tiene estado (atributos), no necesitamos múltiples
    // instancias
    // del mismo cargadas en memoria
    // No utilizamos métodos estáticos, pues perderiamos todas las ventajas de
    // la OO
    // como la herencia
    // Si tuviésemos atributos de objetos-->!!No usar singleton
    public static FactoriaDAO getInstance() {
        if (instance == null) {
            instance = new FactoriaDAO();
        }
        return instance;
    }

    private FactoriaDAO() {

    }

    /**
     * Devuelve un objeto DAO adecuado dependiendo de como esté implementada la
     * persistencia a datos
     *
     * @return
     * @throws AccesoDatosException
     */
    public AlumnoDAO getAlumnoDAO() throws AccesoDatosException {
        AlumnoDAO alumDAO = null;
        if (alumnoDAO.equals("JDBCAlumnoDAO")) {
            alumDAO = new JDBCAlumnoDAO();
        }
        return alumDAO;
    }

    /**
     * Devuelve un objeto DAO adecuado dependiendo de como esté implementada la
     * persistencia a datos
     *
     * @return
     * @throws AccesoDatosException
     */
    public AsignaturaDAO getAsignaturaDAO() throws AccesoDatosException {
        AsignaturaDAO asigDAO = null;
        if (asignaturaDAO.equals("JDBCAsignaturaDAO")) {
            asigDAO = new JDBCAsignaturaDAO();
        }
        return asigDAO;

    }

    /**
     * Devuelve un objeto DAO adecuado dependiendo de como esté implementada la
     * persistencia a datos
     *
     * @return
     * @throws AccesoDatosException
     */
    public CafeDAO getCafeDAO() throws AccesoDatosException {
        CafeDAO cdao = null;
        if (cafeDAO.equals("JDBCCafeDAO")) {
            cdao = new JDBCCafeDAO();
        }
        return cdao;
    }

    /**
     * Devuelve un objeto DAO adecuado dependiendo de como esté implementada la
     * persistencia a datos
     *
     * @return
     * @throws AccesoDatosException
     */
    public ProveedorDAO getProveedorDAO() throws AccesoDatosException {

       return null;
    }


}


