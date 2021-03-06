package org.iesinfantaelena;

import org.iesinfantaelena.dao.*;
import org.iesinfantaelena.model.Alumno;
import org.iesinfantaelena.model.Asignatura;
import org.iesinfantaelena.model.Cafe;
import org.iesinfantaelena.model.Proveedor;

import java.util.List;

public class Main {
    /**
     * Método para visualizar las listas de cafes devueltas
     * @param listaCafes
     */
    public static void imprimirListaCafe(List<Cafe> listaCafes){
        for(Cafe lista : listaCafes){
            System.out.println(lista.getNombre() + ", " + lista.getProvid() + ", " + lista.getPrecio() + "€, " + lista.getVentas() + ", " + lista.getTotal());
        }
    }
    public static Alumno imprimirListaAlumno(List<Alumno> listaAlumnos) {
        Alumno alumnoTemporal = new Alumno();
        for (int i = 0; i < listaAlumnos.size(); i++) {
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
        JDBCCafeDAO cafeTemp = new JDBCCafeDAO();
        Cafe cafe1 = new Cafe("Latte", 1, 3.50F, 3, 45);
        Cafe cafe2 = new Cafe("Americano", 3, 1.50F, 15, 72);
        Cafe cafe3 = new Cafe("Espresso", 6, 2.24F, 2, 33);
        Cafe cafe4 = new Cafe("Macchiato", 2, 4.75F, 9, 50);
        Cafe cafe5 = new Cafe("Cappuccino", 1, 5, 7, 70);

        System.out.println("---Inserción de todos los cafés y visualización de la tabla---");
        cafeTemp.insertar(cafe1);
        cafeTemp.insertar(cafe2);
        cafeTemp.insertar(cafe3);
        cafeTemp.insertar(cafe4);
        cafeTemp.insertar(cafe5);
        cafeTemp.verTabla();

        System.out.println();

        System.out.println("---Buscamos el Cappuccino---");
        imprimirListaCafe(cafeTemp.buscar("Cappuccino"));

        System.out.println();

        System.out.println("---Borramos el Latte y visualizamos de nuevo la tabla---");
        cafeTemp.borrar(cafe1);
        cafeTemp.verTabla();

        System.out.println();

        System.out.println("---Buscamos los cafes por proveedor---");
        imprimirListaCafe(cafeTemp.cafesPorProveedor(2));

        System.out.println();

        System.out.println("---Transferimos las ventas del Americano al Espresso e imprimimos de nuevo la tabla---");
        cafeTemp.transferencia("Americano", "Espresso");
        cafeTemp.verTabla();

        System.out.println();

        System.out.println("---Actualizamos las ventas del Americano e imprimimos la tabla---");
        cafeTemp.actualizar(cafe2);
        cafeTemp.verTabla();

        System.out.println();

        System.out.println("---Proveedores---");
        Proveedor pro1 = new Proveedor(1, "Pro_1", "Calle_1", "Collado Villalba", "España", 28400);
        Proveedor pro2 = new Proveedor(2, "Pro_2", "Calle_2", "Galapagar", "España", 28000);
        Proveedor pro3 = new Proveedor(3, "Pro_3", "Calle_3", "El Escorial", "España", 28000);
        Proveedor pro4 = new Proveedor(4, "Pro_4", "Calle_4", "Las Rozas", "España", 28000);

        Proveedor pro4Nuevo = new Proveedor(4, "Pro_4", "Calle de la oliva", "Madrid", "España", 28000);

        JDBCProveedorDAO proTemporal = new JDBCProveedorDAO();
        proTemporal.insertar(pro1);
        proTemporal.insertar(pro2);
        proTemporal.insertar(pro3);
        proTemporal.insertar(pro4);

        System.out.println();

        System.out.println("---Buscamos un proveedor pasandole un objeto---\n" + proTemporal.buscar(pro1).toString());

        System.out.println();

        System.out.println("---Buscamos un proveedor pasandole el nombre del mismo---\n" + proTemporal.buscar("Pro_2").toString());

        System.out.println();

        System.out.println("---Eliminado el proveedor con id 3---");
        proTemporal.eliminar(pro3);

        System.out.println();

        System.out.println("---Actualizamos el proveedor con id 4 y lo mostramos---");
        proTemporal.actualizar(pro4Nuevo);
        System.out.println(proTemporal.buscar(pro4Nuevo).toString());

        System.out.println();

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

        System.out.println("\n---Buscamos alumno por nombre---\n" + imprimirListaAlumno(alumnoDAO.buscar("Jorge")).toString());

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
