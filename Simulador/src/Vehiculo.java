public class Vehiculo {
    private String tipoVehiculo;
    private String placa;
    private int speed;
    private int distance;

    public Vehiculo(String tipoDeVehiculo, String placaDelVehiculo) {
        this.tipoVehiculo = tipoDeVehiculo;
        this.placa = placaDelVehiculo;
        this.speed = 1; // Velocidad constante para simplificar
        this.distance = 0;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDistance() {
        return distance;
    }

    public void speedUp() {
        distance += speed;
    }
}