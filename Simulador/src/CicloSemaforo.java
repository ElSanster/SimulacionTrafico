import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class CicloSemaforo {
    /**
     *Semáforos de la Avenida Vertical
     */
    private List<Semaforo> semaforosVerticales;
    /**
     *Semáforos de la Avenida Horizontal
     */
    private List<Semaforo> semaforosHorizontales;
    /**
     *Tiempo en verde para la Avenida Vertical
     */
    private int tiempoVerticalVerde;
    /**
     *Tiempo en verde para la Avenida Horizontal
     */
    private int tiempoHorizontalVerde;
    /**
     *Almacena la avenida en verde
     */
    private String avenidaActiva = "vertical";
    /**
     *Almacena el tiempo desde el último ciclo
     */
    private int contadorTiempo = 0;
    private Random random = new Random();
    /**
     * Inicializa el administrador del ciclo del semáforo.<br/><br/>
     * Este tiene las tareas para manejar cada ciclo qué pasa entre cambio y cambio de semáforo, 
     * como establecer tiempos de prioridad, obtener estos tiempos de prioridad,
     * ejecutar la prioridad automática, ejecutar el ciclo y cambiar de color los semáforos.
     * @param semaforosVerticales Lista de semáforos para la avenida Vertical
     * @param semaforosHorizontales Lista de semáforos para la avenida Horizontal
     */
    public CicloSemaforo(List<Semaforo> semaforosVerticales, List<Semaforo> semaforosHorizontales) {
        this.semaforosVerticales = semaforosVerticales;
        this.semaforosHorizontales = semaforosHorizontales;
        this.tiempoVerticalVerde = semaforosVerticales.get(0).getTiempoVerde();
        this.tiempoHorizontalVerde = semaforosHorizontales.get(0).getTiempoVerde();
        activarVerde("vertical");
    }
    /**
     * Establece el tiempo en verde de una avenida y notifica al usuario este cambio en caso de ser manual.
     * @param avenida "vertical" ó "horizontal", especifica a qué avenida cambia el valor
     * @param tiempoPrioridad Cantidad en segundos para cambiar
     * @param prioridadAuto Mostrar ventana de confirmación de la nueva prioridad
     */
    public void establecerPrioridad(String avenida, int tiempoPrioridad, boolean prioridadAuto) {
        if (avenida.equalsIgnoreCase("vertical")) {
            this.tiempoVerticalVerde = tiempoPrioridad;
            System.out.println("\t\tPrioridad de la avenida Vertical establecida en " + tiempoPrioridad + " segundos.");
            if (!prioridadAuto)
                JOptionPane.showMessageDialog(null,
                        "\t\tPrioridad de la avenida Vertical establecida en " + tiempoPrioridad + " segundos.");
        } else if (avenida.equalsIgnoreCase("horizontal")) {
            this.tiempoHorizontalVerde = tiempoPrioridad;
            System.out.println("\t\tPrioridad de la avenida Horizontal establecida en " + tiempoPrioridad + " segundos.");
            if (!prioridadAuto)
                JOptionPane.showMessageDialog(null,
                        "\t\tPrioridad de la avenida Horizontal establecida en " + tiempoPrioridad + " segundos.");
        } else {
            if (!prioridadAuto) System.out.println("Opción de avenida inválida. Use 'vertical' o 'horizontal'.");
            JOptionPane.showMessageDialog(null, "Opción de avenida inválida. Use 'vertical' o 'horizontal'.");
            
            
        }
    }

    public int getTiempoHorizontalVerde() {
        return tiempoHorizontalVerde;
    }

    public int getTiempoVerticalVerde() {
        return tiempoVerticalVerde;
    }

    /**
     * Realiza el cambio de colores en los semáforos y genera un auto al azar <br/><br/>
     * Nota: Este método al ser ejecutado cada segundo, se genera un auto 
     * en una probabilidad de 50% repartida entre los semáforos
     */
    public void ejecutarCiclo() {
        contadorTiempo++;
        if (avenidaActiva.equals("vertical")) {
            if (contadorTiempo >= tiempoVerticalVerde) {
                cambiarAvenidaActiva("horizontal");
                contadorTiempo = 0;
            }
        } else {
            if (contadorTiempo >= tiempoHorizontalVerde) {
                cambiarAvenidaActiva("vertical");
                contadorTiempo = 0;
            }
        }
        int randomSemaforo = random.nextInt(8);// Generar un número entre 0 y 7 (8 números)
        if (randomSemaforo <= 3) {//50 % de probabilidades de generar un carro cada segundo (4/8)
            switch (randomSemaforo) {
                case 0:
                semaforosHorizontales.get(0).agregarVehiculo(new Vehiculo());
                    break;
                case 1:
                semaforosHorizontales.get(1).agregarVehiculo(new Vehiculo());
                    break;
                case 2:
                semaforosVerticales.get(0).agregarVehiculo(new Vehiculo());
                    break;
                case 3:
                semaforosVerticales.get(1).agregarVehiculo(new Vehiculo());
                    break;
                default:
                semaforosVerticales.get(0).agregarVehiculo(new Vehiculo());
                    break;
            }
        }
    }

    public int getContadorTiempo() {
            return contadorTiempo;
    }

    /**
     * Pone en color verde la avenida enviada, hace el respectivo cambio con la que está en verde.
     * @param nuevaAvenida "vertical" ó "horizontal", avenida a poner en verde
     */
    private void cambiarAvenidaActiva(String nuevaAvenida) {
        desactivarVerde(avenidaActiva);
        activarVerde(nuevaAvenida);
        avenidaActiva = nuevaAvenida;
        System.out.println(
            "Cambiando a verde la avenida " + (avenidaActiva.equals("vertical") ? "Vertical" : "Horizontal") + ".");
    }
    
    /**
     * Pone en verde la avenida mencionada
     * @param avenida "vertical" ó "horizontal", Avenida a poner en verde
     */
    private void activarVerde(String avenida) {
        if (avenida.equalsIgnoreCase("vertical")) {
            semaforosVerticales.forEach(Semaforo::cambiarEstado);
        } else if (avenida.equalsIgnoreCase("horizontal")) {
            semaforosHorizontales.forEach(Semaforo::cambiarEstado);
        }
    }

    /**
     * Pone en rojo la avenida mencionada
     * @param avenida "vertical" ó "horizontal", Avenida a poner en rojo
     */
    private void desactivarVerde(String avenida) {
        if (avenida.equalsIgnoreCase("vertical")) {
            semaforosVerticales.forEach(Semaforo::cambiarEstado);
        } else if (avenida.equalsIgnoreCase("horizontal")) {
            semaforosHorizontales.forEach(Semaforo::cambiarEstado);
        }
    }

    /**
     * Establece las prioridades en base de los autos en las avenidas.<br/><br/>
     * Le da el doble de tiempo base a la avenida con más carros y
     * le reduce la que tiene menos, los deja igual en caso contrario.
     * @param prioridadAuto Permitir Ejecución
     * @param tiempoBase Tiempo a restablecer cuando los autos sean iguales
     */
    public void prioridadAutomatica (boolean prioridadAuto, int tiempoBase){
        if (prioridadAuto){
            int autosVertical=0, autosHorizontal=0;
            for (Semaforo semaforoH : semaforosHorizontales) {
                autosHorizontal += semaforoH.cantidadVehiculosEnCola();
            }
            for (Semaforo semaforoV: semaforosVerticales){
                autosVertical += semaforoV.cantidadVehiculosEnCola();
            }
            if (autosHorizontal < autosVertical){
                establecerPrioridad("vertical", tiempoBase*2, prioridadAuto);
                establecerPrioridad("horizontal", tiempoBase/2, prioridadAuto);                
            }
            if (autosHorizontal> autosVertical){
                establecerPrioridad("vertical", tiempoBase/2, prioridadAuto);
                establecerPrioridad("horizontal", tiempoBase*2, prioridadAuto);
            }
            if (autosVertical == autosHorizontal){
                establecerPrioridad("vertical", tiempoBase, prioridadAuto);
                establecerPrioridad("horizontal", tiempoBase, prioridadAuto);
            }
        }
    }

}