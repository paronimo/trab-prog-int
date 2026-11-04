package excepciones;

public class AlmacenamientoLlenoException extends Exception {
    private static final long serialVersionUID = 1L;

    public AlmacenamientoLlenoException(String mensaje) {
        super(mensaje);
    }
}
