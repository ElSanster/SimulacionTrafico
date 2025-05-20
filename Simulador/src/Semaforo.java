import java.util.LinkedList;
import java.util.Queue;

public class Semaforo {
    /**
     * Duración de estado en verde (Prioridad)
     */
    private int tiempoVerde;
    /**
     * Cola qué almacena los vehículos a partir del semáforo
     */
    private Queue<Vehiculo> colaVehiculos;
    /**
     * Indica estado del semaforo
     */
    private boolean enVerde;
    /**
     * Distancia a la qué los vehículos salen de la simulación
     */
    private int distanciaInterseccion;
    /**
     * Máxima cantidad de vehículos en la intersección
     */
    private int maxVehiculosEnInterseccion;
    /**
     * Lista de vehículos en la intersección
     */
    private LinkedList<Vehiculo> vehiculosEnInterseccion;

    /**
     * Objeto qué almacena los autos y da la información sobre tiempos de espera,
     * autos en cola y intersección.
     * 
     * @param tiempoVerde                Duración de estado en verde (Prioridad)
     * @param enVerde                    Establecer el semáforo en verde
     * @param distanciaInterseccion      Distancia a la qué los vehículos salen de
     *                                   la intersección
     * @param maxVehiculosEnInterseccion Máxima cantidad de vehículos en la
     *                                   intersección
     */
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

    /**
     * Cambia el estado del semáforo
     */
    public void cambiarEstado() {
        enVerde = !enVerde;
    }

    public int cantidadVehiculosEnCola() {
        return colaVehiculos.size();
    }

    public int getTiempoVerde() {
        return tiempoVerde;
    }

    /**
     * Agrega un vehículo dentro de la cola del semáforo
     * 
     * @param vehiculo
     */
    public void agregarVehiculo(Vehiculo vehiculo) {
        colaVehiculos.offer(vehiculo);
    }

    /**
     * Saca un vehículo de la cola del semáforo
     * @return Vehículo sacado del semáforo
     */
    public Vehiculo liberarVehiculo() {
        if (enVerde && !colaVehiculos.isEmpty() && vehiculosEnInterseccion.size() < maxVehiculosEnInterseccion) {
            Vehiculo vehiculo = colaVehiculos.poll();
            vehiculosEnInterseccion.offer(vehiculo);
            return vehiculo;
        }
        return null;
    }
    /**
     * Suma la distancia de los carros, si alcanzaron la distancia de salida, los libera de esta simulación
     */
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