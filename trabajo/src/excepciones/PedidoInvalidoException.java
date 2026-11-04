package excepciones;

public class PedidoInvalidoException extends Exception {
    private static final long serialVersionUID = 1L;

    public PedidoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
