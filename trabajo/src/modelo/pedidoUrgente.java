package modelo;

import java.time.LocalDateTime;
import excepciones.PedidoInvalidoException;

public class pedidoUrgente extends pedido {
    public pedidoUrgente(int id, cliente cliente, String destino, float pesokg, float distanciaKm,
                         LocalDateTime fechaHora) throws PedidoInvalidoException {
        super(id, cliente, destino, pesokg, distanciaKm, fechaHora);
    }

    // REGLA: urgente suma 50% sobre el total
    @Override
    protected double calcularCosto() {
        return calcularCostoBase() * 1.5;
    }
}
