/* 
 * El semáforo será una lista de tipo queue qué recibirá y mostrará los autos
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

public class semaforo {
    private Random random = new Random();
    private int time;
    private double promTime;
    private Queue<vehiculo> semaforoQueue;
    private boolean passCars;
    //Pedimos el pass cars para evitar que semaforos de distintas avenidas esten en true
    public semaforo(int setTime, boolean PassCars){
        time = setTime;
        passCars= PassCars;
        semaforoQueue = new LinkedList<>();
    }

    public void setPassCars(boolean newPassCars){
        passCars = newPassCars;
    }

    public int numberCarsInQueue() {
        if (semaforoQueue.isEmpty())
        {
            return 0;
        }else{
            int carsQueue = 0;
            for (vehiculo p : semaforoQueue) {
            carsQueue++;
            }
            return carsQueue;
        }
    }

    public int getTime(){
        return time;
    }

    public boolean theresCarsOut (){
        boolean carsOut = false;
        for (vehiculo p : semaforoQueue) {
            if (p.getOut()){
                carsOut = true;
                break;
            }
        }
        return carsOut;
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

        semaforoQueue.add(new vehiculo(newType,placa));
    }

    public void addManualVehicle(String placa, String tipo){
        semaforoQueue.add(new vehiculo(tipo,placa));
    }
/*
 * Devuelve false en caso de que la lista de carros que pasaron el semaforo está vacía
 */
    public boolean doArrivingManagement(int distanceForArrive, int maxCarsArriving){
        Queue<vehiculo> arrivingQueue = new LinkedList<>();
        if (arrivingQueue.size()<maxCarsArriving|| passCars){
            for (int i = arrivingQueue.size(); i<maxCarsArriving; i++){
                arrivingQueue.add(semaforoQueue.poll());
            }
        }
        if (arrivingQueue.isEmpty()){
            return true;
        }else {
            for (vehiculo vehiculo : arrivingQueue) {
                if (!vehiculo.getOut()){
                    vehiculo.setOut(true);
                }else{
                    vehiculo.setDistance(vehiculo.getDistance()+vehiculo.getSpeed());
                }
            }
            if (arrivingQueue.peek().getDistance()==distanceForArrive){
                arrivingQueue.poll();
            }
            return false;
        }
    }

    public void arriveVehicle(){

    }
}
