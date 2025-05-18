package src_old;


import java.util.Random;
public class Vehiculo {
    private Random random = new Random();
    private String tipoVehiculo, placa;
    private int speed, distance;
    /**
     * Clase vehiculo para ser almacenado en un semáforo,
     * almacena si ha salido del semáforo, velocidad que se suma para llegar a la intersección,
     * distancia total desde su salida, placa y tipo de vehiculo.
     * @param tipoDeVehiculo Tipo de Vehículo
     * @param placaDelVehiculo Placa del vehículo
     */
    public Vehiculo (String tipoDeVehiculo, String placaDelVehiculo){
        placa=placaDelVehiculo;
        tipoVehiculo= tipoDeVehiculo;
        speed = random.nextInt(11);//Según la documentación, genera un número por debajo de 11, osea entre 0 y 10
    }
/**
 * Devuelve el tipo de vehículo
 * @return tipoVehiculo
 */
    public String getTipoVehiculo(){
        return tipoVehiculo;
    }
/**
 * Devuelve la placa del vehículo
 * @return placa
 */
    public String getPlaca(){
        return placa;
    }
/**
 * Devuelve la velocidad del vehículo
 * @return speed
 */
    public int getSpeed () {
        return speed;
    }
/**
 * Devuelve la distancia que lleva el vehículo desde su salida al semáforo
 * @return distance
 */
    public int getDistance(){
        return distance;
    }
/**
 * Establece la nueva distancia que lleva el vehículo, si vas a sumar su velocidad con la distancia, usa speedUp()
 * @param newDistance
 */
    public void setDistance (int newDistance){
        distance = newDistance;
    }
    /**
     * Suma la distancia que lleva con la velocidad que almacena
     */
    public void speedUp(){
        distance += speed;
    }
}


