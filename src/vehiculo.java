
/*
 * Clase que contiene los datos del vehiculo y su velocidad para pasar,
 * estos se mantendrán en una fila (queue) cuando el semaforo esté en rojo y 
 * serán descartados de cada semafóro en verde en un temporizador que 
 * depende de su velocidad, también los semaforos siguientes darán luz 
 * verde una vez haya pasado el último carro que haya pasado el
 * semaforo antes de que se volviera rojo
 *  
 */

import java.util.Random;
public class vehiculo {
    private Random random = new Random();
    private String tipoVehiculo, placa;
    private boolean out;
    private int speed, distance;

    public vehiculo (String tipoDeVehiculo, String placaDelVehiculo){
        placa=placaDelVehiculo;
        tipoVehiculo= tipoDeVehiculo;
        speed = random.nextInt(11);//Un número entre 0 y 10, no puede dar 11
        out = false;
    }

    public String getTipoVehiculo(){
        return tipoVehiculo;
    }

    public String getPlaca(){
        return placa;
    }

    public int getSpeed () {
        return speed;
    }

    public int getDistance(){
        return distance;
    }

    public boolean getOut() {
        return out;
    }

    public void setOut(boolean newOut){
        out = newOut;
    }

    public void setDistance (int newDistance){
        distance = newDistance;
    }
}
