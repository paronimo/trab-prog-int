package modelo;

import excepciones.CapacidadExcedidaException;

public class bicicleta extends vehiculo{
    public bicicleta(int patente, int capacidad, float velocidadKmH, int costoPorKm) {
        super(patente, 10, 15, 50);
    }

    @Override
    public double calcularTiempoEntrega(double distanciaKm) {
        return calcularTiempoConExtra(distanciaKm, 0);
    }

    // La bicicleta no puede transportar pedidos frágiles
    @Override
    public boolean puedeTransportar(pedido p) throws CapacidadExcedidaException {
        if (p instanceof pedidoFragil){
            throw new CapacidadExcedidaException("La bicicleta no puede transportar pedidos frágiles.");
        }
        return super.puedeTransportar(p);
    }
}
