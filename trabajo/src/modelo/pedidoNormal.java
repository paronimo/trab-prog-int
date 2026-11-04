package modelo;

import java.time.LocalDateTime;
import excepciones.PedidoInvalidoException;

public class pedidoNormal extends pedido {
    public pedidoNormal(int id, cliente cliente, String destino, float pesokg, float distanciaKm,
                        LocalDateTime fechaHora) throws PedidoInvalidoException {
        super(id, cliente, destino, pesokg, distanciaKm, fechaHora);
    }

    @Override
    protected double calcularCosto() {
        return calcularCostoBase();
    }

    public boolean esFragil() {
        return false;
    }
}
