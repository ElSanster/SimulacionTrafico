import java.util.LinkedList;
import java.util.Queue;

public class Semaforo {
    private int tiempoVerde;
    private Queue<Vehiculo> colaVehiculos;
    private boolean enVerde;
    private int distanciaInterseccion;
    private int maxVehiculosEnInterseccion;
    private LinkedList<Vehiculo> vehiculosEnInterseccion;

    public Semaforo(int tiempoVerde, boolean enVerde, int distanciaInterseccion, int maxVehiculosEnInterseccion) {
        this.tiempoVerde = tiempoVerde;
        this.enVerde = enVerde;
        this.colaVehiculos = new LinkedList<>();
        this.vehiculosEnInterseccion = new LinkedList<>();
        this.distanciaInterseccion = distanciaInterseccion;
        this.maxVehiculosEnInterseccion = maxVehiculosEnInterseccion;
    }

    public boolean estaEnVerde() {
        return enVerde;
    }

    public void cambiarEstado() {
        enVerde = !enVerde;
    }

    public int cantidadVehiculosEnCola() {
        return colaVehiculos.size();
    }

    public int getTiempoVerde() {
        return tiempoVerde;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        colaVehiculos.offer(vehiculo);
    }

    public Vehiculo liberarVehiculo() {
        if (enVerde && !colaVehiculos.isEmpty() && vehiculosEnInterseccion.size() < maxVehiculosEnInterseccion) {
            Vehiculo vehiculo = colaVehiculos.poll();
            vehiculosEnInterseccion.offer(vehiculo);
            return vehiculo;
        }
        return null;
    }

    public void gestionarVehiculosEnInterseccion() {
        vehiculosEnInterseccion.removeIf(vehiculo -> {
            vehiculo.speedUp();
            return vehiculo.getDistance() >= distanciaInterseccion;
        });
    }

    public LinkedList<Vehiculo> getVehiculosEnInterseccion() {
        return vehiculosEnInterseccion;
    }

    public Queue<Vehiculo> getColaVehiculos() {
        return colaVehiculos;
    }
}