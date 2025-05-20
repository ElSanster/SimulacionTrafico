import java.util.concurrent.*;
import javax.swing.JOptionPane;
import java.util.List;

public class Interseccion {
    //Semáforos de las avenidas
    private Semaforo smDownAvVertical;
    private Semaforo smUpAvVertical;
    private Semaforo smLeftAvHorizontal;
    private Semaforo smRightAvHorizontal;
    /**
     * Lista qué almacena los semáforos de la avenida vertical
     */
    private List<Semaforo> semaforosVerticales;
    /**
     * Lista qué almacena los semáforos de la avenida horizontal
     */
    private List<Semaforo> semaforosHorizontales;
    /**
     * Encargado de las acciones entre cada ciclo y cambio de semáforo<br/><br/>
     * Más información en su respectiva clase
     */
    private CicloSemaforo cicloSemaforo;
    /**
     * Dictamina si se pausa o continua la simulación
     */
    private boolean simulando = false;
    /**
     * Dictamina si se hace la priorización automática
     */
    private boolean prioridadAuto = false;
    /**
     * Indica el tiempo inicial que indica el usuario
     */
    static private int tiempoBase;
    
    /**
     * Inicia el simulador, inicializa el reloj, llama a los métodos para manejar los ciclos,
     * muestra el estado en la consola y muestra el panel de control en JOption
     * y maneja sus respectivas opciones
     * @param tiempoBaseSemaforo tiempo que cuenta el reloj para hacer el ciclo
     * @param distanciaInterseccion distancia a la que los autos salen de la simulación
     * @param maxVehiculosEnInterseccion máxima cantidad de vehiculos que caben en la intersección
     */
    public Interseccion(int tiempoBaseSemaforo, int distanciaInterseccion, int maxVehiculosEnInterseccion) {
        smDownAvVertical = new Semaforo(tiempoBaseSemaforo, false, distanciaInterseccion, maxVehiculosEnInterseccion);
        smUpAvVertical = new Semaforo(tiempoBaseSemaforo, false, distanciaInterseccion, maxVehiculosEnInterseccion);
        smLeftAvHorizontal = new Semaforo(tiempoBaseSemaforo, false, distanciaInterseccion, maxVehiculosEnInterseccion);
        smRightAvHorizontal = new Semaforo(tiempoBaseSemaforo, false, distanciaInterseccion, maxVehiculosEnInterseccion);
        semaforosVerticales = List.of(smDownAvVertical, smUpAvVertical);
        semaforosHorizontales = List.of(smLeftAvHorizontal, smRightAvHorizontal);
        cicloSemaforo = new CicloSemaforo(semaforosVerticales, semaforosHorizontales);
    }
    
    /**
     * Inicializa el reloj principal qué se encarga de la gestión de los semáforos
     */
    public void iniciarSimulacion() {
        simulando = true;
        //Aquí nuestro amigo SchedulerExecutorService ejecuta el contenido qué le pedimos cada segundo:
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        //Alojamos el scheduler en un hilo aparte
        scheduler.scheduleAtFixedRate(() -> {
            if (simulando) {
                cicloSemaforo.ejecutarCiclo();
                cicloSemaforo.prioridadAutomatica(prioridadAuto, tiempoBase);
                gestionarTrafico();
                mostrarEstado();
            }
        }, 0, 1, TimeUnit.SECONDS); //Establecemos en cuantas unidades ejecutamos nuestro código
    }
    /**
     * Cambia el estado de la simulacion a detenida o reaunudada
     */
    public void cambiarSimulacion() {
        simulando = !simulando;
        System.out.println("Simulación"+(simulando? " reanudada.":" detenida."));
    }
    /**
    * Establece la prioridad de la avenida especificada en el tiempo solicitado
    * @param avenida "vertical" ó "horizontal", Avenida a seleccionar
    * @param tiempoPrioridad Tiempo en segundos a establecer
    */
    public void establecerPrioridad(String avenida, int tiempoPrioridad) {
        cicloSemaforo.establecerPrioridad(avenida, tiempoPrioridad, prioridadAuto);
    }

    // Métodos para añadir vehículos manualmente
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
    
