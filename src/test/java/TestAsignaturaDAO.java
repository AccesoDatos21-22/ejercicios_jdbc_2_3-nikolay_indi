import org.iesinfantaelena.dao.AsignaturaDAO;
import org.iesinfantaelena.dao.FactoriaDAO;
import org.iesinfantaelena.model.Alumno;
import org.iesinfantaelena.model.Asignatura;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestAsignaturaDAO {
    private AsignaturaDAO asignaturaDAO;

    @Test
    void conexion() {
        assertDoesNotThrow(() -> {
            asignaturaDAO = FactoriaDAO.getInstance().getAsignaturaDAO();
            asignaturaDAO.cerrar();
        });
    }

    @Test
    void insertar() {
        assertDoesNotThrow(() -> {
            asignaturaDAO = FactoriaDAO.getInstance().getAsignaturaDAO();

            Asignatura asig1 = new Asignatura(1, "Acceso a datos", "Trimenstral", 10);
            asignaturaDAO.insertar(asig1);
            asignaturaDAO.insertar(asig1);

            asignaturaDAO.cerrar();
        });
    }

    @Test
    void buscar() {
        assertDoesNotThrow(() -> {
            asignaturaDAO = FactoriaDAO.getInstance().getAsignaturaDAO();

            Asignatura asig1 = new Asignatura(1, "Acceso a datos", "Trimenstral", 10);
            asignaturaDAO.buscar("Acceso a datos");
            asignaturaDAO.insertar(asig1);
            asignaturaDAO.buscar(1);

            asignaturaDAO.cerrar();
        });
    }

    @Test
    void borrar() {
        assertDoesNotThrow(() -> {
            asignaturaDAO = FactoriaDAO.getInstance().getAsignaturaDAO();

            Asignatura asig1 = new Asignatura(1, "Acceso a datos", "Trimenstral", 10);
            asignaturaDAO.borrar(asig1);
            asignaturaDAO.insertar(asig1);
            asignaturaDAO.borrar(asig1);

            asignaturaDAO.cerrar();
        });
    }
}
