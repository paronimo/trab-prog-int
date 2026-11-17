package modelo;

import algoritmos.Algoritmos;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.TreeMap;

public class CityLink {
    private final TreeMap<Integer, cliente> clientes = new TreeMap<>();
    private final ArrayList<pedido> pedidos = new ArrayList<>();
    private final ArrayList<repartidor> repartidores = new ArrayList<>();
    private final Deque<pedido> colaUrgentes = new ArrayDeque<>();
    private final Deque<pedido> colaNormales = new ArrayDeque<>();
    private final Deque<String> pilaOperaciones = new ArrayDeque<>();

    public void cargarDatosPrueba() throws ClienteNoEncontradoException,
            PedidoInvalidoException, CapacidadExcedidaException {
        List<cliente> clientesPrueba = List.of(
            new cliente("Centro", 1111111, "Ana", 1),
            new cliente("Norte", 2222222, "Bruno", 2),
            new cliente("Sur", 3333333, "Carla", 3),
            new cliente("Este", 4444444, "Diego", 4),
            new cliente("Oeste", 5555555, "Elena", 5)
        );
        for (cliente clientePrueba : clientesPrueba) {
            registrarCliente(clientePrueba);
        }

        List<repartidor> repartidoresPrueba = List.of(
            new repartidor(101, "Rocio", new bicicleta(101, 10, 15, 50), null),
            new repartidor(102, "Martin", new motocicleta(102, 30, 40, 120), null),
            new repartidor(103, "Sofia", new camioneta(103, 500, 30, 300), null)
        );
        for (repartidor repartidorPrueba : repartidoresPrueba) {
            registrarRepartidor(repartidorPrueba);
        }

        registrarPedido(new pedidoNormal(1, clientesPrueba.get(0), "Plaza Central", 2, 4,
                LocalDateTime.now()));
        registrarPedido(new pedidoUrgente(2, clientesPrueba.get(1), "Hospital Norte", 5, 8,
                LocalDateTime.now()));
        registrarPedido(new pedidoFragil(3, clientesPrueba.get(2), "Farmacia Sur", 3, 6,
                LocalDateTime.now()));
        registrarPedido(new pedidoNormal(4, clientesPrueba.get(3), "Escuela Este", 4, 10,
                LocalDateTime.now()));
        registrarPedido(new pedidoUrgente(5, clientesPrueba.get(4), "Terminal Oeste", 12, 14,
                LocalDateTime.now()));
        registrarPedido(new pedidoFragil(6, clientesPrueba.get(0), "Clinica Centro", 8, 3,
                LocalDateTime.now()));
        registrarPedido(new pedidoNormal(7, clientesPrueba.get(1), "Parque Norte", 7, 5,
                LocalDateTime.now()));
        registrarPedido(new pedidoUrgente(8, clientesPrueba.get(2), "Estacion Sur", 20, 12,
                LocalDateTime.now()));

        asignarPedido(1, 101);
        asignarPedido(2, 102);
        asignarPedido(3, 103);
        asignarPedido(4, 101);
        asignarPedido(5, 102);
        asignarPedido(6, 103);
        asignarPedido(7, 101);
        asignarPedido(8, 103);

        cambiarEstadoPedido(1, "EN_CAMINO");
        cambiarEstadoPedido(1, "ENTREGADO");
        cambiarEstadoPedido(3, "EN_CAMINO");
        cambiarEstadoPedido(3, "ENTREGADO");
    }

