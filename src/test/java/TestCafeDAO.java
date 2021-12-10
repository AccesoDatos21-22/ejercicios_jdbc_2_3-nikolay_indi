import org.iesinfantaelena.dao.*;
import org.iesinfantaelena.model.Cafe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCafeDAO {

    private CafeDAO cafeDAO;

    @Test
    @DisplayName("La conexión no debería lanza una excepción")
    void conexion() {
        assertDoesNotThrow(() -> {
            cafeDAO= FactoriaDAO.getInstance().getCafeDAO();
            cafeDAO.cerrar();
        });
    }

    @Test
    void insertar() {
        assertDoesNotThrow(() -> {
            cafeDAO = FactoriaDAO.getInstance().getCafeDAO();

            Cafe cafe = new Cafe("Cafetito", 150, 1.0f, 100,1000);
            cafeDAO.insertar(cafe);
            cafeDAO.insertar("Cafetito", 150, 1.0f, 100,1000);
            assertEquals(cafe, cafeDAO.obtener(cafe));

            Cafe cafeVacio = new Cafe();
            cafeDAO.insertar(cafeVacio);

            cafeDAO.cerrar();
        });
    }

    // añadir resto de tests

    @Test
    void verTabla() {
        assertDoesNotThrow(()->{
            cafeDAO = FactoriaDAO.getInstance().getCafeDAO();
            cafeDAO.verTabla();
            cafeDAO.cerrar();
        });
    }

    @Test
    void buscar() {
        assertDoesNotThrow(()->{
            cafeDAO = FactoriaDAO.getInstance().getCafeDAO();
            cafeDAO.buscar("1");
            cafeDAO.cerrar();
        });
    }

    @Test
    void borrar() {
        assertDoesNotThrow(()->{
            cafeDAO = FactoriaDAO.getInstance().getCafeDAO();
            Cafe cafe = new Cafe("Cafetito", 150, 1.0f, 100,1000);
            cafeDAO.borrar(cafe);
            cafeDAO.borrar("Cafe");
            cafeDAO.cerrar();
        });
    }

    @Test
    void cafePorProveedor() {
        assertDoesNotThrow(()->{
            cafeDAO = FactoriaDAO.getInstance().getCafeDAO();
            cafeDAO.cafesPorProveedor(2);
            cafeDAO.cerrar();
        });
    }

    @Test
    void tranferencia() {
        assertDoesNotThrow(()->{
            cafeDAO = FactoriaDAO.getInstance().getCafeDAO();
            Cafe cafe = new Cafe("Cafetito", 150, 1.0f, 100,1000);
            cafeDAO.transferencia("Cafetito", "cafe2");
            cafeDAO.cerrar();
        });
    }

    @Test
    void obtener() {
        assertDoesNotThrow(()->{
            cafeDAO = FactoriaDAO.getInstance().getCafeDAO();
            Cafe cafe = new Cafe("Cafetito", 150, 1.0f, 100,1000);
            cafeDAO.obtener(cafe);
            cafeDAO.cerrar();
        });
    }

    @Test
    void actualizar() {
        assertDoesNotThrow(()->{
            cafeDAO = FactoriaDAO.getInstance().getCafeDAO();
            Cafe cafe = new Cafe("Cafetito", 150, 1.0f, 100,1000);
            cafeDAO.actualizar(cafe);
            cafeDAO.cerrar();
        });
    }
}