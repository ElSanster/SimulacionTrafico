
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
public class Interseccion {
    private static int waitAv1, waitAv2;
    private static int seconds;
    private static Semaforo sm1Av1;
    private static Semaforo sm2Av1;
    private static Semaforo sm1Av2;
    private static Semaforo sm2Av2;
    private static boolean av1,av2;
    /**
     * Tomando en cuenta el comentario dentro de esta clase, genera una nueva intersección y crea 4 semáforos
     * Maneja los semáforos dependiendo de que avenida es la que se abre y llama los respectivos métodos para 
     * mover y quitar carros de las listas de sus semáforos, también calculara el promedio y dará prioridad
     * a aquellos semáforos que tienen más carros
     * @param time Cantidad de tiempo para los semáforos
     */
    public Interseccion(int time){   
        sm1Av1 = new Semaforo(time, false);
        sm2Av1 = new Semaforo(time, false);
        sm1Av2 = new Semaforo(time, true);
        sm2Av2 = new Semaforo(time, true);
        waitAv1 = time;
        waitAv1 = time;
        seconds = 0;
    }
    /**
     * Método en construccion - Eliminar este mensaje cuando se termine-
     * Inicia un reloj que hace un conteo de los semáforos de una avenida y dependiendo del tiempo
     * establecido de espera, cierra los semaforos y espera a que su método de salida de carros
     * termine para empezar con este mismo proceso con los semáforos de la otra avenida, entre este
     * cambio entre semáforos, calcula el promedio por avenida y realiza los cambios (prioriza)
     * a los semaforos dependiendo de la cantidad de carros que hay en estos.
     * @param distanceForArrive Distancia a la que los carros pasan la intersección (ArrivingList en los semáforos)
     * @param maxCarsPerArriving Máxima cantidad de carros en la intersección (Arriving list en los semáforos)
     */
    public void StartIntersection(int distanceForArrive, int maxCarsPerArriving){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(()->{
            seconds++;
            //Primero empezar con la comparación de los semáforos y preferiblemente saltar tareas insecesarias

            //Luego calcular que avenida requiere más prioridad en base a la cantidad de carros en sus semáforos

            //Luego calcular el promedio en base a este prioridad y actualizar el de los semáforos
            
        },0,1,TimeUnit.SECONDS);
    }
    







}
