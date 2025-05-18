import java.util.concurrent.*;

import java.util.List;

public class Interseccion {
    private Semaforo smDownAvVertical;
    private Semaforo smUpAvVertical;
    private Semaforo smLeftAvHorizontal;
    private Semaforo smRightAvHorizontal;
    private List<Semaforo> semaforosVerticales;
    private List<Semaforo> semaforosHorizontales;
    private CicloSemaforo cicloSemaforo;
    private boolean simulando = false;

    public Interseccion(int tiempoBaseSemaforo, int distanciaInterseccion, int maxVehiculosEnInterseccion) {
        smDownAvVertical = new Semaforo(tiempoBaseSemaforo, false, distanciaInterseccion, maxVehiculosEnInterseccion);
        smUpAvVertical = new Semaforo(tiempoBaseSemaforo, false, distanciaInterseccion, maxVehiculosEnInterseccion);
        smLeftAvHorizontal = new Semaforo(tiempoBaseSemaforo, false, distanciaInterseccion, maxVehiculosEnInterseccion);
        smRightAvHorizontal = new Semaforo(tiempoBaseSemaforo, false, distanciaInterseccion, maxVehiculosEnInterseccion);
        semaforosVerticales = List.of(smDownAvVertical, smUpAvVertical);
        semaforosHorizontales = List.of(smLeftAvHorizontal, smRightAvHorizontal);
        cicloSemaforo = new CicloSemaforo(semaforosVerticales, semaforosHorizontales);
    }

    public void iniciarSimulacion() {
        simulando = true;
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            if (simulando) {
                cicloSemaforo.ejecutarCiclo();
                gestionarTrafico();
                mostrarEstado();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void detenerSimulacion() {
        simulando = false;
        System.out.println("Simulaci�n detenida.");
    }

    public void reanudarSimulacion() {
        simulando = true;
        System.out.println("Simulaci�n reanudada.");
    }

    public void establecerPrioridad(String avenida, int tiempoPrioridad) {
        cicloSemaforo.establecerPrioridad(avenida, tiempoPrioridad);
    }

    // M�todos para a�adir veh�culos manualmente
    public void agregarVehiculoVerticalAbajo(Vehiculo vehiculo) {
        smDownAvVertical.agregarVehiculo(vehiculo);
    }

    public void agregarVehiculoVerticalArriba(Vehiculo vehiculo) {
        smUpAvVertical.agregarVehiculo(vehiculo);
    }

    public void agregarVehiculoHorizontalIzquierda(Vehiculo vehiculo) {
        smLeftAvHorizontal.agregarVehiculo(vehiculo);
    }

    public void agregarVehiculoHorizontalDerecha(Vehiculo vehiculo) {
        smRightAvHorizontal.agregarVehiculo(vehiculo);
    }

    private void gestionarTrafico() {
        semaforosVerticales.forEach(Semaforo::gestionarVehiculosEnInterseccion);
        semaforosHorizontales.forEach(Semaforo::gestionarVehiculosEnInterseccion);
        semaforosVerticales.forEach(semaforo -> {
            if (semaforo.estaEnVerde()) {
                semaforo.liberarVehiculo();
            }
        });
        semaforosHorizontales.forEach(semaforo -> {
            if (semaforo.estaEnVerde()) {
                semaforo.liberarVehiculo();
            }
        });
    }

    private void mostrarEstado() {
        System.out.println("Estado de la Intersecci�n:");
        System.out.println("  Vertical (Abajo): Cola=" + smDownAvVertical.cantidadVehiculosEnCola() + ", En Intersecci�n=" + smDownAvVertical.getVehiculosEnInterseccion().size() + (smDownAvVertical.estaEnVerde() ? "(Verde)" : "(Rojo)"));
        System.out.println("  Vertical (Arriba): Cola=" + smUpAvVertical.cantidadVehiculosEnCola() + ", En Intersecci�n=" + smUpAvVertical.getVehiculosEnInterseccion().size() + (smUpAvVertical.estaEnVerde() ? "(Verde)" : "(Rojo)"));
        System.out.println("  Horizontal (Izquierda): Cola=" + smLeftAvHorizontal.cantidadVehiculosEnCola() + ", En Intersecci�n=" + smLeftAvHorizontal.getVehiculosEnInterseccion().size() + (smLeftAvHorizontal.estaEnVerde() ? "(Verde)" : "(Rojo)"));
        System.out.println("  Horizontal (Derecha): Cola=" + smRightAvHorizontal.cantidadVehiculosEnCola() + ", En Intersecci�n=" + smRightAvHorizontal.getVehiculosEnInterseccion().size() + (smRightAvHorizontal.estaEnVerde() ? "(Verde)" : "(Rojo)"));
    }

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Ingrese el tiempo base de los sem�foros (segundos): ");
        int tiempoBase = scanner.nextInt();
        Interseccion interseccion = new Interseccion(tiempoBase, 5, 2); // Distancia y max veh�culos en intersecci�n m�s bajos para ver el cruce m�s r�pido
        interseccion.iniciarSimulacion();

        // A�adir veh�culos manualmente
        interseccion.agregarVehiculoVerticalAbajo(new Vehiculo("Sedan", "VAB-111"));
        interseccion.agregarVehiculoHorizontalIzquierda(new Vehiculo("Hatchback", "HIZ-222"));
        interseccion.agregarVehiculoVerticalAbajo(new Vehiculo("SUV", "VAB-333"));
        interseccion.agregarVehiculoHorizontalDerecha(new Vehiculo("Pickup", "HDR-444"));

        while (true) {
            System.out.println("\nOpciones:");
            System.out.println("1. Establecer prioridad de la avenida Vertical (tiempo en segundos)");
            System.out.println("2. Establecer prioridad de la avenida Horizontal (tiempo en segundos)");
            System.out.println("3. Detener la simulaci�n");
            System.out.println("4. Reanudar la simulaci�n");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opci�n: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el tiempo de prioridad para la avenida Vertical: ");
                    int tiempoVertical = scanner.nextInt();
                    interseccion.establecerPrioridad("vertical", tiempoVertical);
                    break;
                case 2:
                    System.out.print("Ingrese el tiempo de prioridad para la avenida Horizontal: ");
                    int tiempoHorizontal = scanner.nextInt();
                    interseccion.establecerPrioridad("horizontal", tiempoHorizontal);
                    break;
                case 3:
                    interseccion.detenerSimulacion();
                    break;
                case 4:
                    interseccion.reanudarSimulacion();
                    break;
                case 5:
                    interseccion.detenerSimulacion();
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opci�n inv�lida.");
            }
        }
    }
}