package excepciones;

public class SinPedidosPendientesException extends Exception {
    private static final long serialVersionUID = 1L;

    public SinPedidosPendientesException(String mensaje) {
        super(mensaje);
    }
}

