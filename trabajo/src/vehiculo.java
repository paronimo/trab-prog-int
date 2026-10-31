public class vehiculo {
    int patente;
    int capacidad;
    float velocidadKmH;
    int costoPorKm;

    private void calcularTiempoEntrega(){
    }
    private void puedeTransportar(){
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public float getVelocidadKmH() {
        return velocidadKmH;
    }

    public void setVelocidadKmH(float velocidadKmH) {
        this.velocidadKmH = velocidadKmH;
    }

    public int getCostoPorKm() {
        return costoPorKm;
    }

    public void setCostoPorKm(int costoPorKm) {
        this.costoPorKm = costoPorKm;
    }

    public int getPatente() {
        return patente;
    }

    public void setPatente(int patente) {
        this.patente = patente;
    }

    public vehiculo(int patente, int capacidad, float velocidadKmH, int costoPorKm) {
        this.patente = patente;
        this.capacidad = capacidad;
        this.velocidadKmH = velocidadKmH;
        this.costoPorKm = costoPorKm;
    }
}
