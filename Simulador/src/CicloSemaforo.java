import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class CicloSemaforo {
    private List<Semaforo> semaforosVerticales;
    private List<Semaforo> semaforosHorizontales;
    private int tiempoVerticalVerde;
    private int tiempoHorizontalVerde;
    private String avenidaActiva = "vertical";
    private int contadorTiempo = 0;
    
    private Random random = new Random();

    public CicloSemaforo(List<Semaforo> semaforosVerticales, List<Semaforo> semaforosHorizontales) {
        this.semaforosVerticales = semaforosVerticales;
        this.semaforosHorizontales = semaforosHorizontales;
        this.tiempoVerticalVerde = semaforosVerticales.get(0).getTiempoVerde();
        this.tiempoHorizontalVerde = semaforosHorizontales.get(0).getTiempoVerde();
        activarVerde("vertical");
    }

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
    private void cambiarAvenidaActiva(String nuevaAvenida) {
        desactivarVerde(avenidaActiva);
        activarVerde(nuevaAvenida);
        avenidaActiva = nuevaAvenida;
        System.out.println(
                "Cambiando a verde la avenida " + (avenidaActiva.equals("vertical") ? "Vertical" : "Horizontal") + ".");
    }

    private void activarVerde(String avenida) {
        if (avenida.equalsIgnoreCase("vertical")) {
            semaforosVerticales.forEach(Semaforo::cambiarEstado);
        } else if (avenida.equalsIgnoreCase("horizontal")) {
            semaforosHorizontales.forEach(Semaforo::cambiarEstado);
        }
    }

    private void desactivarVerde(String avenida) {
        if (avenida.equalsIgnoreCase("vertical")) {
            semaforosVerticales.forEach(Semaforo::cambiarEstado);
        } else if (avenida.equalsIgnoreCase("horizontal")) {
            semaforosHorizontales.forEach(Semaforo::cambiarEstado);
        }
    }
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