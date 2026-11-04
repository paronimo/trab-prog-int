package modelo;

import java.time.LocalDateTime;
import excepciones.PedidoInvalidoException;

public class pedidoFragil extends pedido {
    public pedidoFragil(int id, cliente cliente, String destino, float pesokg, float distanciaKm,
                        LocalDateTime fechaHora) throws PedidoInvalidoException {
        super(id, cliente, destino, pesokg, distanciaKm, fechaHora);
    }

    // REGLA: frágil suma un adicional fijo de $800
    @Override
    protected double calcularCosto() {
        return calcularCostoBase() + 800;
    }

    public boolean esFragil() {
        return true;
    }
}
