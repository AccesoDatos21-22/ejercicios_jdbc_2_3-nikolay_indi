import org.iesinfantaelena.dao.*;
import org.iesinfantaelena.model.Cafe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCafeDAO {


    @Test
    @DisplayName("La conexión no debería lanza una excepción")
        void conexion() {
            assertDoesNotThrow(() -> {
                CafeDAO  cafeDAO= FactoriaDAO.getInstance().getCafeDAO();

            });
        }

    @Test
    void insertar() {
        assertDoesNotThrow(() -> {
            Cafe test;
            test = new Cafe("Cafetito", 150, 1.0f, 100,1000);
            CafeDAO cafeDAO= FactoriaDAO.getInstance().getCafeDAO();
            cafeDAO.insertar(test);
            assertEquals(test, cafeDAO.obtener(test));
        });
    }

    // añadir resto de tests

}
