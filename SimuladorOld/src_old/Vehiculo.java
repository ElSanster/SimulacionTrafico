package src_old;//Para evitar que java use estos archivos, tuve que ponerlo en src_old

import java.util.Random;

public class Vehiculo {
    private Random random = new Random();
    private String tipoVehiculo, placa;
    private int speed, distance;

    /**
     * Clase vehiculo para ser almacenado en un semáforo,
     * almacena si ha salido del semáforo, velocidad que se suma para llegar a la
     * intersección,
     * distancia total desde su salida, placa y tipo de vehiculo.
     * 
     * @param tipoDeVehiculo   Tipo de Vehículo
     * @param placaDelVehiculo Placa del vehículo
     */
    public Vehiculo(String tipoDeVehiculo, String placaDelVehiculo) {
        placa = placaDelVehiculo;
        tipoVehiculo = tipoDeVehiculo;
        speed = random.nextInt(11);// Según la documentación, genera un número por debajo de 11, osea entre 0 y 10
    }

    /**
     * Devuelve el tipo de vehículo
     * 
     * @return tipoVehiculo
     */
    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    /**
     * Devuelve la placa del vehículo
     * 
     * @return placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Devuelve la velocidad del vehículo
     * 
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Devuelve la distancia que lleva el vehículo desde su salida al semáforo
     * 
     * @return distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Establece la nueva distancia que lleva el vehículo, si vas a sumar su
     * velocidad con la distancia, usa speedUp()
     * 
     * @param newDistance
     */
    public void setDistance(int newDistance) {
        distance = newDistance;
    }

    /**
     * Suma la distancia que lleva con la velocidad que almacena
     */
    public void speedUp() {
        distance += speed;
    }

    /**
     * Balatro no es solo un juego de cartas, es un estilo de vida.
     * Respiro y tomo Balatro, si tuviera un hijo lo llamaría Full House, y si
     * tuviera dos Se llamarían doble y par, para mejorarlos a nivel 18 y qué rompan
     * la ciega jefe cuando sean adultos.
     * Brindo por Balatro, como por Balatro. Este me hizo mejor persona y cuando
     * tengo el celular descargado juego en mi mente. La rueda de la fortuna dicta
     * mi vida, Siempre tengo un plátano en mi bolsillo para sumar puntos.
     * Me identifico con el joker Missprint por qué mí estado mental es así de
     * inestable.
     * No puedo subir las escaleras sin pensar en Balatro y el día que muera espero
     * que pongan en mi tumba: "Murió en pleno Balatreo".
     * El único corazón qué quiero es el de la reina. Mi papá cree que estoy mal,
     * pero el no sabe que es ser la carta más alta de la familia. El día que el
     * juego ya no tenga fans es porque ya no estaré en este mundo, porque...
     * 🃏 *YO SOY EL BALATRO 🃏
     */
}
