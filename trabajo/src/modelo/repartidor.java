package modelo;

import excepciones.CapacidadExcedidaException;
import java.util.ArrayList;
import java.util.List;

public class repartidor implements rastreable, facturable {
    private final int legajo;
    private String nombre;
    private vehiculo vehiculo; // antes era String: necesita el objeto para capacidad/costo
    private final List<pedido> pedidosAsignados = new ArrayList<>();
    private final List<pedido> historialEntregas = new ArrayList<>();

    // REGLA: solo se asigna si el vehículo puede transportarlo
    public void asignarPedido(pedido p) throws CapacidadExcedidaException {
        if (p == null) {
            throw new IllegalArgumentException("El pedido es obligatorio.");
        }
        if (p.getRepartidorAsignado() != null) {
            throw new IllegalArgumentException("El pedido ya tiene un repartidor asignado.");
        }
        if (!"PENDIENTE".equals(p.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden asignar pedidos pendientes.");
        }
        this.vehiculo.puedeTransportar(p);
        p.asignarRepartidor(this);
        pedidosAsignados.add(p);
    }

    public void registrarEntrega(pedido pedido) {
        if (pedido == null || !contienePedido(pedido)) {
            throw new IllegalArgumentException("El pedido no está asignado a este repartidor.");
        }
        pedido.cambiarEstado("ENTREGADO");
        historialEntregas.add(pedido);
    }

    void desasignarPedido(pedido pedido) {
        if (!pedidosAsignados.remove(pedido)) {
            throw new IllegalArgumentException("El pedido no está asignado a este repartidor.");
        }
        pedido.desasignarRepartidor(this);
    }

    void quitarEntrega(pedido pedido) {
        if (!historialEntregas.remove(pedido)) {
            throw new IllegalArgumentException("El pedido no está en el historial de entregas.");
        }
    }

    private boolean contienePedido(pedido buscado) {
        return pedidosAsignados.contains(buscado);
    }

    public int getLegajo() {
        return legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo es obligatorio.");
        }
        this.vehiculo = vehiculo;
    }

    public List<pedido> getPedidosAsignados() {
        return new ArrayList<>(pedidosAsignados);
    }

    public List<pedido> getHistorialEntregas() {
        return new ArrayList<>(historialEntregas);
    }

    public repartidor(int legajo, String nombre, vehiculo vehiculo, List<pedido> pedidosAsignados) {
        if (legajo <= 0 || nombre == null || nombre.isBlank() || vehiculo == null) {
            throw new IllegalArgumentException("Legajo, nombre y vehículo deben ser válidos.");
        }
        this.legajo = legajo;
        this.nombre = nombre;
        this.vehiculo = vehiculo;
        if (pedidosAsignados != null) {
            for (pedido asignado : pedidosAsignados) {
                if (asignado != null) {
                    this.pedidosAsignados.add(asignado);
                }
            }
        }
    }

    // REGLA: comisión = 10% del importe de los pedidos entregados
    @Override
    public double calcularImporte() {
        double importe = 0;
        for (pedido pedidoActual : pedidosAsignados) {
            if ("ENTREGADO".equals(pedidoActual.getEstado())) {
                importe += pedidoActual.calcularImporte();
            }
        }
        return importe * 0.10;
    }

    @Override
    public String obtenerEstadoActual() {
        return "Repartidor " + nombre + " - pedidos asignados: " + pedidosAsignados.size();
    }

    public int getCantidadPedidos() {
        return pedidosAsignados.size();
    }
}
