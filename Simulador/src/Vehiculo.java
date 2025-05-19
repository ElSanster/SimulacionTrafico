import java.util.Random;
public class Vehiculo {
    private Random random = new Random();
    private String tipoVehiculo;
    private String placa;
    private int speed;
    private int distance;

    public Vehiculo() {
        int tipoRandom = random.nextInt(13);
        String newType;
        switch (tipoRandom) {
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
        placa = "" + (char) (random.nextInt(26) + 'a') + (char) (random.nextInt(26) + 'a')
                + (char) (random.nextInt(26) + 'a');
        int rndrumber = random.nextInt();
        if (rndrumber < 100) {
            if (rndrumber < 10) {
                placa = placa + " 00" + rndrumber;
            } else {
                placa = placa + " 0" + rndrumber;
            }
        } else {
            placa = placa + " " + rndrumber;
        }
        this.tipoVehiculo = newType;
        this.placa = placa;
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