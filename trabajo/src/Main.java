import java.util.Scanner;
void main() {
    System.out.println("_________________________________________________________");
    System.out.println("|       Sistemas de gestion         |      Citylink      |");
    System.out.println("|___________________________________|____________________|");
    System.out.println("1)Registrar Cliente");
    System.out.println("2)Registrar repartidor con su vehiculo");
    System.out.println("3)Registrar pedido (normal, urgente o frágil).");
    System.out.println("4)Asignar un pedido a un repartidor, verificando capacidad y restricciones.");
    System.out.println("5)Cambiar el estado de un pedido (en camino, entregado, cancelado).");
    System.out.println("6)Buscar un cliente por código o por nombre.");
    System.out.println("8)Listar pedidos con su costo y tiempo estimado (polimorfismo).");
    System.out.println("9)Consultar el estado de un pedido o de un repartidor (Rastreable).");
    System.out.println("10)Ver el importe total de los pedidos y la comisión de cada repartidor (Facturable).");
    System.out.println("11)Salir");
    System.out.print("\nIngresa un número entero: ");
    Scanner scanner = new Scanner(System.in);
    int opc = scanner.nextInt();
    scanner.close();
    switch (opc) {
        case 1:
            System.out.printf("1");
            break;
        case 2:
            System.out.printf("2");
            break;
        case 3:
            System.out.printf("3");
            break;
        case 4:
            System.out.printf("4");
            break;
        case 5:
            System.out.printf("5");
            break;
        case 6:
            System.out.printf("6");
            break;
        case 7:
            System.out.printf("7");
            break;
        case 8:
            System.out.printf("8");
            break;
        case 9:
            System.out.printf("9");
            break;
        case 10:
            System.out.printf("10");
            break;
        case 11:
            System.out.printf("11");
            break;
        default:
    }
    /*
    bicicleta bici1 = new bicicleta(17671676,20, 23, 300);
    System.out.println(bici1.getCapacidad());
    //pedidoUrgente botiquin = new pedidoUrgente()
    repartidor repartidor1 = new repartidor(21727367, Jorge,bici1, asignacion1);
    System.out.println(repartidor.getNombre);*/
    return;
}
