package CLIENTE.vistaCliente;

import CLIENTE.controladorCliente.ControladorCliente;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

//Vista principal (Login)
public class VistaLogeo extends JFrame{

    ControladorCliente controladorCliente;
    JPanel panelIzquierdo;
    JPanel panelDerecho;
    JLabel jLabelUsuario, jLabelPassword;
    JTextFieldRound textFieldRound;
    JPasswordFieldRound passwordFieldRound;
    JButton jButtonAceptarLogin;
    JButton jButtonCancelarLogin;
    JCheckBox checkBox;


    public VistaLogeo(){
        setTitle("Ventana Login BANAMER");
        setBounds(450,180,700,430);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        instanciaComponentes();
        addBackground();
        agregarComponentes();
    }

    public void instanciaComponentes(){
        this.getContentPane().setBackground(new Color(11, 55, 117));
        this.getContentPane().setLayout(new GridLayout(1,2));

        panelIzquierdo = new JPanel();
        panelDerecho = new JPanel(null);
        panelDerecho.setBackground(new Color(0xDAAD0606, true));

        getContentPane().add(panelIzquierdo);
        getContentPane().add(panelDerecho);

        jLabelPassword = new JLabel("Contraseña:");
        jLabelUsuario = new JLabel("      Usuario:");

        passwordFieldRound = new JPasswordFieldRound();
        passwordFieldRound.setEditable(true);

        jButtonAceptarLogin = new JButton("Login");
        jButtonAceptarLogin.setBorder(javax.swing.BorderFactory.createBevelBorder(BevelBorder.RAISED));

        jButtonCancelarLogin = new JButton("Cancelar");
        jButtonCancelarLogin.setBorder(javax.swing.BorderFactory.createBevelBorder(BevelBorder.RAISED));

        textFieldRound = new JTextFieldRound();
        checkBox = new JCheckBox();
    }

    public void addBackground(){
        try{
            Fondo_Swing fondo = new Fondo_Swing(ImageIO.read(new File("src/images/BANAMER_logo.png")));
            panelIzquierdo.setBorder(fondo);
            Fondo_Swing fondo2 = new Fondo_Swing(ImageIO.read(new File("src/images/BANAMER-LEFT.png")));
            panelDerecho.setBorder(fondo2);
        }catch (IOException ex){
            JOptionPane.showMessageDialog(this,ex.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void agregarComponentes(){
        jLabelUsuario.setBounds(34,80,110,30);
        jLabelUsuario.setFont(new Font("Century Gothic",Font.ITALIC,16));
        jLabelUsuario.setForeground(Color.white);
        panelDerecho.add(jLabelUsuario);

        jLabelPassword.setBounds(34,165,110,30);
        jLabelPassword.setFont(new Font("Century Gothic",Font.ITALIC,16));
        jLabelPassword.setForeground(Color.white);
        panelDerecho.add(jLabelPassword);

        textFieldRound.setBounds(140, 80, 175, 30);
        panelDerecho.add(textFieldRound);

        passwordFieldRound.setBounds(140, 165, 175, 30);
        panelDerecho.add(passwordFieldRound);

        checkBox.setBounds(320,167,13,13);
        checkBox.setBorder(new BorderLineRound(Color.lightGray.darker(),true));
        panelDerecho.add((checkBox));

        jButtonCancelarLogin.setBounds(130, 270, 85, 25);
        panelDerecho.add(jButtonCancelarLogin);

        jButtonAceptarLogin.setBounds(230,270,85,25);
        panelDerecho.add(jButtonAceptarLogin);

    }

    public void showMessage(String s){
        JOptionPane.showMessageDialog(this,s,"Sistema Login",JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String s){
        JOptionPane.showMessageDialog(this,s,"Login - ERROR",JOptionPane.ERROR_MESSAGE);
    }

    public String getPassword(){
        return String.valueOf(passwordFieldRound.getPassword()); //retorna la contrasena para enviarla al servidor y que este lo verifique en la base de datos
    }

    public String getUsuario(){
        return textFieldRound.getText();
    }

    public JTextField getjTextFieldUsuario() {
        return textFieldRound;
    }

    public JPasswordField getjPasswordField() {
        return passwordFieldRound;
    }

    public JButton getjButtonAceptarLogin() {
        return jButtonAceptarLogin;
    }

    public JButton getjButtonCancelarLogin() {
        return jButtonCancelarLogin;
    }

    public void addListeners(ActionListener ac){
        jButtonAceptarLogin.setActionCommand("Login");
        jButtonAceptarLogin.addActionListener(ac);
        jButtonCancelarLogin.setActionCommand("Cancelar");
        jButtonCancelarLogin.addActionListener(ac);
        checkBox.setActionCommand("CheckBox");
        checkBox.addActionListener(ac);
    }

    public JCheckBox getCheckBox() {
        return checkBox;
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

    public void salir() {
        System.exit(0);
    }
}
