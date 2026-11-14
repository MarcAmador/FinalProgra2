package vista;

import model.ResultadoLogin;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class InicioFrame extends JFrame {

    public InicioFrame(ResultadoLogin datos) {
        setTitle("Bienvenido");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 16));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(24, 30, 22, 30));

        JPanel panelEncabezado = new JPanel(new BorderLayout(0, 10));
        JLabel lblEstado = new JLabel(datos.getMensaje());
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEstado.setForeground(new Color(22, 101, 52));
        panelEncabezado.add(lblEstado, BorderLayout.NORTH);
        panelEncabezado.add(new JSeparator(), BorderLayout.SOUTH);
        panelPrincipal.add(panelEncabezado, BorderLayout.NORTH);

        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createEmptyBorder(6, 0, 8, 0));
        String usuarioTexto = datos.getUsuario() != null ? datos.getUsuario() : "-";
        String nombreTexto = datos.getNombre() != null ? datos.getNombre() : "-";

        agregarFila(panelDatos, 0, "Usuario:", usuarioTexto);
        agregarFila(panelDatos, 1, "Nombre:", nombreTexto);

        panelPrincipal.add(panelDatos, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout(0, 14));
        panelInferior.add(new JSeparator(), BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setPreferredSize(new Dimension(130, 34));
        btnCerrarSesion.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
        panelBotones.add(btnCerrarSesion);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        setContentPane(panelPrincipal);

        pack();
        setSize(420, Math.max(getHeight(), 275));
        setLocationRelativeTo(null);
    }

    private void agregarFila(JPanel panel, int fila, String etiqueta, String valor) {
        GridBagConstraints gbcEtiqueta = new GridBagConstraints();
        gbcEtiqueta.gridx = 0;
        gbcEtiqueta.gridy = fila;
        gbcEtiqueta.anchor = GridBagConstraints.WEST;
        gbcEtiqueta.insets = new Insets(8, 0, 8, 18);

        JLabel lblClave = new JLabel(etiqueta);
        lblClave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lblClave, gbcEtiqueta);

        GridBagConstraints gbcValor = new GridBagConstraints();
        gbcValor.gridx = 1;
        gbcValor.gridy = fila;
        gbcValor.weightx = 1.0;
        gbcValor.fill = GridBagConstraints.HORIZONTAL;
        gbcValor.anchor = GridBagConstraints.WEST;
        gbcValor.insets = new Insets(8, 0, 8, 0);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lblValor, gbcValor);
    }
}
