package modelo;

import java.time.LocalDateTime;
import excepciones.PedidoInvalidoException;
import excepciones.EstadoInvalidoException;

public abstract class pedido implements rastreable, facturable {
    private int id; // codigo entero único (el enunciado no pide UUID)
    private vehiculo vehiculoAsignado; // para calcular costo y tiempo con su costo por km
    private cliente cliente;
    private String destino;
    private float pesokg;
    private float distanciaKm;
    private LocalDateTime fechaHora;
    private String estado;

    // REGLA: costo = 1000 + (distancia * costo por km) + recargo según el tipo
    protected abstract double calcularCosto();

    protected double calcularCostoBase() {
        if (vehiculoAsignado == null) {
            throw new IllegalStateException("El pedido todavía no tiene un vehículo asignado.");
        }
        double costo = 1000 + distanciaKm * vehiculoAsignado.getCostoPorKm();
        return costo;
    }

    public int getId() {
        return id;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente es obligatorio.");
        }
        this.cliente = cliente;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) throws PedidoInvalidoException {
        if (destino == null || destino.isBlank()) {
            throw new PedidoInvalidoException("El destino no puede estar vacío.");
        }
        this.destino = destino;
    }

    public float getPesokg() {
        return pesokg;
    }

    public void setPesokg(float pesokg) throws PedidoInvalidoException {
        if (!Float.isFinite(pesokg) || pesokg <= 0) {
            throw new PedidoInvalidoException("El peso debe ser mayor que cero.");
        }
        this.pesokg = pesokg;
    }

    public float getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(float distanciaKm) throws PedidoInvalidoException {
        if (!Float.isFinite(distanciaKm) || distanciaKm <= 0) {
            throw new PedidoInvalidoException("La distancia debe ser mayor que cero.");
        }
        this.distanciaKm = distanciaKm;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora son obligatorias.");
        }
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public pedido(int id, cliente cliente, String destino, float pesokg, float distanciaKm,
                  LocalDateTime fechaHora) throws PedidoInvalidoException {
        if (id <= 0 || cliente == null || destino == null || destino.isBlank()
                || !Float.isFinite(pesokg) || pesokg <= 0
                || !Float.isFinite(distanciaKm) || distanciaKm <= 0 || fechaHora == null) {
            throw new PedidoInvalidoException("Los datos del pedido deben ser válidos.");
        }
        this.id = id;
        this.cliente = cliente;
        this.destino = destino;
        this.pesokg = pesokg;
        this.distanciaKm = distanciaKm;
        this.fechaHora = fechaHora;
        this.estado = "PENDIENTE";
    }

    public void asignarVehiculo(vehiculo v) {
        if (v == null) {
            throw new IllegalArgumentException("El vehículo es obligatorio.");
        }
        this.vehiculoAsignado = v;
    }

    // REGLA: PENDIENTE -> EN_CAMINO -> ENTREGADO, y CANCELADO solo desde PENDIENTE
    public void cambiarEstado(String nuevoEstado) {
        boolean valido =
            ("PENDIENTE".equals(estado) && ("EN_CAMINO".equals(nuevoEstado) || "CANCELADO".equals(nuevoEstado)))
            || ("EN_CAMINO".equals(estado) && "ENTREGADO".equals(nuevoEstado));
        if (valido) {
            estado = nuevoEstado;
        } else {
            throw new EstadoInvalidoException("Transición inválida: " + estado + " -> " + nuevoEstado);
        }
    }

    // REGLA: el pedido informa su importe (el tipo agrega el recargo)
    @Override
    public double calcularImporte() {
        if (vehiculoAsignado == null) {
            throw new IllegalStateException("No se puede calcular el importe sin un vehículo asignado.");
        }
        return calcularCosto();
    }

    @Override
    public String obtenerEstadoActual() {
        return "Pedido N° " + id + " - Estado: " + estado;
    }
}
