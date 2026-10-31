import java.time.LocalDateTime;

public class pedidoNormal extends pedido{
    public pedidoNormal(int id, String cliente, String destino, float pesokg, float distanciaKm, LocalDateTime fechaHora, String estado) {
        super(id, cliente, destino, pesokg, distanciaKm, fechaHora, estado);
    }
}
