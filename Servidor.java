package puntuacion1;

public class Servidor {

    // Encapsulacion
    private int id;
    private String nombre;
    private int consumoKW;
    private int temperaturaC;
    private int eficiencia;
    private int emisionesCO2;

    // Constructor
    public Servidor(int id, String nombre, int consumoKW, int temperaturaC, int eficiencia, int emisionesCO2) {
        System.out.println("Servidor constructor");
        this.id = id;
        this.nombre = nombre;
        this.consumoKW = consumoKW;
        this.temperaturaC = temperaturaC;
        this.eficiencia = eficiencia;
        this.emisionesCO2 = emisionesCO2;
    }

    public int getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getConsumoKW() {
        return this.consumoKW;
    }

    public void setConsumoKW(int consumoKW) {
        if (consumoKW > 0) {
            this.consumoKW = consumoKW;
        }
    }

    public int getTemperaturaC() {
        return this.temperaturaC;
    }

    public void setTemperaturaC(int temperaturaC) {
        if (temperaturaC > 0) {
            this.temperaturaC = temperaturaC;
        }
    }

    public int getEficiencia() {
        return this.eficiencia;
    }

    public int getEmisionesCO2() {
        return this.emisionesCO2;
    }

    public String toString() {
        return "Servidor #" + this.id + " - " + this.nombre +
               " | Consumo: " + this.consumoKW + " kW" +
               " | Temp: " + this.temperaturaC + "°C" +
               " | Eficiencia: " + this.eficiencia + "%" +
               " | CO2: " + this.emisionesCO2 + " kg/h";
    }
}
