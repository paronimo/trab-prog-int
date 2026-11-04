package modelo;

public class camioneta extends vehiculo{
    public camioneta(int patente, int capacidad, float velocidadKmH, int costoPorKm) {
        super(patente, 500, 30, 300);
    }

    @Override
    public double calcularTiempoEntrega(double distanciaKm) {
        return calcularTiempoConExtra(distanciaKm, 10);
    }

    @Override
    public double getTiempoExtra(){
        return 10; // carga y descarga
    }
}
