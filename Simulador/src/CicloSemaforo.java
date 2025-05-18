import java.util.List;

public class CicloSemaforo {
    private List<Semaforo> semaforosVerticales;
    private List<Semaforo> semaforosHorizontales;
    private int tiempoVerticalVerde;
    private int tiempoHorizontalVerde;
    private String avenidaActiva = "vertical";
    private int contadorTiempo = 0;

    public CicloSemaforo(List<Semaforo> semaforosVerticales, List<Semaforo> semaforosHorizontales) {
        this.semaforosVerticales = semaforosVerticales;
        this.semaforosHorizontales = semaforosHorizontales;
        this.tiempoVerticalVerde = semaforosVerticales.get(0).getTiempoVerde();
        this.tiempoHorizontalVerde = semaforosHorizontales.get(0).getTiempoVerde();
        activarVerde("vertical");
    }

    public void establecerPrioridad(String avenida, int tiempoPrioridad) {
        if (avenida.equalsIgnoreCase("vertical")) {
            this.tiempoVerticalVerde = tiempoPrioridad;
            System.out.println("Prioridad de la avenida Vertical establecida en " + tiempoPrioridad + " segundos.");
        } else if (avenida.equalsIgnoreCase("horizontal")) {
            this.tiempoHorizontalVerde = tiempoPrioridad;
            System.out.println("Prioridad de la avenida Horizontal establecida en " + tiempoPrioridad + " segundos.");
        } else {
            System.out.println("Opci�n de avenida inv�lida. Use 'vertical' o 'horizontal'.");
        }
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
    }

    private void cambiarAvenidaActiva(String nuevaAvenida) {
        desactivarVerde(avenidaActiva);
        activarVerde(nuevaAvenida);
        avenidaActiva = nuevaAvenida;
        System.out.println("Cambiando a verde la avenida " + (avenidaActiva.equals("vertical") ? "Vertical" : "Horizontal") + ".");
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
}