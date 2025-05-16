
/* 
 * El semáforo tendrá una lista de tipo queue qué recibirá y mostrará los autos
 * en cola, liberará uno por uno dependiendo del tiempo que cada 
 * carro tarda en avanzar (número que almacena el carro) qué se descuenta del contador.
 * 
 * tendrá el bool passCars qué define si los carros pasan o no, el indicador del
 * semaforo pasará en amarillo cuando queden 3 segundos antes de cambiar a rojo y viceversa
 * 
*/
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Semaforo {
    private Random random = new Random();
    private int time;
    private double promTime;
    private Queue<Vehiculo> semaforoQueue;
    private boolean passCars;
    private int distanceForArrive;
    private int maxCarsArriving;

    /**
     * Devuelve el estado del semáforo
     * 
     * @return passCars - Dejar pasar los carros
     */
    public boolean getPassCars() {
        return passCars;
    }

    private LinkedList<Vehiculo> arrivingList;

    /**
     * Genera una nueva clase Semáforo que conforma varios datos:
     * Un Queue de los carros que están esperando a que el semaforo esté en verde
     * Un linked list de los carros que aun no han llegado al final de la
     * intersección
     * Un boolean passCars que almacena si el semaforo está en verde, caso contrario
     * rojo
     * Un int que almacena la distancia de llegada de los carros a la intersección
     * un int que almacena la maxima cantidad de carros en la intersección
     * y faltando 3 segundos en el reloj de la intersección es amarillo
     * Promtime almacena el promedio de salida de los carros
     * 
     * @param setTime             Tiempo que da para poder pasar los carros
     * @param PassCars            Establecer el semaforo en rojo o verde
     *                            (true/false)
     * @param distanceForArriving Distancia a la que los carros llegan al final
     * @param maxCarsArrive       Máxima cantidad de carros en la intersección
     */
    public Semaforo(int setTime, boolean PassCars, int distanceForArriving, int maxCarsArrive) {
        time = setTime;
        passCars = PassCars;
        semaforoQueue = new LinkedList<>();
        arrivingList = new LinkedList<>();
        distanceForArrive = distanceForArriving;
        maxCarsArriving = maxCarsArrive;
    }

    /**
     * Cambia a false cuando era true y viceversa.
     * 
     */
    public void switchPassCars() {
        passCars = !passCars;
    }

    /**
     * Devuelve la cantidad(Int) de cuantas clases Vehiculo hay en el queue de este
     * Semaforo
     * 
     * @return int carsQueue
     */
    public int numberCarsInQueue() {
        if (semaforoQueue.isEmpty()) {
            return 0;
        } else {
            int carsQueue = 0;
            for (Vehiculo p : semaforoQueue) {
                carsQueue++;
            }
            return carsQueue;
        }
    }

    /**
     * Devuelve el tiempo que tarda en pasar los carros el semaforo
     * 
     * @return
     */
    public int getTime() {
        return time;
    }

    /**
     * Devuelve si hay carros que tienen establecido que salieron del semaforo
     * pero no han sido transferidos al linked list para pasar al otro lado de la
     * intersección
     * 
     * @return theresCarsOut
     */
    public boolean theresCarsOut() {
        for (Vehiculo p : semaforoQueue) {
            if (p.getOut()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Genera un vehículo dentro de este semáforo, los datos de este vehículo son al
     * azar
     */
    public void addRandomVehicle() {
        int randomType = random.nextInt(13);
        String newType;
        switch (randomType) {
            case 1:
                newType = "Urbano";
                break;
            case 2:
                newType = "Sedan";
                break;
            case 3:
                newType = "HatchBack";
                break;
            case 4:
                newType = "Descapotable";
                break;
            case 5:
                newType = "Coupe";
                break;
            case 6:
                newType = "Deportivo";
                break;
            case 7:
                newType = "Monovolumen";
                break;
            case 8:
                newType = "Todoterreno";
                break;
            case 9:
                newType = "SUV";
                break;
            case 10:
                newType = "Roadster";
                break;
            case 11:
                newType = "Pickup";
                break;
            case 12:
                newType = "Furgoneta";
                break;
            default:
                newType = "urbano";
                break;
        }
        String placa;
        placa = "" + (char) (random.nextInt(26) + 'a') + (char) (random.nextInt(26) + 'a')
                + (char) (random.nextInt(26) + 'a');
        int rndrumber = random.nextInt();
        if (rndrumber < 100) {
            if (rndrumber < 10) {
                placa = placa + " 00" + rndrumber;
            } else {
                placa = placa + " 0" + rndrumber;
            }
        } else {
            placa = placa + " " + rndrumber;
        }

        semaforoQueue.add(new Vehiculo(newType, placa));
    }

    /**
     * Añade un vehículo al semáforo, puede que se desee añadir un vehículo con
     * datos del usuario.
     * De preferencia usar addRandomVehicle() que realiza esta acción con datos
     * aleatorios
     * 
     * @param placa Placa del vehiculo
     * @param tipo  Tipo de vehículo
     */
    public void addManualVehicle(String placa, String tipo) {
        semaforoQueue.add(new Vehiculo(tipo, placa));
    }

    /**
     * Realiza el paso de semáforo a intersección
     * En caso de que la lista esté llena o el queue este vacío, significa que no hizo cambios y devolverá falso
     * @return Boolean - Pasó un carro
     */
    public boolean PassACar() {
        if (semaforoQueue.isEmpty()) {
            return false;
        }
        if (arrivingList.size() < maxCarsArriving && passCars) {
            while (arrivingList.size() <= maxCarsArriving) {
                arrivingList.add(semaforoQueue.poll());
            }
            return true;
        } else
            return false;
    }

    /**
     * Hace las verificaciones para la intersección<br>
     * Verifica si los carros pasando la intersección (Carros que están en
     * arrivingList) no llegan al límite establecido (MaxcarsArriving), pasando más
     * carros del semáforo queue para llegar al máximo<br>
     * Luego verifica si la lista de carros pasando la intersección está vacía,
     * devolviendo true <br>
     * Sí no esta vacía verifica si la distancia de los vehículos es igual al del
     * final de la intersección para quitarlos y si no
     * suma su distancia con la velocidad del vehículo
     * 
     * @param distanceForArrive Distancia a la que se considera que los carros pasan
     *                          la intersección
     * @param maxCarsArriving   Máxima cantidad de carros en la intersección
     * @return Ha terminado el manejo de autos en la intersección
     */
    public boolean doArrivingManagement() {
        if (arrivingList.isEmpty()) {
            return true;
        } else {
            for (Vehiculo vehiculo : arrivingList) {
                if (vehiculo.getDistance() >= distanceForArrive) {
                    vehiculo.setOut(true);
                } else {
                    vehiculo.speedUp();
                }
                if (vehiculo.getOut()) {
                    arrivingList.poll();
                }
            }
            return false;
        }
    }

    /**
     * Saca el carro de la intersección (que posteriormente debía estar en su
     * semáforo),
     * con esto saldría de la simulación y el garbage collector se desharía de el
     */
    public void arriveVehicle() {
        arrivingList.add(semaforoQueue.poll());
    }

}
