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
    private JComboBox<String> comboMetrica;
    private JComboBox<String> comboServidor;
    private JLabel lblBienvenida;
    private JLabel lblValor;
    private JLabel lblUnidad;
    private JLabel lblDescripcion;
    private JLabel lblAlerta;

    // Datos [servidor][metrica] -> {valor, unidad, descripcion}
    private static final String[] SERVIDORES = {
        "SRV-01 - Produccion", "SRV-02 - Staging", "SRV-03 - Backup", "SRV-04 - Analytics"
    };

    private static final String[][][] DATOS = {
        // SRV-01
        {
            {"2840",  "kWh/h",   "Electricidad consumida en produccion."},
            {"4.28",  "ton/dia", "CO2 emitido en produccion."},
            {"99.97", "%",       "Tiempo activo ultimo trimestre."}
        },
        // SRV-02
        {
            {"1120",  "kWh/h",   "Electricidad consumida en staging."},
            {"1.68",  "ton/dia", "CO2 emitido en staging."},
            {"98.75", "%",       "Tiempo activo en staging."}
        },
        // SRV-03
        {
            {"640",   "kWh/h",   "Electricidad del sistema de copias."},
            {"0.96",  "ton/dia", "Emisiones del servidor de respaldo."},
            {"99.99", "%",       "Disponibilidad del sistema de backup."}
        },
        // SRV-04
        {
            {"3450",  "kWh/h",   "Electricidad para analitica de datos."},
            {"5.18",  "ton/dia", "CO2 del servidor de Big Data."},
            {"97.30", "%",       "Disponibilidad del entorno analitico."}
        }
    };

    /*
     * Umbrales basados en estandares de la industria:
     *
     * Consumo Energetico:
     *   - Servidores estandar recomendados: < 1500 kWh/h
     *   - Advertencia: 1500 - 2500 kWh/h
     *   - Critico: > 2500 kWh/h
     *
     * Emisiones CO2:
     *   - Aceptable: < 2.0 ton/dia
     *   - Advertencia: 2.0 - 3.5 ton/dia
     *   - Critico: > 3.5 ton/dia
     *
     * Disponibilidad:
     *   - Optimo: >= 99.9% (Tier III estandar)
     *   - Advertencia: 99.0% - 99.9%
     *   - Critico: < 99.0%
     */
    private static final double[][] UMBRAL_WARN = {
        {1500.0, 2.0,  99.9},   // [metrica][warn]   energia, co2, disponibilidad
    };
    private static final double[][] UMBRAL_CRIT = {
        {2500.0, 3.5,  99.0},   // [metrica][critico]
    };

    public programa1() {
        setTitle("Centro de Datos");
        setSize(450, 400);
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

        JLabel titulo = new JLabel("Iniciar sesion", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titulo, gbc(0, 0, 2));

        panel.add(new JLabel("Usuario:"),    gbc(0, 1, 1));
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario,                gbc(1, 1, 1));

        panel.add(new JLabel("Contrasena:"), gbc(0, 2, 1));
        txtContrasena = new JPasswordField(15);
        panel.add(txtContrasena,             gbc(1, 2, 1));

        lblError = new JLabel("", SwingConstants.CENTER);
        lblError.setForeground(Color.RED);
        panel.add(lblError, gbc(0, 3, 2));

        JButton btnLogin = new JButton("Entrar");
        panel.add(btnLogin, gbc(0, 4, 2));

        btnLogin.addActionListener(e -> comprobarLogin());
        txtContrasena.addActionListener(e -> comprobarLogin());

        return panel;
    }

    private void comprobarLogin() {
        String usuario    = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());

        if (usuario.equals("admin") && contrasena.equals("1234")) {
            lblBienvenida.setText("Bienvenido, " + usuario);
            lblError.setText("");
            actualizarDatos();
            mostrarPantalla("panel");
        } else {
            lblError.setText("Usuario o contrasena incorrectos");
        }
    }

    // ── Pantalla principal ───────────────────────────────────────────
    private JPanel crearPantallaPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        lblBienvenida = new JLabel("Bienvenido", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(lblBienvenida, gbc(0, 0, 2));

        panel.add(new JLabel("Servidor:"), gbc(0, 1, 1));
        comboServidor = new JComboBox<>(SERVIDORES);
        comboServidor.addActionListener(e -> actualizarDatos());
        panel.add(comboServidor, gbc(1, 1, 1));

        panel.add(new JLabel("Metrica:"), gbc(0, 2, 1));
        comboMetrica = new JComboBox<>(new String[]{"Consumo Energetico", "Emisiones de CO2", "Disponibilidad"});
        comboMetrica.addActionListener(e -> actualizarDatos());
        panel.add(comboMetrica, gbc(1, 2, 1));

        panel.add(new JSeparator(), gbc(0, 3, 2));

        lblValor = new JLabel("Valor: ");
        lblValor.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lblValor, gbc(0, 4, 2));

        lblUnidad = new JLabel("Unidad: ");
        panel.add(lblUnidad, gbc(0, 5, 2));

        lblDescripcion = new JLabel("Descripcion: ");
        lblDescripcion.setFont(new Font("Arial", Font.ITALIC, 11));
        panel.add(lblDescripcion, gbc(0, 6, 2));

        // Etiqueta de alerta (inicialmente vacia)
        lblAlerta = new JLabel("", SwingConstants.CENTER);
        lblAlerta.setFont(new Font("Arial", Font.BOLD, 12));
        lblAlerta.setOpaque(true);
        lblAlerta.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        panel.add(lblAlerta, gbc(0, 7, 2));

        JLabel lblRef = new JLabel("<html><i>Umbrales: Energia &lt;1500 OK / &lt;2500 WARN / &gt;2500 CRIT" +
                                   " | CO2 &lt;2.0 OK / &lt;3.5 WARN / &gt;3.5 CRIT" +
                                   " | Disp. &gt;99.9% OK / &gt;99% WARN / &lt;99% CRIT</i></html>");
        lblRef.setFont(new Font("Arial", Font.PLAIN, 9));
        lblRef.setForeground(Color.GRAY);
        panel.add(lblRef, gbc(0, 8, 2));

        JButton btnCerrar = new JButton("Cerrar sesion");
        btnCerrar.addActionListener(e -> {
            txtUsuario.setText("");
            txtContrasena.setText("");
            mostrarPantalla("login");
        });
        panel.add(btnCerrar, gbc(0, 9, 2));

        return panel;
    }

    private void actualizarDatos() {
        if (comboServidor == null || comboMetrica == null) return;

        int srv = comboServidor.getSelectedIndex();
        int met = comboMetrica.getSelectedIndex();

        String valorStr = DATOS[srv][met][0];
        lblValor.setText("Valor: "        + valorStr);
        lblUnidad.setText("Unidad: "      + DATOS[srv][met][1]);
        lblDescripcion.setText("Descripcion: " + DATOS[srv][met][2]);

        evaluarAlerta(met, valorStr);
    }

    private void evaluarAlerta(int metrica, String valorStr) {
        double valor;
        try {
            valor = Double.parseDouble(valorStr.replace(" ", ""));
        } catch (NumberFormatException e) {
            lblAlerta.setText("");
            lblAlerta.setBackground(null);
            return;
        }

        double warn = UMBRAL_WARN[0][metrica];
        double crit = UMBRAL_CRIT[0][metrica];
        boolean esDisponibilidad = (metrica == 2); // la disponibilidad: menor = peor

        int nivel; // 0=ok, 1=warn, 2=critico
        if (!esDisponibilidad) {
            // energia y CO2: mayor valor = peor
            if (valor > crit)      nivel = 2;
            else if (valor > warn) nivel = 1;
            else                   nivel = 0;
        } else {
            // disponibilidad: menor valor = peor
            if (valor < crit)      nivel = 2;
            else if (valor < warn) nivel = 1;
            else                   nivel = 0;
        }

        switch (nivel) {
            case 0:
                lblAlerta.setText("OK  —  Valor dentro del rango recomendado");
                lblAlerta.setBackground(new Color(198, 239, 206));
                lblAlerta.setForeground(new Color(0, 97, 0));
                break;
            case 1:
                lblAlerta.setText("ADVERTENCIA  —  Valor elevado, revisar consumo");
                lblAlerta.setBackground(new Color(255, 235, 156));
                lblAlerta.setForeground(new Color(156, 87, 0));
                break;
            case 2:
                lblAlerta.setText("CRITICO  —  Valor supera el limite recomendado");
                lblAlerta.setBackground(new Color(255, 199, 206));
                lblAlerta.setForeground(new Color(156, 0, 6));
                break;
        }
    }

    private GridBagConstraints gbc(int x, int y, int width) {
        GridBagConstraints g = new GridBagConstraints();
        g.insets    = new Insets(5, 10, 5, 10);
        g.fill      = GridBagConstraints.HORIZONTAL;
        g.gridx     = x;
        g.gridy     = y;
        g.gridwidth = width;
        return g;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(programa1::new);
    }
}
