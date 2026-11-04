package modelo;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;

public class repartidor implements rastreable, facturable {
    private final int legajo;
    private String nombre;
    private vehiculo vehiculo; // antes era String: necesita el objeto para capacidad/costo
    private pedido[] pedidosAsignados;
    private int cantidadPedidos;

    // REGLA: solo se asigna si el vehículo puede transportarlo
    public void asignarPedido(pedido p) throws CapacidadExcedidaException, AlmacenamientoLlenoException {
        if (cantidadPedidos == pedidosAsignados.length) {
            throw new AlmacenamientoLlenoException("El repartidor alcanzó el máximo de pedidos asignados.");
        }
        this.vehiculo.puedeTransportar(p);
        p.asignarVehiculo(this.vehiculo);
        pedidosAsignados[cantidadPedidos++] = p;
    }

    public void registrarEntrega(pedido pedido) {
        if (pedido == null || !contienePedido(pedido)) {
            throw new IllegalArgumentException("El pedido no está asignado a este repartidor.");
        }
        pedido.cambiarEstado("ENTREGADO");
    }

    private boolean contienePedido(pedido buscado) {
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidosAsignados[i] == buscado) {
                return true;
            }
        }
        return false;
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

    public pedido[] getPedidosAsignados() {
        return pedidosAsignados;
    }

    public repartidor(int legajo, String nombre, vehiculo vehiculo, pedido[] pedidosAsignados) {
        if (legajo <= 0 || nombre == null || nombre.isBlank() || vehiculo == null) {
            throw new IllegalArgumentException("Legajo, nombre y vehículo deben ser válidos.");
        }
        this.legajo = legajo;
        this.nombre = nombre;
        this.vehiculo = vehiculo;
        this.pedidosAsignados = pedidosAsignados == null ? new pedido[100] : pedidosAsignados;
    }

    // REGLA: comisión = 10% del importe de los pedidos entregados
    @Override
    public double calcularImporte() {
        double importe = 0;
        for (int i = 0; i < cantidadPedidos; i++) {
            if ("ENTREGADO".equals(pedidosAsignados[i].getEstado())) {
                importe += pedidosAsignados[i].calcularImporte();
            }
        }
        return importe * 0.10;
    }

    @Override
    public String obtenerEstadoActual() {
        return "Repartidor " + nombre + " - pedidos asignados: " + cantidadPedidos;
    }
}
