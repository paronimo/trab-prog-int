package modelo;

public class motocicleta extends vehiculo{
    public motocicleta(int patente, int capacidad, float velocidadKmH, int costoPorKm) {
        super(patente, 30, 40, 120);
    }

    @Override
    public double calcularTiempoEntrega(double distanciaKm) {
        return calcularTiempoConExtra(distanciaKm, 5);
    }

    @Override
    public double getTiempoExtra(){
        return 5; // estacionamiento
    }
}
