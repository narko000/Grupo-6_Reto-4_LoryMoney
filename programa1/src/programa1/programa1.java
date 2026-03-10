package programa1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class programa1 extends JFrame {

    // Pantallas
    private JPanel pantallaLogin;
    private JPanel pantallaPanel;

    // Login
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JLabel lblError;

    // Panel principal
    private JComboBox<String> comboBox;
    private JLabel lblValor;
    private JLabel lblUnidad;
    private JLabel lblDescripcion;
    private JLabel lblBienvenida;

    public programa1() {
        setTitle("Centro de Datos");
        setSize(400, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        pantallaLogin = crearPantallaLogin();
        pantallaPanel = crearPantallaPanel();

        add(pantallaLogin, "login");
        add(pantallaPanel, "panel");

        mostrarPantalla("login");
        setVisible(true);
    }

    private void mostrarPantalla(String nombre) {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), nombre);
    }

    // ── Pantalla de login ────────────────────────────────────────────
    private JPanel crearPantallaLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Iniciar sesión", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;

        JLabel lblU = new JLabel("Usuario:");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblU, gbc);

        txtUsuario = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtUsuario, gbc);

        JLabel lblP = new JLabel("Contraseña:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblP, gbc);

        txtContrasena = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtContrasena, gbc);

        lblError = new JLabel("", SwingConstants.CENTER);
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Arial", Font.PLAIN, 11));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(lblError, gbc);

        JButton btnLogin = new JButton("Entrar");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> comprobarLogin());

        // También al pulsar Enter en contraseña
        txtContrasena.addActionListener(e -> comprobarLogin());

        return panel;
    }

    private void comprobarLogin() {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());

        if (usuario.equals("admin") && contrasena.equals("1234")) {
            lblBienvenida.setText("Bienvenido, " + usuario);
            lblError.setText("");
            mostrarPantalla("panel");
        } else {
            lblError.setText("Usuario o contraseña incorrectos");
        }
    }

    // ── Pantalla principal ───────────────────────────────────────────
    private JPanel crearPantallaPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));

        lblBienvenida = new JLabel("Bienvenido");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(lblBienvenida);

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

        JButton btnCerrar = new JButton("Cerrar sesión");
        btnCerrar.addActionListener(e -> {
            txtUsuario.setText("");
            txtContrasena.setText("");
            mostrarPantalla("login");
        });
        panel.add(btnCerrar);

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