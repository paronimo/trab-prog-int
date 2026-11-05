package modelo;

import excepciones.AlmacenamientoLlenoException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;
import excepciones.CapacidadExcedidaException;

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

    public void cargarDatosPrueba() throws AlmacenamientoLlenoException, ClienteNoEncontradoException,
            PedidoInvalidoException, CapacidadExcedidaException {
        cliente[] clientesPrueba = {
            new cliente("Centro", 1111111, "Ana", 1),
            new cliente("Norte", 2222222, "Bruno", 2),
            new cliente("Sur", 3333333, "Carla", 3),
            new cliente("Este", 4444444, "Diego", 4),
            new cliente("Oeste", 5555555, "Elena", 5)
        };
        for (cliente clientePrueba : clientesPrueba) {
            registrarCliente(clientePrueba);
        }

        repartidor[] repartidoresPrueba = {
            new repartidor(101, "Rocio", new bicicleta(101, 10, 15, 50), null),
            new repartidor(102, "Martin", new motocicleta(102, 30, 40, 120), null),
            new repartidor(103, "Sofia", new camioneta(103, 500, 30, 300), null)
        };
        for (repartidor repartidorPrueba : repartidoresPrueba) {
            registrarRepartidor(repartidorPrueba);
        }

        registrarPedido(new pedidoNormal(1, clientesPrueba[0], "Plaza Central", 2, 4,
                java.time.LocalDateTime.now()));
        registrarPedido(new pedidoUrgente(2, clientesPrueba[1], "Hospital Norte", 5, 8,
                java.time.LocalDateTime.now()));
        registrarPedido(new pedidoFragil(3, clientesPrueba[2], "Farmacia Sur", 3, 6,
                java.time.LocalDateTime.now()));
        registrarPedido(new pedidoNormal(4, clientesPrueba[3], "Escuela Este", 4, 10,
                java.time.LocalDateTime.now()));
        registrarPedido(new pedidoUrgente(5, clientesPrueba[4], "Terminal Oeste", 12, 14,
                java.time.LocalDateTime.now()));
        registrarPedido(new pedidoFragil(6, clientesPrueba[0], "Clinica Centro", 8, 3,
                java.time.LocalDateTime.now()));
        registrarPedido(new pedidoNormal(7, clientesPrueba[1], "Parque Norte", 7, 5,
                java.time.LocalDateTime.now()));
        registrarPedido(new pedidoUrgente(8, clientesPrueba[2], "Estacion Sur", 20, 12,
                java.time.LocalDateTime.now()));

        asignarPedido(1, 101);
        asignarPedido(2, 102);
        asignarPedido(3, 103);
        asignarPedido(4, 101);
        asignarPedido(5, 102);
        asignarPedido(6, 103);
        asignarPedido(7, 101);
        asignarPedido(8, 103);

        buscarPedido(1).cambiarEstado("EN_CAMINO");
        buscarPedido(1).cambiarEstado("ENTREGADO");
        buscarPedido(3).cambiarEstado("EN_CAMINO");
        buscarPedido(3).cambiarEstado("ENTREGADO");
    }

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
        if (nuevo.getCliente() == null) {
            throw new PedidoInvalidoException("El pedido debe tener un cliente.");
        }
        nuevo.setCliente(buscarCliente(nuevo.getCliente().getId()));
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

    public void asignarPedido(int codigoPedido, int legajo)
            throws CapacidadExcedidaException, AlmacenamientoLlenoException {
        buscarRepartidor(legajo).asignarPedido(buscarPedido(codigoPedido));
    }

    public pedido buscarPedido(int codigo) {
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getId() == codigo) {
                return pedidos[i];
            }
        }
        throw new IllegalArgumentException("No existe un pedido con el código " + codigo + ".");
    }

    public repartidor buscarRepartidor(int legajo) {
        for (int i = 0; i < cantidadRepartidores; i++) {
            if (repartidores[i].getLegajo() == legajo) {
                return repartidores[i];
            }
        }
        throw new IllegalArgumentException("No existe un repartidor con ese legajo.");
    }

    public void cambiarEstadoPedido(int codigoPedido, String nuevoEstado) {
        pedido encontrado = buscarPedido(codigoPedido);
        if ("ENTREGADO".equals(nuevoEstado)) {
            if (encontrado.getRepartidorAsignado() == null) {
                throw new IllegalArgumentException("El pedido no está asignado a un repartidor.");
            }
            encontrado.getRepartidorAsignado().registrarEntrega(encontrado);
        } else {
            encontrado.cambiarEstado(nuevoEstado);
        }
    }

    public pedido[] getPedidos() {
        pedido[] resultado = new pedido[cantidadPedidos];
        System.arraycopy(pedidos, 0, resultado, 0, cantidadPedidos);
        return resultado;
    }

    public repartidor[] getRepartidores() {
        repartidor[] resultado = new repartidor[cantidadRepartidores];
        System.arraycopy(repartidores, 0, resultado, 0, cantidadRepartidores);
        return resultado;
    }

    public double calcularImporteTotal() {
        double total = 0;
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getRepartidorAsignado() != null) {
                total += pedidos[i].calcularImporte();
            }
        }
        return total;
    }
}
