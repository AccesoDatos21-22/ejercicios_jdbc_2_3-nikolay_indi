import org.iesinfantaelena.dao.AlumnoDAO;
import org.iesinfantaelena.dao.FactoriaDAO;
import org.iesinfantaelena.model.Alumno;
import org.iesinfantaelena.model.Asignatura;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestAlumnoDAO {
    private AlumnoDAO alumnoDAO;

    @Test
    void conexion() {
        assertDoesNotThrow(() -> {
            alumnoDAO = FactoriaDAO.getInstance().getAlumnoDAO();
            alumnoDAO.cerrar();
        });
    }

    @Test
    void insertar() {
        assertDoesNotThrow(() -> {
            alumnoDAO = FactoriaDAO.getInstance().getAlumnoDAO();

            Alumno alu1 = new Alumno("Pedro", 1, "Gonzalez", 4, 3);
            alumnoDAO.insertar(alu1);
            alumnoDAO.insertar(alu1);

            alumnoDAO.cerrar();
        });
    }

    @Test
    void buscar() {
        assertDoesNotThrow(() -> {
            alumnoDAO = FactoriaDAO.getInstance().getAlumnoDAO();

            Alumno alu1 = new Alumno("Pedro", 1, "Gonzalez", 4, 3);
            alumnoDAO.buscar("Pedro");
            alumnoDAO.insertar(alu1);
            alumnoDAO.buscar(1);

            alumnoDAO.cerrar();
        });
    }

    @Test
    void borrar() {
        assertDoesNotThrow(() -> {
            alumnoDAO = FactoriaDAO.getInstance().getAlumnoDAO();

            Alumno alu1 = new Alumno("Pedro", 1, "Gonzalez", 4, 3);
            alumnoDAO.borrar(1);
            alumnoDAO.insertar(alu1);
            alumnoDAO.borrar(1);

            alumnoDAO.cerrar();
        });
    }

    @Test
    void matricular() {
        assertDoesNotThrow(() -> {
            alumnoDAO = FactoriaDAO.getInstance().getAlumnoDAO();

            Alumno alu1 = new Alumno("Pedro", 1, "Gonzalez", 4, 3);
            Asignatura asig1 = new Asignatura(1, "Acceso a datos", "Trimenstral", 10);
            alumnoDAO.matricular(alu1,asig1);

            alumnoDAO.cerrar();
        });
    }
}
