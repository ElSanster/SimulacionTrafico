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
    private LinkedList<Vehiculo> arrivingList;
    //Pedimos el pass cars para evitar que semaforos de distintas avenidas esten en true
    public Semaforo(int setTime, boolean PassCars){
        time = setTime;
        passCars= PassCars;
        semaforoQueue = new LinkedList<>();
        arrivingList = new LinkedList<>();
    }

    public void setPassCars(boolean newPassCars){
        passCars = newPassCars;
    }
    /**
     * @param numberCarsInQueue Devuelve el número de carros que están dentro del queue
     * del semaforo
     * @return Número de carros dentro del queue del semaforo
     */
    public int numberCarsInQueue() {
        if (semaforoQueue.isEmpty())
        {
            return 0;
        }else{
            int carsQueue = 0;
            for (Vehiculo p : semaforoQueue) {
            carsQueue++;
            }
            return carsQueue;
        }
    }

    public int getTime(){
        return time;
    }

    public boolean theresCarsOut (){
        for (Vehiculo p : semaforoQueue) {
            if (p.getOut()){
                return true;
            }
        }
        return false;
    }

    public void addRandomVehicle(){
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
        placa = ""+(char)(random.nextInt(26)+'a')+(char)(random.nextInt(26)+'a')+(char)(random.nextInt(26)+'a');
        int rndrumber = random.nextInt();
        if (rndrumber < 100){
            if (rndrumber < 10){
                placa = placa + " 00"+ rndrumber;
            }else{
                placa = placa + " 0"+ rndrumber;
            }
        }else {
            placa = placa + " "+rndrumber;
        }

        semaforoQueue.add(new Vehiculo(newType,placa));
    }

    public void addManualVehicle(String placa, String tipo){
        semaforoQueue.add(new Vehiculo(tipo,placa));
    }
/*
 * Devuelve verdadero en caso de que la lista de carros que pasaron el semaforo está vacía
 * Primero, verifica si el queue de carros que van saliendo del semaforo está en su límite
 * Luego Si la lista está vacía devuelve verdadero y sale del método
 * Sí no está vacía: 
 *      -Devuelve falso
 *      - Verifica si todos los vehiculos han llegado al otro lado de la avenida
 *        si no es así les suma su distancia
 *        si es así cambia su boolean a true indicando que salieron de la interseccion
 *      -Verifica si el último vehiculo del queue ha llegado a su destino
 *        si es así, lo saca del queue y el garbage collector de java lo eliminará de la memoria
 */
    public boolean doArrivingManagement(int distanceForArrive, int maxCarsArriving){
        if (arrivingList.size() < maxCarsArriving && passCars){
            for (int i = arrivingList.size(); i<maxCarsArriving; i++){
                arrivingList.add(semaforoQueue.poll());
            }
        }
        if (arrivingList.isEmpty()){
            return true;
        }else {
            for (Vehiculo vehiculo : arrivingList) {
                if (vehiculo.getDistance()>= distanceForArrive){
                    vehiculo.setOut(true);
                }else{
                    vehiculo.setDistance(vehiculo.getDistance()+vehiculo.getSpeed());
                }
                if (vehiculo.getOut()){
                    arrivingList.poll();
                }
            }
            return false;
        }
    }

    public void arriveVehicle(){
        arrivingList.add(semaforoQueue.poll());
    }


}
