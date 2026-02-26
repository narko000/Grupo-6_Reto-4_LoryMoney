package puntuacion1;

public class MotorPuntuacion {

    public static int calcularPuntuacion(Servidor s) {
        int scoreConsumo     = Math.max(0, 100 - (s.getConsumoKW() * 7));
        int scoreTemperatura = Math.max(0, 100 - (s.getTemperaturaC() * 2));
        int scoreEficiencia  = s.getEficiencia();
        int scoreCO2         = Math.max(0, 100 - (s.getEmisionesCO2() * 15));

        return (scoreConsumo + scoreTemperatura + scoreEficiencia + scoreCO2) / 4;
    }

    public static String etiqueta(int puntuacion) {
        if (puntuacion >= 80) return "EXCELENTE";
        if (puntuacion >= 60) return "ACEPTABLE";
        if (puntuacion >= 40) return "MEJORABLE";
        return "CRITICO";
    }

    public static void imprimirInforme(Servidor s) {
        int puntuacion = calcularPuntuacion(s);
        System.out.println(s);
        System.out.println("Puntuacion: " + puntuacion + " / 100 -> " + etiqueta(puntuacion));
        System.out.println("---");
    }
}
