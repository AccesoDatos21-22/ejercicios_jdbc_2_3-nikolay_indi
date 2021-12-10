import org.iesinfantaelena.dao.FactoriaDAO;
import org.iesinfantaelena.dao.ProveedorDAO;
import org.iesinfantaelena.model.Proveedor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestProveedorDAO {
    private ProveedorDAO proveedorDAO;

    @Test
    void conexion() {
        assertDoesNotThrow(() -> {
            proveedorDAO = FactoriaDAO.getInstance().getProveedorDAO();
            proveedorDAO.cerrar();
        });
    }

    @Test
    void insertar() {
        assertDoesNotThrow(() -> {
            proveedorDAO = FactoriaDAO.getInstance().getProveedorDAO();

            Proveedor pro1 = new Proveedor(1, "Pro_1", "Calle_1", "Collado Villalba", "España", 28400);
            proveedorDAO.insertar(pro1);
            proveedorDAO.insertar(pro1);

            proveedorDAO.cerrar();
        });
    }

    @Test
    void buscar() {
        assertDoesNotThrow(() -> {
            proveedorDAO = FactoriaDAO.getInstance().getProveedorDAO();

            Proveedor pro1 = new Proveedor(1, "Pro_1", "Calle_1", "Collado Villalba", "España", 28400);
            proveedorDAO.buscar("Pro1");
            proveedorDAO.buscar(pro1);

            proveedorDAO.cerrar();
        });
    }

    @Test
    void actualizar() {
        assertDoesNotThrow(() -> {
            proveedorDAO = FactoriaDAO.getInstance().getProveedorDAO();

            Proveedor pro1 = new Proveedor(1, "Pro_1", "Calle_1", "Collado Villalba", "España", 28400);
            proveedorDAO.actualizar(pro1);

            proveedorDAO.cerrar();
        });
    }

    @Test
    void eliminar() {
        assertDoesNotThrow(() -> {
            proveedorDAO = FactoriaDAO.getInstance().getProveedorDAO();

            Proveedor pro1 = new Proveedor(1, "Pro_1", "Calle_1", "Collado Villalba", "España", 28400);
            proveedorDAO.insertar(pro1);
            proveedorDAO.eliminar(pro1);
            proveedorDAO.eliminar(pro1);

            proveedorDAO.cerrar();
        });
    }
}
