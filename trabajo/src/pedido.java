import java.time.LocalDateTime;
import java.util.UUID;

public class pedido implements rastreable, facturable{
    private UUID id;
    private String cliente;
    private String destino;
    private float pesokg;
    private float distanciaKm;
    private LocalDateTime fechaHora;
    private String estado;

    public pedido() {
    }

    private void calcularCosto(){
    }

    public UUID getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public float getPesokg() {
        return pesokg;
    }

    public void setPesokg(float pesokg) {
        this.pesokg = pesokg;
    }

    public float getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(float distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public pedido(int id, String cliente, String destino, float pesokg, float distanciaKm, LocalDateTime fechaHora, String estado) {
        this.id = UUID.randomUUID();
        this.cliente = cliente;
        this.destino = destino;
        this.pesokg = pesokg;
        this.distanciaKm = distanciaKm;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    private void cambiarEstado(){
    }

    @Override
    public void calcularImporte() {

    }

    @Override
    public void obtenerEstadoActual() {

    }
}
