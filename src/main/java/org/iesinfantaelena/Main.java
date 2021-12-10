package org.iesinfantaelena;

import org.iesinfantaelena.dao.AccesoDatosException;
import org.iesinfantaelena.dao.JDBCAlumnoDAO;
import org.iesinfantaelena.dao.MatriculaException;
import org.iesinfantaelena.model.Alumno;

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

        alumnoDAO.insertar(alu1);
        alumnoDAO.insertar(alu2);
        alumnoDAO.insertar(alu3);

        System.out.println("\n---Buscamos alumno por id---\n" + alumnoDAO.buscar(1).toString());

        System.out.println("\n---Buscamos alumno por nombre---\n" + imprimirLista(alumnoDAO.buscar("Jorge")).toString());
    }
}
