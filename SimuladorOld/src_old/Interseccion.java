package src_old;

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
/*Paquete para programación concurrente, este es todo un tema por aparte con procesos en paralelo
* Por ahora usaremos el método ScheduledExecutorService para ejecutar un reloj que
* Haga las verificaciones de logica con los semaforos
*/
import java.util.LinkedList;

//Para agrupar los semáforos en avenidas
public class Interseccion {
    private static int seconds; // Segundos entre cambio de semáforos
    private static boolean running;// Almacena el estado del reloj
    private static boolean turnVertical;
    /**
     * Lista (tomadas como avenida) que almacenan los semáforos
     */
    private static LinkedList<Semaforo> avVerticals, avHorizontals;

    /**
     * Tomando en cuenta el comentario dentro del código, genera una nueva
     * intersección y crea 4 semáforos.
     * Para iniciarlo, usar StartIntersection()
     * 
     * @param time int - Cantidad de tiempo para los semáforos
     */
    public Interseccion(int time, int distanceForArriving, int maxCarsArrive) {
        avVerticals.add(new Semaforo(time, true, distanceForArriving, maxCarsArrive));
        avVerticals.add(new Semaforo(time, true, distanceForArriving, maxCarsArrive));
        avHorizontals.add(new Semaforo(time, false, distanceForArriving, maxCarsArrive));
        avHorizontals.add(new Semaforo(time, false, distanceForArriving, maxCarsArrive));
        turnVertical = true;
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
                seconds++;
                return;
            }
            // Realizar la comprobación de las tareas de los semáforos y sus respectivo
            // intercambio
            int finished = 0; // Cantidad de semáforos que dejaron de pasar carros y terminaron su trabajo
            for (Semaforo semaforo : avHorizontals) { // En los 2 semáforos horizontales
                if (semaforo.getPassCars()) {// Si el semáforo está en verde
                    semaforo.PassACar(); // Pasar un carro del semáforo a la intersección
                    semaforo.doArrivingManagement(); // Hacer la comprobación de la intersección
                    if (semaforo.getTime() - 3 == seconds) {
                        // Poner semáforo en amarillo
                    }
                    if (semaforo.getTime() == seconds) {
                        semaforo.switchPassCars();
                    }
                } else if (semaforo.doArrivingManagement()) {// En caso de que el semaforo este en rojo, un avance en la
                                                             // intersección
                    finished++; // Si ya no hay carros en la intersección, dar por finalizada la tarea en este
                                // semáforo
                    semaforo.addTime(seconds);

                }
            }
            for (Semaforo semaforo : avVerticals) {// En los 2 semáforos verticales
                if (semaforo.getPassCars()) {// Si el semaforo está en verde
                    semaforo.PassACar();// Pasar un carro del semáforo a la intersección
                    semaforo.doArrivingManagement();
                    if (semaforo.getTime() - 3 == (seconds)) {
                        // Poner semáforo en amarillo
                    }
                } else if (semaforo.doArrivingManagement()) {// En caso de que el semáforo esté en rojo, un avance en la
                                                             // intersección
                    finished++;// Si no hay carros en la intersección, dar por finalizada la tarea en este
                               // semáforo
                    semaforo.addTime(seconds);
                }
            }

            if (finished == 2) { // En caso de que los semáforos de una avenida hayan terminado su trabajo
                finished = 0;// Reiniciar el contador y intercambiar el bool de los semáforos
            } else if (finished > 2) {
                // En caso de que el turno era del semáforo vertical, iniciar el semaforo
                // horizontal y viceversa
                if (turnVertical) {
                    for (Semaforo sm : avHorizontals) {
                        sm.switchPassCars();
                    }
                } else {
                    for (Semaforo sm : avVerticals) {
                        sm.switchPassCars();
                    }
                }
            } else if (finished > 2) {
                // Soltar una excepción, hay algún otro semáforo mal configurado
            }
            // Luego calcular que avenida requiere más prioridad en base a la cantidad de
            // carros en sus semáforos
            int avVerticalsCars = 0;
            for (Semaforo semaforo : avVerticals) {
                avVerticalsCars += semaforo.numberCarsInQueue();
            }
            int avHorizontalsCars = 0;
            for (Semaforo semaforo : avHorizontals) {
                avHorizontalsCars += semaforo.numberCarsInQueue();
            }
            if (avVerticalsCars>avHorizontalsCars){
                
            }
            // Luego calcular el promedio en base a este prioridad y actualizar el de los
            // semáforos

        }, 0, 1, TimeUnit.SECONDS);
    }

}
