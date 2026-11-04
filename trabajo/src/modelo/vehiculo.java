package modelo;

import excepciones.CapacidadExcedidaException;

public abstract class vehiculo {
    private int patente;
    private int capacidad;
    private float velocidadKmH;
    private int costoPorKm;

    // REGLA: tiempo (min) = (distancia / velocidad) * 60 + tiempo extra del vehículo
    public abstract double calcularTiempoEntrega(double distanciaKm);

    protected double calcularTiempoConExtra(double distanciaKm, double tiempoExtra) {
        if (distanciaKm <= 0) {
            throw new IllegalArgumentException("La distancia debe ser mayor que cero.");
        }
        return (distanciaKm / velocidadKmH) * 60 + tiempoExtra;
    }

    // Tiempo extra propio de cada vehículo (bicicleta 0, moto 5, camioneta 10)
    public double getTiempoExtra(){
        return 0;
    }

    // REGLA: no puede superar la capacidad ni llevar frágiles en bicicleta
    public boolean puedeTransportar(pedido p) throws CapacidadExcedidaException {
        if (p == null) {
            throw new IllegalArgumentException("El pedido es obligatorio.");
        }
        if (p.getPesokg() > capacidad){
            throw new CapacidadExcedidaException("El pedido supera la capacidad del vehículo.");
        }
        return true;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva.");
        }
        this.capacidad = capacidad;
    }

    public float getVelocidadKmH() {
        return velocidadKmH;
    }

    public void setVelocidadKmH(float velocidadKmH) {
        if (!Float.isFinite(velocidadKmH) || velocidadKmH <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser positiva.");
        }
        this.velocidadKmH = velocidadKmH;
    }

    public int getCostoPorKm() {
        return costoPorKm;
    }

    public void setCostoPorKm(int costoPorKm) {
        if (costoPorKm < 0) {
            throw new IllegalArgumentException("El costo por kilómetro no puede ser negativo.");
        }
        this.costoPorKm = costoPorKm;
    }

    public int getPatente() {
        return patente;
    }

    public void setPatente(int patente) {
        this.patente = patente;
    }

    public vehiculo(int patente, int capacidad, float velocidadKmH, int costoPorKm) {
        if (patente <= 0 || capacidad <= 0 || !Float.isFinite(velocidadKmH)
                || velocidadKmH <= 0 || costoPorKm < 0) {
            throw new IllegalArgumentException("Los datos del vehículo deben ser válidos.");
        }
        this.patente = patente;
        this.capacidad = capacidad;
        this.velocidadKmH = velocidadKmH;
        this.costoPorKm = costoPorKm;
    }
}
