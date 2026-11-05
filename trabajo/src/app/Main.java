package app;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.EstadoInvalidoException;
import excepciones.PedidoInvalidoException;
import java.time.LocalDateTime;
import java.util.Scanner;
import modelo.CityLink;
import modelo.bicicleta;
import modelo.camioneta;
import modelo.cliente;
import modelo.motocicleta;
import modelo.pedido;
import modelo.pedidoFragil;
import modelo.pedidoNormal;
import modelo.pedidoUrgente;
import modelo.repartidor;
import modelo.vehiculo;

public class Main {
    public static void main(String[] args) {
        CityLink cityLink = new CityLink();
        Scanner scanner = new Scanner(System.in);

        try {
            cityLink.cargarDatosPrueba();
        } catch (AlmacenamientoLlenoException | ClienteNoEncontradoException
                 | PedidoInvalidoException | CapacidadExcedidaException e) {
            System.out.println("No se pudieron cargar los datos de prueba: " + e.getMessage());
            scanner.close();
            return;
        }

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero(scanner, "Opción: ");
            try {
                switch (opcion) {
                    case 1 -> registrarCliente(scanner, cityLink);
                    case 2 -> registrarRepartidor(scanner, cityLink);
                    case 3 -> registrarPedido(scanner, cityLink);
                    case 4 -> asignarPedido(scanner, cityLink);
                    case 5 -> cambiarEstado(scanner, cityLink);
                    case 6 -> buscarCliente(scanner, cityLink);
                    case 7 -> listarPedidos(cityLink);
                    case 8 -> consultarEstado(scanner, cityLink);
                    case 9 -> mostrarImportes(cityLink);
                    case 10 -> continuar = false;
                    default -> System.out.println("Opción fuera de rango.");
                }
            } catch (AlmacenamientoLlenoException | ClienteNoEncontradoException
                     | PedidoInvalidoException | CapacidadExcedidaException
                     | IllegalArgumentException | EstadoInvalidoException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
        System.out.println("CityLink finalizado.");
    }

    private static void mostrarMenu() {
        System.out.println("\n=== CityLink ===");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Registrar repartidor con vehículo");
        System.out.println("3. Registrar pedido");
        System.out.println("4. Asignar pedido");
        System.out.println("5. Cambiar estado del pedido");
        System.out.println("6. Buscar cliente");
        System.out.println("7. Listar pedidos con costo y tiempo");
        System.out.println("8. Consultar estado de pedido o repartidor");
        System.out.println("9. Ver importes y comisiones");
        System.out.println("10. Salir");
    }

    private static void registrarCliente(Scanner scanner, CityLink cityLink)
            throws AlmacenamientoLlenoException {
        int codigo = leerEntero(scanner, "Código: ");
        String nombre = leerTexto(scanner, "Nombre: ");
        int telefono = leerEntero(scanner, "Teléfono: ");
        String zona = leerTexto(scanner, "Zona: ");
        cityLink.registrarCliente(new cliente(zona, telefono, nombre, codigo));
        System.out.println("Cliente registrado.");
    }

    private static void registrarRepartidor(Scanner scanner, CityLink cityLink)
            throws AlmacenamientoLlenoException {
        int legajo = leerEntero(scanner, "Legajo: ");
        String nombre = leerTexto(scanner, "Nombre: ");
        int patente = leerEntero(scanner, "Patente (número): ");
        System.out.println("Vehículo: 1. Bicicleta  2. Moto  3. Camioneta");
        int tipoVehiculo = leerEntero(scanner, "Tipo: ");
        vehiculo vehiculoAsignado = switch (tipoVehiculo) {
            case 1 -> new bicicleta(patente, 10, 15, 50);
            case 2 -> new motocicleta(patente, 30, 40, 120);
            case 3 -> new camioneta(patente, 500, 30, 300);
            default -> throw new IllegalArgumentException("Tipo de vehículo inválido.");
        };
        cityLink.registrarRepartidor(new repartidor(legajo, nombre, vehiculoAsignado, null));
        System.out.println("Repartidor registrado.");
    }

    private static void registrarPedido(Scanner scanner, CityLink cityLink)
            throws AlmacenamientoLlenoException, ClienteNoEncontradoException, PedidoInvalidoException {
        int codigo = leerEntero(scanner, "Código del pedido: ");
        int codigoCliente = leerEntero(scanner, "Código del cliente: ");
        cliente clientePedido = cityLink.buscarCliente(codigoCliente);
        String destino = leerTexto(scanner, "Destino: ");
        float peso = leerDecimal(scanner, "Peso en kg: ");
        float distancia = leerDecimal(scanner, "Distancia en km: ");
        System.out.println("Tipo: 1. Normal  2. Urgente  3. Frágil");
        int tipo = leerEntero(scanner, "Tipo: ");
        LocalDateTime fechaHora = LocalDateTime.now();

        pedido nuevo = switch (tipo) {
            case 1 -> new pedidoNormal(codigo, clientePedido, destino, peso, distancia, fechaHora);
            case 2 -> new pedidoUrgente(codigo, clientePedido, destino, peso, distancia, fechaHora);
            case 3 -> new pedidoFragil(codigo, clientePedido, destino, peso, distancia, fechaHora);
            default -> throw new IllegalArgumentException("Tipo de pedido inválido.");
        };
        cityLink.registrarPedido(nuevo);
        System.out.println("Pedido registrado.");
    }

    private static void asignarPedido(Scanner scanner, CityLink cityLink)
            throws AlmacenamientoLlenoException, CapacidadExcedidaException {
        int codigoPedido = leerEntero(scanner, "Código del pedido: ");
        int legajo = leerEntero(scanner, "Legajo del repartidor: ");
        cityLink.asignarPedido(codigoPedido, legajo);
        System.out.println("Pedido asignado.");
    }

    private static void cambiarEstado(Scanner scanner, CityLink cityLink) {
        int codigo = leerEntero(scanner, "Código del pedido: ");
        System.out.println("Estado: 1. En camino  2. Entregado  3. Cancelado");
        String estado = switch (leerEntero(scanner, "Opción: ")) {
            case 1 -> "EN_CAMINO";
            case 2 -> "ENTREGADO";
            case 3 -> "CANCELADO";
            default -> throw new IllegalArgumentException("Estado inválido.");
        };
        cityLink.cambiarEstadoPedido(codigo, estado);
        System.out.println("Estado actualizado.");
    }

    private static void buscarCliente(Scanner scanner, CityLink cityLink)
            throws ClienteNoEncontradoException {
        System.out.println("Buscar por: 1. Código  2. Nombre");
        cliente encontrado = switch (leerEntero(scanner, "Opción: ")) {
            case 1 -> cityLink.buscarCliente(leerEntero(scanner, "Código: "));
            case 2 -> cityLink.buscarCliente(leerTexto(scanner, "Nombre: "));
            default -> throw new IllegalArgumentException("Opción de búsqueda inválida.");
        };
        System.out.println(encontrado);
    }

    private static void listarPedidos(CityLink cityLink) {
        pedido[] pedidos = cityLink.getPedidos();
        if (pedidos.length == 0) {
            System.out.println("No hay pedidos registrados.");
            return;
        }
        for (pedido pedidoActual : pedidos) {
            System.out.print(pedidoActual.obtenerEstadoActual());
            if (pedidoActual.getRepartidorAsignado() == null) {
                System.out.println(" - sin repartidor asignado");
            } else {
                vehiculo vehiculoAsignado = pedidoActual.getRepartidorAsignado().getVehiculo();
                double costo = pedidoActual.calcularImporte();
                double minutos = vehiculoAsignado.calcularTiempoEntrega(pedidoActual.getDistanciaKm());
                System.out.printf(" - costo: $%.2f - tiempo: %.1f minutos%n", costo, minutos);
            }
        }
    }

    private static void consultarEstado(Scanner scanner, CityLink cityLink) {
        System.out.println("Consultar: 1. Pedido  2. Repartidor");
        switch (leerEntero(scanner, "Opción: ")) {
            case 1 -> System.out.println(cityLink.buscarPedido(
                    leerEntero(scanner, "Código del pedido: ")).obtenerEstadoActual());
            case 2 -> System.out.println(cityLink.buscarRepartidor(
                    leerEntero(scanner, "Legajo del repartidor: ")).obtenerEstadoActual());
            default -> throw new IllegalArgumentException("Opción de consulta inválida.");
        }
    }

    private static void mostrarImportes(CityLink cityLink) {
        System.out.printf("Importe total de pedidos asignados: $%.2f%n",
                cityLink.calcularImporteTotal());
        for (repartidor repartidorActual : cityLink.getRepartidores()) {
            System.out.printf("Comisión de %s: $%.2f%n",
                    repartidorActual.getNombre(), repartidorActual.calcularImporte());
        }
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private static float leerDecimal(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim().replace(',', '.');
            try {
                return Float.parseFloat(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private static String leerTexto(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
}
