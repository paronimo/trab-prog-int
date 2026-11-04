package excepciones;

public class CapacidadExcedidaException extends Exception {
    private static final long serialVersionUID = 1L;

    public CapacidadExcedidaException(String mensaje) {
        super(mensaje);
    }
}