    public void registrarCliente(cliente nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("El cliente es obligatorio.");
        }
        if (nuevo.getId() <= 0) {
            throw new IllegalArgumentException("El código del cliente debe ser positivo.");
        }
        if (clientes.containsKey(nuevo.getId())) {
            throw new IllegalArgumentException("Ya existe un cliente con ese código.");
        }
        clientes.put(nuevo.getId(), nuevo);
    }

    public void eliminarCliente(int codigo) throws ClienteNoEncontradoException {
        if (clientes.remove(codigo) == null) {
            throw new ClienteNoEncontradoException(
                    "No existe un cliente con el código " + codigo + ".");
        }
    }

    public void registrarPedido(pedido nuevo)
            throws ClienteNoEncontradoException, PedidoInvalidoException {
        if (nuevo == null) {
            throw new PedidoInvalidoException("El pedido es obligatorio.");
        }
        if (nuevo.getCliente() == null) {
            throw new PedidoInvalidoException("El pedido debe tener un cliente.");
        }
        nuevo.setCliente(buscarCliente(nuevo.getCliente().getId()));
        if (Algoritmos.buscarPedidoLineal(pedidos, nuevo.getId()) != null) {
            throw new PedidoInvalidoException("Ya existe un pedido con ese código.");
        }
        pedidos.add(nuevo);
        if (nuevo instanceof pedidoUrgente) {
            colaUrgentes.addLast(nuevo);
        } else {
            colaNormales.addLast(nuevo);
        }
        pilaOperaciones.push("ALTA_PEDIDO:" + nuevo.getId());
    }

    public void registrarRepartidor(repartidor nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("El repartidor es obligatorio.");
        }
        repartidores.add(nuevo);
    }

    public cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {
        cliente encontrado = clientes.get(codigo);
        if (encontrado == null) {
            throw new ClienteNoEncontradoException(
                    "No existe un cliente con el código " + codigo + ".");
        }
        return encontrado;
    }

    public cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
        cliente encontrado = Algoritmos.buscarClientePorNombreLineal(
                new ArrayList<>(clientes.values()), nombre);
        if (encontrado == null) {
            throw new ClienteNoEncontradoException("No existe un cliente con ese nombre.");
        }
        return encontrado;
    }

    public void asignarPedido(int codigoPedido, int legajo)
            throws CapacidadExcedidaException {
        pedido pedidoAsignado = buscarPedido(codigoPedido);
        buscarRepartidor(legajo).asignarPedido(pedidoAsignado);
        colaUrgentes.remove(pedidoAsignado);
        colaNormales.remove(pedidoAsignado);
        pilaOperaciones.push("ASIGNAR_PEDIDO:" + codigoPedido);
    }

    public pedido buscarPedido(int codigo) {
        pedido encontrado = Algoritmos.buscarPedidoLineal(pedidos, codigo);
        if (encontrado == null) {
            throw new IllegalArgumentException("No existe un pedido con el código " + codigo + ".");
        }
        return encontrado;
    }

    public pedido buscarPedidoBinaria(int codigo) {
        List<pedido> pedidosOrdenados = Algoritmos.ordenarInsercion(
                pedidos, Comparator.comparingInt(pedido::getId));
        return Algoritmos.buscarPedidoBinaria(pedidosOrdenados, codigo);
    }

    public repartidor buscarRepartidor(int legajo) {
        for (repartidor repartidorActual : repartidores) {
            if (repartidorActual.getLegajo() == legajo) {
                return repartidorActual;
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
            if ("CANCELADO".equals(nuevoEstado)) {
                colaUrgentes.remove(encontrado);
                colaNormales.remove(encontrado);
            }
        }
        pilaOperaciones.push("CAMBIAR_ESTADO:" + codigoPedido + ":" + nuevoEstado);
    }

    public List<cliente> getClientes() {
        return new ArrayList<>(clientes.values());
    }

    public List<pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }

    public List<repartidor> getRepartidores() {
        return new ArrayList<>(repartidores);
    }

    public Deque<pedido> getColaUrgentes() {
        return new ArrayDeque<>(colaUrgentes);
    }

    public Deque<pedido> getColaNormales() {
        return new ArrayDeque<>(colaNormales);
    }

    public Deque<String> getPilaOperaciones() {
        return new ArrayDeque<>(pilaOperaciones);
    }

    public double calcularImporteTotal() {
        double total = 0;
        for (pedido pedidoActual : pedidos) {
            if (pedidoActual.getRepartidorAsignado() != null) {
                total += pedidoActual.calcularImporte();
            }
        }
        return total;
    }
}