    /**
     * Hace la verificación de autos que hayan salido de la intersección para sacarlos de la simulación
     */
    private void gestionarTrafico() {
        //Qué cada semáforo se encargue de sumar y sacar vehículos de su lista de intersección
        semaforosVerticales.forEach(Semaforo::gestionarVehiculosEnInterseccion);
        semaforosHorizontales.forEach(Semaforo::gestionarVehiculosEnInterseccion);
        //Por cada semáforo en su avenida, si está en verde que envíe un vehiculo a su intersección
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
    /**
     * Muestra en consola los datos de la simulación para el usuario
     */
    private void mostrarEstado() {
        System.out.println("\n\n\n\n\nEstado de la Intersección:");
        System.out.println(   prioridadAuto ? "-Prioridad automática-" : "-Prioridad Manual-");
        System.out.println("  Tiempo desde que se cambió de semáforo: "+cicloSemaforo.getContadorTiempo());
        System.out.println("  Tiempo actual de prioridad de la avenida vertical: "+ cicloSemaforo.getTiempoVerticalVerde());
        System.out.println("  Tiempo actual de prioridad de avenida horizontal:"+ cicloSemaforo.getTiempoHorizontalVerde());
        System.out.println(" Promedio del tiempo actual de prioridad en ambos semáforos: "+((cicloSemaforo.getTiempoHorizontalVerde()+cicloSemaforo.getTiempoVerticalVerde())/2));
        System.out.println("  Vertical (Abajo) - Cola: " + smDownAvVertical.cantidadVehiculosEnCola() + ", En Intersección: " + smDownAvVertical.getVehiculosEnInterseccion().size() + (smDownAvVertical.estaEnVerde() ? "(Verde)" : "(Rojo)"));
        System.out.println("  Vertical (Arriba) - Cola: " + smUpAvVertical.cantidadVehiculosEnCola() + ", En Intersección: " + smUpAvVertical.getVehiculosEnInterseccion().size() + (smUpAvVertical.estaEnVerde() ? "(Verde)" : "(Rojo)"));
        System.out.println("  Horizontal (Izquierda) - Cola: " + smLeftAvHorizontal.cantidadVehiculosEnCola() + ", En Intersección: " + smLeftAvHorizontal.getVehiculosEnInterseccion().size() + (smLeftAvHorizontal.estaEnVerde() ? "(Verde)" : "(Rojo)"));
        System.out.println("  Horizontal (Derecha) - Cola: " + smRightAvHorizontal.cantidadVehiculosEnCola() + ", En Intersección: " + smRightAvHorizontal.getVehiculosEnInterseccion().size() + (smRightAvHorizontal.estaEnVerde() ? "(Verde)" : "(Rojo)"));
    }
    /**
     * Envía al usuario una JOptionPane.showInputDialog con el mensaje solicitado,
     * y verifica qué sea un int.<br/><br/>
     * Usa un ciclo while y un try catch para evitar NumberFormatException
     * @param mensaje Mensaje para el usuario
     * @return El int solicitado
     */
    static private int IntInputMessageDialog(String mensaje){
        int salida = 0;
        while (true){
        try{
            salida = Integer.parseInt(JOptionPane.showInputDialog(mensaje));
            break;
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Ingrese un número entero, por favor.");
        }
        }
        return salida;
    }

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        tiempoBase = IntInputMessageDialog("Ingrese el tiempo base de los semáforos (segundos): ");
        Interseccion interseccion = new Interseccion(tiempoBase, 5, 5); // Distancia y max veh�culos en intersecci�n m�s bajos para ver el cruce m�s r�pido
        interseccion.iniciarSimulacion();

        // Añadir vehículos manualmente
        interseccion.agregarVehiculoVerticalAbajo(new Vehiculo());
        interseccion.agregarVehiculoHorizontalIzquierda(new Vehiculo());
        interseccion.agregarVehiculoVerticalArriba(new Vehiculo());
        interseccion.agregarVehiculoHorizontalDerecha(new Vehiculo());

        while (true) {
            String menu = "Nota: el estado de interseción está en la consola\n"+
            "\nOpciones:\n"+
            "1. Establecer prioridad de la avenida Vertical (tiempo en segundos)\n"+
            "2. Establecer prioridad de la avenida Horizontal (tiempo en segundos)\n"+
            "3. "+(interseccion.prioridadAuto ? "Deshabilitar" : "Habilitar ")+" Prioridad automática.\n"+
            "4. "+(interseccion.simulando? "Detener ":"Reaunudar ") + "la simulación\n"+
            "5. Salir\n"+
            "Seleccione una opción:"
            ;
            int opcion = IntInputMessageDialog(menu);

            switch (opcion) {
                case 1:
                    int tiempoVertical = IntInputMessageDialog("Ingrese el tiempo de prioridad para la avenida Vertical: ");
                    interseccion.establecerPrioridad("vertical", tiempoVertical);
                    break;
                case 2:
                    int tiempoHorizontal = IntInputMessageDialog("Ingrese el tiempo de prioridad para la avenida Horizontal: ");
                    interseccion.establecerPrioridad("horizontal", tiempoHorizontal);
                    break;
                case 3:
                    interseccion.prioridadAuto = !interseccion.prioridadAuto;
                    break;
                case 4:
                    interseccion.cambiarSimulacion();
                    break;
                case 5:
                    if (interseccion.simulando) interseccion.cambiarSimulacion();
                    scanner.close();
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null,"Opción inválida.");
            }
        }
    }
}