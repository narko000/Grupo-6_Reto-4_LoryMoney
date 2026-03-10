package programa1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class programa1 extends JFrame {

    // Panel principal
    private JComboBox<String> comboBox;
    private JLabel lblValor;
    private JLabel lblUnidad;
    private JLabel lblDescripcion;

    public programa1() {
        setTitle("Centro de Datos");
        setSize(400, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(crearPantallaPanel());

        setVisible(true);
    }

// Pantalla principal
  private JPanel crearPantallaPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));

        JLabel lblSelecciona = new JLabel("Selecciona una métrica:");
        panel.add(lblSelecciona);

        String[] opciones = {"Consumo Energético", "Emisiones de CO2", "Disponibilidad"};
        comboBox = new JComboBox<>(opciones);
        comboBox.addActionListener(e -> actualizarDatos());
        panel.add(comboBox);

        lblValor = new JLabel("Valor: ");
        lblValor.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblValor);

        lblUnidad = new JLabel("Unidad: ");
        panel.add(lblUnidad);

        lblDescripcion = new JLabel("Descripción: ");
        panel.add(lblDescripcion);

        return panel;
    }

    private void actualizarDatos() {
        String seleccion = (String) comboBox.getSelectedItem();

        if (seleccion.equals("Consumo Energético")) {
            lblValor.setText("Valor: 2840");
            lblUnidad.setText("Unidad: kWh / hora");
            lblDescripcion.setText("Electricidad consumida por los servidores.");

        } else if (seleccion.equals("Emisiones de CO2")) {
            lblValor.setText("Valor: 4.28");
            lblUnidad.setText("Unidad: toneladas / día");
            lblDescripcion.setText("CO2 emitido por la operación del centro de datos.");

        } else if (seleccion.equals("Disponibilidad")) {
            lblValor.setText("Valor: 99.97");
            lblUnidad.setText("Unidad: %");
            lblDescripcion.setText("Porcentaje de tiempo que el sistema ha estado activo.");
        }
    }

    public static void main(String[] args) {
        new programa1();
    }
}
