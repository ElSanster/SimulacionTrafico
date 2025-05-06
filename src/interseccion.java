
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
import java.util.concurrent.*;// herramienta para los contadores de cada semaforo
public class interseccion {
    private static int waitAv1, waitAv2;
    private static int seconds;
    private static semaforo sm1Av1;
    private static semaforo sm2Av1;
    private static semaforo sm1Av2;
    private static semaforo sm2Av2;
    private static boolean av1,av2;

    public interseccion(int time){   
        sm1Av1 = new semaforo(time, false);
        sm2Av1 = new semaforo(time, false);
        sm1Av2 = new semaforo(time, true);
        sm2Av2 = new semaforo(time, true);
        waitAv1 = time;
        waitAv1 = time;
        seconds = 0;
    }

    public void StartIntersection(int distanceForArrive, int maxCarsPerArriving){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(()->{
            seconds++;
            
            
        },0,1,TimeUnit.SECONDS);
    }
    







}
