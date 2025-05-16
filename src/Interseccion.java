
/*
 * Basado en una intersección entre 2 avenidas, esta intersección debe tener 4 semaforos;
 * 2 por avenida
 *          |  Spawn  |         |
*           |  Av1    |    ^    |
 *          |  Sm4    |    |    |   
 * _________| | | | | | | | | | |_________
 *  Av2  ___ *                 * ___ Sm3
 * Spawn ___     *         *     ___   ___Despawn     
 * _________          *          _________ 
 * __ Sm1___          *          ___   ___
 *-----> ___     *         *     ___ ----->
 * _________ *                 * _________
 *          | | | | | | | | | | |   
 *          |         | Av3^ Sm2|
 *          | Despawn | Sp | wn |   
 * La interseccion elimina los autos que hayan pasado y genera aleatoriamente
 * los que van a pasar, cada semaforo tendrá un número de carros a generar al azar que
 * cambiará cada tanto.
 * Las cosas extra como la prioridad de vehiculos se va a basar en cuantos vehículos
 * hay en espera, usando un contador qué se le pedira al usuario para los grupos 
 * de ambos semafóros que cambiará dependiendo de la prioridad a menos tiempo para una
 * avenida y más para la otra. (Esto cubre el promedio, tiempos de espera y priorización).
 * 
 */
import java.util.concurrent.*;
import java.util.LinkedList;

/*Paquete para programación concurrente, este es todo un tema por aparte con procesos en paralelo
* Por ahora usaremos el método ScheduledExecutorService para ejecutar un reloj que
* Haga las verificaciones de logica con los semaforos
*/
public class Interseccion {
    private static int waitAvVertical, waitAvHorizontal; // Tiempo de espera de cada semáforo
    private static int seconds; // Segundos entre cambio de semáforos
    // Semáforos dentro de la intersección sm(Dirección semáforo)Av(Tipo avenida)
    private static Semaforo smDownAvVertical;
    private static Semaforo smUpAvVertical;
    private static Semaforo smLeftAvHorizontal;
    private static Semaforo smRightAvHorizontal;
    private static boolean running;// Almacena el estado del reloj
    private static LinkedList<Semaforo> avVerticals, avHorizontals;

    /**
     * Tomando en cuenta el comentario dentro del código, genera una nueva
     * intersección y crea 4 semáforos.
     * Para iniciarlo, usar StartIntersection()
     * 
     * @param time int - Cantidad de tiempo para los semáforos
     */
    public Interseccion(int time, int distanceForArriving, int maxCarsArrive) {
        avVerticals.add(smDownAvVertical = new Semaforo(time, true, distanceForArriving, maxCarsArrive));
        avVerticals.add(smUpAvVertical = new Semaforo(time, true, distanceForArriving, maxCarsArrive));
        avHorizontals.add(smLeftAvHorizontal = new Semaforo(time, false, distanceForArriving, maxCarsArrive));
        avHorizontals.add(smRightAvHorizontal = new Semaforo(time, false, distanceForArriving, maxCarsArrive));
        waitAvVertical = time;
        waitAvHorizontal = time;
        seconds = 0;
        
    }

    /**
     * Cambia el estado de la ejecución del sistema
     * 
     * @param running Boolean
     */
    public static void setRunning(boolean running) {
        Interseccion.running = running;
    }

    /**
     * Método en construcción - Eliminar este mensaje cuando se termine-
     * Inicia un reloj que hace un conteo de los semáforos de una avenida y
     * dependiendo del tiempo
     * establecido de espera, cierra los semaforos y espera a que su método de
     * salida de carros
     * termine para empezar con este mismo proceso con los semáforos de la otra
     * avenida, entre este
     * cambio entre semáforos, calcula el promedio por avenida y realiza los cambios
     * (prioriza)
     * a los semaforos dependiendo de la cantidad de carros que hay en estos.
     * 
     * @param distanceForArrive  int - Distancia a la que los carros pasan la
     *                           intersección (ArrivingList en los semáforos)
     * @param maxCarsPerArriving int - Máxima cantidad de carros en la intersección
     *                           (Arriving list en los semáforos)
     */
    public void StartIntersection(int distanceForArrive, int maxCarsPerArriving) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            if (!running) {
                // Para la ejecución del reloj
                seconds ++;
                return;
            }
            int finished = 0;
            for (Semaforo semaforo : avHorizontals) {
                if (semaforo.getPassCars()){
                    semaforo.PassACar();
                    semaforo.doArrivingManagement();
                    if (semaforo.getTime()-3==(seconds)){
                        //Poner semáforo en amarillo
                    }
                }else if(semaforo.doArrivingManagement() && !smDownAvVertical.getPassCars()){
                    finished++;
                }
            }
            for (Semaforo semaforo : avVerticals) {
                if (semaforo.getPassCars()){
                    semaforo.PassACar();
                    semaforo.doArrivingManagement();
                }else if(semaforo.doArrivingManagement()){
                    finished++;
                }
            }
            if (finished ==2){
                finished =0;
                for (Semaforo sm: avHorizontals){
                    sm.switchPassCars();
                }
                for (Semaforo sm: avVerticals){
                    sm.switchPassCars();
                }
            }

            // Primero empezar con la comparación de los semáforos y preferiblemente saltar
            // tareas inecesarias

            // Luego calcular que avenida requiere más prioridad en base a la cantidad de
            // carros en sus semáforos

            // Luego calcular el promedio en base a este prioridad y actualizar el de los
            // semáforos

        }, 0, 1, TimeUnit.SECONDS);
    }

}
