import java.util.UUID;

public class repartidor implements facturable{
    private final UUID legajo;
    private String nombre;
    private String vehiculo;
    private String[] pedidosAsignados;

    private void asignarPedido(){}
    private void registrarEntrega(){}

    public UUID getLegajo() {
        return legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String[] getPedidosAsignados() {
        return pedidosAsignados;
    }

    public void setPedidosAsignados(String[] pedidosAsignados) {
        this.pedidosAsignados = pedidosAsignados;
    }

    public repartidor(int legajo, String nombre, String vehiculo, String[] pedidosAsignados) {
        this.legajo = UUID.randomUUID();
        this.nombre = nombre;
        this.vehiculo = vehiculo;
        this.pedidosAsignados = pedidosAsignados;
    }

    @Override
    public void calcularImporte() {
    }
}
