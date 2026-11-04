package modelo;

import excepciones.AlmacenamientoLlenoException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;

public class CityLink {
    private static final int CAPACIDAD_CLIENTES = 50;
    private static final int CAPACIDAD_PEDIDOS = 100;
    private static final int CAPACIDAD_REPARTIDORES = 10;

    private final cliente[] clientes = new cliente[CAPACIDAD_CLIENTES];
    private final pedido[] pedidos = new pedido[CAPACIDAD_PEDIDOS];
    private final repartidor[] repartidores = new repartidor[CAPACIDAD_REPARTIDORES];
    private int cantidadClientes;
    private int cantidadPedidos;
    private int cantidadRepartidores;

    public void registrarCliente(cliente nuevo) throws AlmacenamientoLlenoException {
        if (cantidadClientes == clientes.length) {
            throw new AlmacenamientoLlenoException("No hay espacio para más clientes.");
        }
        if (nuevo == null) {
            throw new IllegalArgumentException("El cliente es obligatorio.");
        }
        if (nuevo.getId() <= 0) {
            throw new IllegalArgumentException("El código del cliente debe ser positivo.");
        }
        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getId() == nuevo.getId()) {
                throw new IllegalArgumentException("Ya existe un cliente con ese código.");
            }
        }
        clientes[cantidadClientes++] = nuevo;
    }

    public void registrarPedido(pedido nuevo)
            throws AlmacenamientoLlenoException, ClienteNoEncontradoException, PedidoInvalidoException {
        if (cantidadPedidos == pedidos.length) {
            throw new AlmacenamientoLlenoException("No hay espacio para más pedidos.");
        }
        if (nuevo == null) {
            throw new PedidoInvalidoException("El pedido es obligatorio.");
        }
        buscarCliente(nuevo.getCliente().getId());
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getId() == nuevo.getId()) {
                throw new PedidoInvalidoException("Ya existe un pedido con ese código.");
            }
        }
        pedidos[cantidadPedidos++] = nuevo;
    }

    public void registrarRepartidor(repartidor nuevo) throws AlmacenamientoLlenoException {
        if (cantidadRepartidores == repartidores.length) {
            throw new AlmacenamientoLlenoException("No hay espacio para más repartidores.");
        }
        if (nuevo == null) {
            throw new IllegalArgumentException("El repartidor es obligatorio.");
        }
        repartidores[cantidadRepartidores++] = nuevo;
    }

    public cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {
        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getId() == codigo) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException("No existe un cliente con el código " + codigo + ".");
    }

    public cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
        if (nombre != null) {
            for (int i = 0; i < cantidadClientes; i++) {
                if (clientes[i].getNombre().equalsIgnoreCase(nombre.trim())) {
                    return clientes[i];
                }
            }
        }
        throw new ClienteNoEncontradoException("No existe un cliente con ese nombre.");
    }
}
