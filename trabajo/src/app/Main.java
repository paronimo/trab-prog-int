package app;

import algoritmos.Algoritmos;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.EstadoInvalidoException;
import excepciones.PedidoInvalidoException;
import excepciones.SinPedidosPendientesException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
        } catch (ClienteNoEncontradoException | PedidoInvalidoException
                 | CapacidadExcedidaException e) {
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
                    case 1 -> gestionarClientes(scanner, cityLink);
                    case 2 -> gestionarRepartidores(scanner, cityLink);
                    case 3 -> registrarPedido(scanner, cityLink);
                    case 4 -> atenderSiguientePedido(cityLink);
                    case 5 -> registrarEntrega(scanner, cityLink);
                    case 6 -> deshacerUltimaOperacion(cityLink);
                    case 7 -> ordenarYListarPedidos(scanner, cityLink);
                    case 8 -> buscarPedido(scanner, cityLink);
                    case 9 -> intercalarTurnos(scanner, cityLink);
                    case 10 -> continuar = false;
                    default -> System.out.println("Opción fuera de rango.");
                }
            } catch (ClienteNoEncontradoException | PedidoInvalidoException
                     | CapacidadExcedidaException | SinPedidosPendientesException
                     | IllegalArgumentException | IllegalStateException
                     | EstadoInvalidoException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
        System.out.println("CityLink finalizado.");
    }

    private static void mostrarMenu() {
        System.out.println("\n=== CityLink ===");
        System.out.println("1. Gestión de clientes");
        System.out.println("2. Gestión de repartidores");
        System.out.println("3. Registrar un pedido");
        System.out.println("4. Atender el siguiente pedido");
        System.out.println("5. Registrar una entrega");
        System.out.println("6. Deshacer la última operación");
        System.out.println("7. Ordenar y listar pedidos");
        System.out.println("8. Buscar un pedido");
        System.out.println("9. Intercalar pedidos de dos turnos");
        System.out.println("10. Salir");
    }

    private static void gestionarClientes(Scanner scanner, CityLink cityLink)
            throws ClienteNoEncontradoException {
        System.out.println("\n--- Clientes ---");
        System.out.println("1. Alta");
        System.out.println("2. Baja por código");
        System.out.println("3. Buscar por código");
        System.out.println("4. Listar por código");
        int opcion = leerEntero(scanner, "Opción: ");
        switch (opcion) {
            case 1 -> {
                int codigo = leerEntero(scanner, "Código: ");
                String nombre = leerTexto(scanner, "Nombre: ");
                int telefono = leerEntero(scanner, "Teléfono: ");
                String zona = leerTexto(scanner, "Zona: ");
                cityLink.registrarCliente(new cliente(zona, telefono, nombre, codigo));
                System.out.println("Cliente registrado.");
            }
            case 2 -> {
                cityLink.eliminarCliente(leerEntero(scanner, "Código del cliente: "));
                System.out.println("Cliente eliminado.");
            }
            case 3 -> System.out.println(cityLink.buscarCliente(
                    leerEntero(scanner, "Código del cliente: ")));
            case 4 -> {
                List<cliente> clientes = cityLink.getClientes();
                if (clientes.isEmpty()) {
                    System.out.println("No hay clientes registrados.");
                } else {
                    for (cliente clienteActual : clientes) {
                        System.out.println(clienteActual);
                    }
                }
            }
            default -> System.out.println("Opción de clientes inválida.");
        }
    }

    private static void gestionarRepartidores(Scanner scanner, CityLink cityLink) {
        System.out.println("\n--- Repartidores ---");
        System.out.println("1. Registrar repartidor");
        System.out.println("2. Listar repartidores e historial");
        int opcion = leerEntero(scanner, "Opción: ");
        if (opcion == 1) {
            registrarRepartidor(scanner, cityLink);
        } else if (opcion == 2) {
            List<repartidor> repartidores = cityLink.getRepartidores();
            if (repartidores.isEmpty()) {
                System.out.println("No hay repartidores registrados.");
                return;
            }
            for (repartidor repartidorActual : repartidores) {
                System.out.printf("%s - legajo: %d - capacidad: %.1f kg - asignados: %d%n",
                        repartidorActual.getNombre(), repartidorActual.getLegajo(),
                        (double) repartidorActual.getVehiculo().getCapacidad(),
                        repartidorActual.getCantidadPedidos());
                System.out.println("  Entregas: " + repartidorActual.getHistorialEntregas().size());
                for (pedido entrega : repartidorActual.getHistorialEntregas()) {
                    System.out.println("  - Pedido " + entrega.getId() + ": " + entrega.getDestino());
                }
            }
        } else {
            System.out.println("Opción de repartidores inválida.");
        }
    }

    private static void registrarRepartidor(Scanner scanner, CityLink cityLink) {
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
            throws ClienteNoEncontradoException, PedidoInvalidoException {
        int codigo = leerEntero(scanner, "Código del pedido: ");
        int codigoCliente = leerEntero(scanner, "Código del cliente: ");
        cliente clientePedido = cityLink.buscarCliente(codigoCliente);
        String destino = leerTexto(scanner, "Destino: ");
        float peso = leerDecimal(scanner, "Peso en kg: ");
        float distancia = leerDecimal(scanner, "Distancia en km: ");
        System.out.println("Tipo: 1. Normal  2. Urgente  3. Frágil");
        int tipo = leerEntero(scanner, "Tipo: ");

        pedido nuevo = switch (tipo) {
            case 1 -> new pedidoNormal(codigo, clientePedido, destino, peso, distancia,
                    LocalDateTime.now());
            case 2 -> new pedidoUrgente(codigo, clientePedido, destino, peso, distancia,
                    LocalDateTime.now());
            case 3 -> new pedidoFragil(codigo, clientePedido, destino, peso, distancia,
                    LocalDateTime.now());
            default -> throw new IllegalArgumentException("Tipo de pedido inválido.");
        };
        cityLink.registrarPedido(nuevo);
        System.out.println("Pedido registrado y agregado a la cola correspondiente.");
    }

    private static void atenderSiguientePedido(CityLink cityLink)
            throws CapacidadExcedidaException, SinPedidosPendientesException {
        pedido atendido = cityLink.atenderSiguientePedido();
        System.out.printf("Pedido %d asignado a %s y marcado EN_CAMINO.%n",
                atendido.getId(), atendido.getRepartidorAsignado().getNombre());
    }

    private static void registrarEntrega(Scanner scanner, CityLink cityLink) {
        int codigo = leerEntero(scanner, "Código del pedido entregado: ");
        cityLink.cambiarEstadoPedido(codigo, "ENTREGADO");
        System.out.println("Entrega registrada en el historial del repartidor.");
    }

    private static void deshacerUltimaOperacion(CityLink cityLink) {
        cityLink.deshacerUltimaOperacion();
        System.out.println("Última operación deshecha.");
    }

    private static void ordenarYListarPedidos(Scanner scanner, CityLink cityLink) {
        System.out.println("Criterio: 1. Costo  2. Distancia  3. Fecha");
        Comparator<pedido> criterio = leerCriterio(scanner);
        System.out.println("Algoritmo: 1. Burbuja  2. Inserción");
        int algoritmo = leerEntero(scanner, "Opción: ");

        List<pedido> pedidos = cityLink.getPedidos();
        List<pedido> ordenados = switch (algoritmo) {
            case 1 -> Algoritmos.ordenarBurbuja(pedidos, criterio);
            case 2 -> Algoritmos.ordenarInsercion(pedidos, criterio);
            default -> throw new IllegalArgumentException("Algoritmo inválido.");
        };
        mostrarPedidos(ordenados);
    }

    private static void buscarPedido(Scanner scanner, CityLink cityLink) {
        System.out.println("Búsqueda: 1. Lineal  2. Binaria");
        int algoritmo = leerEntero(scanner, "Opción: ");
        int codigo = leerEntero(scanner, "Código del pedido: ");
        pedido encontrado = switch (algoritmo) {
            case 1 -> Algoritmos.buscarPedidoLineal(cityLink.getPedidos(), codigo);
            case 2 -> cityLink.buscarPedidoBinaria(codigo);
            default -> throw new IllegalArgumentException("Algoritmo de búsqueda inválido.");
        };
        if (encontrado == null) {
            System.out.println("No existe un pedido con ese código.");
        } else {
            mostrarPedidos(List.of(encontrado));
        }
    }

    private static void intercalarTurnos(Scanner scanner, CityLink cityLink) {
        System.out.println("Criterio: 1. Costo  2. Distancia  3. Fecha");
        Comparator<pedido> criterio = leerCriterio(scanner);
        List<pedido> manana = leerPedidosTurno(scanner, cityLink, "mañana");
        List<pedido> tarde = leerPedidosTurno(scanner, cityLink, "tarde");
        for (pedido pedidoManana : manana) {
            if (tarde.contains(pedidoManana)) {
                throw new IllegalArgumentException("Un pedido no puede pertenecer a ambos turnos.");
            }
        }
        List<pedido> mananaOrdenado = Algoritmos.ordenarInsercion(manana, criterio);
        List<pedido> tardeOrdenado = Algoritmos.ordenarInsercion(tarde, criterio);
        mostrarPedidos(Algoritmos.intercalar(mananaOrdenado, tardeOrdenado, criterio));
    }

    private static List<pedido> leerPedidosTurno(Scanner scanner, CityLink cityLink, String turno) {
        String entrada = leerTexto(scanner,
                "Códigos de pedidos del turno " + turno + " (separados por coma; vacío si no hay): ");
        List<pedido> pedidosTurno = new ArrayList<>();
        if (entrada.isBlank()) {
            return pedidosTurno;
        }
        for (String codigoTexto : entrada.split("[,;\\s]+")) {
            int codigo;
            try {
                codigo = Integer.parseInt(codigoTexto);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Código de pedido inválido: " + codigoTexto);
            }
            pedido pedidoActual = cityLink.buscarPedido(codigo);
            if (pedidosTurno.contains(pedidoActual)) {
                throw new IllegalArgumentException("El pedido " + codigo + " está repetido.");
            }
            pedidosTurno.add(pedidoActual);
        }
        return pedidosTurno;
    }

    private static Comparator<pedido> leerCriterio(Scanner scanner) {
        return switch (leerEntero(scanner, "Opción: ")) {
            case 1 -> Comparator.comparingDouble(pedido::calcularImporte);
            case 2 -> Comparator.comparingDouble(pedido::getDistanciaKm);
            case 3 -> Comparator.comparing(pedido::getFechaHora);
            default -> throw new IllegalArgumentException("Criterio de orden inválido.");
        };
    }

    private static void mostrarPedidos(List<pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos para mostrar.");
            return;
        }
        for (pedido pedidoActual : pedidos) {
            String detalle = String.format("Pedido %d - %s - %s - distancia: %.1f km",
                    pedidoActual.getId(), pedidoActual.getEstado(),
                    pedidoActual.getDestino(), pedidoActual.getDistanciaKm());
            if (pedidoActual.getRepartidorAsignado() != null) {
                detalle += String.format(" - costo: $%.2f", pedidoActual.calcularImporte());
            } else {
                detalle += " - sin repartidor asignado";
            }
            System.out.println(detalle);
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
