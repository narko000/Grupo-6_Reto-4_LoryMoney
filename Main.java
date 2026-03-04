package puntuacion1;

public class Main {
    public static void main(String[] args) {

        Servidor srv1 = new Servidor(1, "SRV-01", 8, 30, 75, 2);
        Servidor srv2 = new Servidor(2, "SRV-02", 13, 38, 40, 4);

        System.out.println(srv1);
        System.out.println(srv1.getNombre());

        srv1.setNombre("SRV-01-Actualizado");
        System.out.println(srv1.getNombre());

        srv1.setConsumoKW(-5);  // no hace nada, valor invalido
        System.out.println(srv1.getConsumoKW());  // sigue siendo 8.5

        System.out.println("---");

        MotorPuntuacion.imprimirInforme(srv1);
        MotorPuntuacion.imprimirInforme(srv2);
    }
}
