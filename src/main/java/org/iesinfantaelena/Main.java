package org.iesinfantaelena;

import org.iesinfantaelena.dao.AccesoDatosException;
import org.iesinfantaelena.dao.JDBCCafeDAO;
import org.iesinfantaelena.model.Cafe;

import java.util.List;

public class Main {
    public static void imprimirLista(List<Cafe> listaCafes){
        for(Cafe lista : listaCafes){
            System.out.println(lista.getNombre() + ", " + lista.getProvid() + ", " + lista.getPrecio() + "€, " + lista.getVentas() + ", " + lista.getTotal());
        }
    }
    public static void main(String[] args) throws AccesoDatosException {
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
        imprimirLista(cafeTemp.buscar("Cappuccino"));

        System.out.println();

        System.out.println("---Borramos el Latte y visualizamos de nuevo la tabla---");
        cafeTemp.borrar(cafe1);
        cafeTemp.verTabla();

        System.out.println();

        System.out.println("---Buscamos los cafes por proveedor---");
        imprimirLista(cafeTemp.cafesPorProveedor(2));

        System.out.println();

        System.out.println("---Transferimos las ventas del Americano al Espresso e imprimimos de nuevo la tabla");
        cafeTemp.transferencia("Americano", "Espresso");
        cafeTemp.verTabla();

        System.out.println();

        System.out.println("---Actualizamos las ventas del Americano e imprimimos la tabla");
        cafeTemp.actualizar(cafe2);
        cafeTemp.verTabla();
    }
}
