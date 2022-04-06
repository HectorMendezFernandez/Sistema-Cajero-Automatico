package CLIENTE.vistaCliente;

import CLIENTE.controladorCliente.ControladorCliente;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

//ventana Principal (hacer retiro, cambiar clave, salir)
public class VistaPrincipal extends JFrame {

    ControladorCliente controladorCliente;
    JButton jButtonRetiro;
    JButton jButtonCambioPass;
    JButton jButtonSalir;
    JPanel panelPrincipal;

    public VistaPrincipal() {
        setTitle("Ventana Principal BANAMER");
        setBounds(500,180,500,430);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(false);
        instanciaComponentes();
        addBackground();
        agregarComponentes();
        setLayout(new GridLayout(1, 2));
    }

    private void addBackground() {
        try{
            Fondo_Swing fondo = new Fondo_Swing(ImageIO.read(new File("src/images/BANAMER-LEFT.png")));
            panelPrincipal.setBorder(fondo);
        }catch (IOException ex){
            JOptionPane.showMessageDialog(this,ex.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarComponentes() {

        jButtonRetiro.setBounds(80,80,300,40);
        panelPrincipal.add(jButtonRetiro);

        jButtonCambioPass.setBounds(80,170,300,40);
        panelPrincipal.add(jButtonCambioPass);

        jButtonSalir.setBounds(80,260,300,40);
        panelPrincipal.add(jButtonSalir);


    }

    private void instanciaComponentes() {
        this.getContentPane().setLayout(new GridLayout(1,1));
        panelPrincipal = new JPanel(null);
        this.getContentPane().add(panelPrincipal);

        Font font = new Font("Arial",Font.BOLD,30);
        this.getContentPane().setLayout(null);

        jButtonCambioPass = new JButton("CAMBIO CLAVE");
        jButtonCambioPass.setBorder(javax.swing.BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jButtonCambioPass.setFont(font);

        jButtonRetiro = new JButton("RETIRO");
        jButtonRetiro.setBorder(javax.swing.BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jButtonRetiro.setFont(font);

        jButtonSalir = new JButton("SALIR");
        jButtonSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jButtonSalir.setFont(font);
    }

    public void addListeners(ActionListener ac){
        jButtonSalir.setActionCommand("Salir");
        jButtonSalir.addActionListener(ac);
        jButtonRetiro.setActionCommand("Retiro");
        jButtonRetiro.addActionListener(ac);
        jButtonCambioPass.setActionCommand("CambiarPassword");
        jButtonCambioPass.addActionListener(ac);
    }

    public void setControlador(ControladorCliente controlador) {
        controladorCliente = controlador;
    }

    public void hacerVisible() {
        setVisible(true);
    }

    public void hacerInvisible() {
        setVisible(false);
    }

}
