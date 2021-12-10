package org.iesinfantaelena;

import org.iesinfantaelena.dao.AccesoDatosException;
import org.iesinfantaelena.dao.JDBCAlumnoDAO;
import org.iesinfantaelena.dao.JDBCAsignaturaDAO;
import org.iesinfantaelena.dao.MatriculaException;
import org.iesinfantaelena.model.Alumno;
import org.iesinfantaelena.model.Asignatura;
import org.sqlite.JDBC;

import java.util.List;

public class Main {
    public static Alumno imprimirLista(List<Alumno> listaAlumnos){
        Alumno alumnoTemporal = new Alumno();
        for(int i = 0; i < listaAlumnos.size(); i++){
            int id = listaAlumnos.get(i).getId();
            String apellidos = listaAlumnos.get(i).getApellidos();
            String nombre = listaAlumnos.get(i).getNombre();
            int curso = listaAlumnos.get(i).getCurso();
            int titulacion = listaAlumnos.get(i).getTitulacion();

            alumnoTemporal = new Alumno(nombre, id, apellidos, curso, titulacion);
        }
        return alumnoTemporal;
    }

    public static void main(String[] args) throws AccesoDatosException, MatriculaException {
        Alumno alu1 = new Alumno("Pedro", 1, "Gonzalez", 4, 3);
        Alumno alu2 = new Alumno("Jorge", 2, "López", 1, 2);
        Alumno alu3 = new Alumno("Fernando", 3, "Gonalez", 3, 8);

        JDBCAlumnoDAO alumnoDAO = new JDBCAlumnoDAO();

        Asignatura asig1 = new Asignatura(1, "Acceso a datos", "Trimenstral", 10);
        Asignatura asig2 = new Asignatura(2, "Desarrollo de interfaces", "Trimenstral", 20);
        Asignatura asig3 = new Asignatura(3, "Programación de servicios y procesos", "Trimenstral", 10);

        JDBCAsignaturaDAO asignaturaDAO = new JDBCAsignaturaDAO();

        System.out.println("---Agregamos alumnos---");
        alumnoDAO.insertar(alu1);
        alumnoDAO.insertar(alu2);
        alumnoDAO.insertar(alu3);

        System.out.println("\n---Buscamos alumno por id---\n" + alumnoDAO.buscar(4338289).toString());

        System.out.println("\n---Buscamos alumno por nombre---\n" + imprimirLista(alumnoDAO.buscar("Jorge")).toString());

        System.out.println("\n---Borramos un alumno---");
        alumnoDAO.borrar(3);

        System.out.println("\n---Agregamos asignaturas---");
        asignaturaDAO.insertar(asig1);
        asignaturaDAO.insertar(asig2);
        asignaturaDAO.insertar(asig3);

        System.out.println("\n---Buscamos una asignatura por nombre---\n" + asignaturaDAO.buscar("Acceso a datos").toString());

        System.out.println("\n---Buscamos una asignatura por id---\n" + asignaturaDAO.buscar(78200));

        System.out.println("\n---Borramos una asignatura---");
        asignaturaDAO.borrar(asig2);

        System.out.println("\n---Mátriculamos a un alumno en una asignatura---");
        alumnoDAO.matricular(alu1, asig1);
    }
}
