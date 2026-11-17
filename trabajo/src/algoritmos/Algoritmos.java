package algoritmos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import modelo.cliente;
import modelo.pedido;

public final class Algoritmos {
    private Algoritmos() {
    }

    public static pedido buscarPedidoLineal(List<pedido> pedidos, int codigo) {
        for (pedido pedidoActual : pedidos) {
            if (pedidoActual.getId() == codigo) {
                return pedidoActual;
            }
        }
        return null;
    }

    public static cliente buscarClientePorNombreLineal(List<cliente> clientes, String nombre) {
        if (nombre == null) {
            return null;
        }
        for (cliente clienteActual : clientes) {
            if (clienteActual.getNombre().equalsIgnoreCase(nombre.trim())) {
                return clienteActual;
            }
        }
        return null;
    }

    public static pedido buscarPedidoBinaria(List<pedido> pedidos, int codigo) {
        int inicio = 0;
        int fin = pedidos.size() - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            pedido pedidoMedio = pedidos.get(medio);
            if (pedidoMedio.getId() == codigo) {
                return pedidoMedio;
            }
            if (pedidoMedio.getId() < codigo) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return null;
    }

    public static <T> List<T> ordenarBurbuja(List<T> elementos, Comparator<? super T> criterio) {
        List<T> resultado = new ArrayList<>(elementos);
        for (int fin = resultado.size() - 1; fin > 0; fin--) {
            boolean huboIntercambio = false;
            for (int i = 0; i < fin; i++) {
                if (criterio.compare(resultado.get(i), resultado.get(i + 1)) > 0) {
                    T temporal = resultado.get(i);
                    resultado.set(i, resultado.get(i + 1));
                    resultado.set(i + 1, temporal);
                    huboIntercambio = true;
                }
            }
            if (!huboIntercambio) {
                break;
            }
        }
        return resultado;
    }

    public static <T> List<T> ordenarInsercion(List<T> elementos, Comparator<? super T> criterio) {
        List<T> resultado = new ArrayList<>(elementos);
        for (int i = 1; i < resultado.size(); i++) {
            T elemento = resultado.get(i);
            int j = i - 1;
            while (j >= 0 && criterio.compare(resultado.get(j), elemento) > 0) {
                resultado.set(j + 1, resultado.get(j));
                j--;
            }
            resultado.set(j + 1, elemento);
        }
        return resultado;
    }

    public static <T> List<T> intercalar(List<T> primero, List<T> segundo,
                                         Comparator<? super T> criterio) {
        List<T> resultado = new ArrayList<>(primero.size() + segundo.size());
        int i = 0;
        int j = 0;
        while (i < primero.size() && j < segundo.size()) {
            if (criterio.compare(primero.get(i), segundo.get(j)) <= 0) {
                resultado.add(primero.get(i++));
            } else {
                resultado.add(segundo.get(j++));
            }
        }
        while (i < primero.size()) {
            resultado.add(primero.get(i++));
        }
        while (j < segundo.size()) {
            resultado.add(segundo.get(j++));
        }
        return resultado;
    }
}
