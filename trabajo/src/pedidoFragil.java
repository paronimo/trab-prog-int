import java.time.LocalDateTime;

public class pedidoFragil extends pedido{
    public pedidoFragil(int id, String cliente, String destino, float pesokg, float distanciaKm, LocalDateTime fechaHora, String estado) {
        super(id, cliente, destino, pesokg, distanciaKm, fechaHora, estado);
    }
}
