package vista;

import model.ResultadoLogin;
import service.LoginClienteService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class LoginFrame extends JFrame {

    private final JTextField txtUsuario;
    private final JPasswordField txtPassword;
    private final JButton btnIngresar;
    private final LoginClienteService loginService;

    public LoginFrame() {
        this.loginService = new LoginClienteService();

        setTitle("Acceso al Sistema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 14));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(24, 30, 26, 30));

        JLabel lblTitulo = new JLabel("Iniciar sesión");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 19));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridy = 0;
        gbc.insets = new Insets(4, 0, 4, 0);
        panelFormulario.add(lblUsuario, gbc);

        txtUsuario = new JTextField();
        txtUsuario.setPreferredSize(new Dimension(280, 34));
        txtUsuario.putClientProperty("JTextField.placeholderText", "Ingrese su usuario");
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 12, 0);
        panelFormulario.add(txtUsuario, gbc);

        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 4, 0);
        panelFormulario.add(lblPassword, gbc);

        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(280, 34));
        txtPassword.putClientProperty("JTextField.placeholderText", "Ingrese su contraseña");
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        panelFormulario.add(txtPassword, gbc);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setPreferredSize(new Dimension(280, 36));
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelFormulario.add(btnIngresar, gbc);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        setContentPane(panelPrincipal);

        configurarEventos();
        getRootPane().setDefaultButton(btnIngresar);

        pack();
        setSize(390, Math.max(getHeight(), 320));
        setLocationRelativeTo(null);
    }

    private void configurarEventos() {
        btnIngresar.addActionListener(e -> procesarIngreso());
    }

    private void procesarIngreso() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Complete el usuario y la contraseña.",
                    "Campos requeridos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        btnIngresar.setEnabled(false);
        try {
            ResultadoLogin resultado = loginService.enviarLogin(usuario, password);

            if (resultado.isAutenticado()) {
                this.dispose();
                InicioFrame inicioFrame = new InicioFrame(resultado);
                inicioFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        resultado.getMensaje(),
                        "Acceso denegado",
                        JOptionPane.ERROR_MESSAGE
                );
                txtPassword.setText("");
                txtPassword.requestFocusInWindow();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo conectar con la API.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            btnIngresar.setEnabled(true);
        }
    }
}
