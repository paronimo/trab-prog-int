package modelo;

public class cliente {
    //Atributos
    private int id;
    private String nombre;
    private int telefono;
    private String zona;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El código debe ser positivo.");
        }
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        if (telefono <= 0) {
            throw new IllegalArgumentException("El teléfono debe ser positivo.");
        }
        this.telefono = telefono;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        if (zona == null || zona.isBlank()) {
            throw new IllegalArgumentException("La zona no puede estar vacía.");
        }
        this.zona = zona;
    }

    public cliente() {
    }

    public cliente(String zona, int telefono, String nombre, int id) {
        if (id <= 0 || telefono <= 0 || nombre == null || nombre.isBlank()
                || zona == null || zona.isBlank()) {
            throw new IllegalArgumentException("Los datos del cliente deben ser válidos.");
        }
        this.id = id;
        this.zona = zona;
        this.telefono = telefono;
        this.nombre = nombre;
    }
}
