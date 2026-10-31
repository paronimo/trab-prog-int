public class cliente {
    //Atributos
    private final int id;
    private String nombre;
    private int telefono;
    private String zona;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre.isEmpty()){
            System.out.println("No esta permitido los vacios.");
        }
        if (nombre.isBlank()){
            System.out.println("Llene la respuesta con caracteres reales.");
        }
        else {
            this.nombre = nombre;
        }
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        /*if (telefono) {
            System.out.println("No esta permitido los vacios.");
        }
        if (telefono.isBlank()) {
            System.out.println("Llene la respuesta con caracteres reales.");
        } else {*/
            this.telefono = telefono;
       // }
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        if(zona.isEmpty()){
            System.out.println("No esta permitido los vacios.");
        }
        if (zona.isBlank()){
            System.out.println("Llene la respuesta con caracteres reales.");
        }
        else {
            this.zona = zona;
        }
    }
 //Constuctor
    public cliente(String zona, int telefono, String nombre, int id) {
        this.zona = zona;
        this.telefono = telefono;
        this.nombre = nombre;
        this.id = id;
    }

}

