package CLIENTE.vistaCliente;

import CLIENTE.controladorCliente.ControladorCliente;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class VistaCambioPass extends JFrame{

    ControladorCliente controladorCliente;
    JButton jButtonAceptar;
    JButton jButtonLimpiar;
    JButton jButtonRegresar;
    JLabel jLabelClaveAct;
    JLabel jLabelClaveNew;
    JLabel jLabelConfirmPass;
    JTextField jTextFieldClaveAct, jTextFieldClaveNew, jTextFieldConfirmPass;
    JPanel panelPrincipal;


    public VistaCambioPass(){
        setTitle("Cambio Clave BANAMER");
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
        //se agregan los labels
        jLabelClaveAct.setBounds(15,40,110,20);
        jLabelClaveAct.setFont(new Font("Agency FB",Font.BOLD,15));
        jLabelClaveAct.setForeground(Color.white);
        panelPrincipal.add( jLabelClaveAct);

        jLabelClaveNew.setBounds(15,110,110,20);
        jLabelClaveNew.setFont(new Font("Agency FB",Font.BOLD,15));
        jLabelClaveNew.setForeground(Color.white);
        panelPrincipal.add(jLabelClaveNew);

        jLabelConfirmPass.setBounds(15,180,150,20);
        jLabelConfirmPass.setFont(new Font("Agency FB",Font.BOLD,15));
        jLabelConfirmPass.setForeground(Color.white);
        panelPrincipal.add(jLabelConfirmPass);

        //se agregan los campos de texto
        jTextFieldClaveAct.setBounds(150,40,230,25);
        panelPrincipal.add(jTextFieldClaveAct);

        jTextFieldClaveNew.setBounds(150,110,230,25);
        panelPrincipal.add(jTextFieldClaveNew);

        jTextFieldConfirmPass.setBounds(150,180,230,25);
        panelPrincipal.add(jTextFieldConfirmPass);

        //se agregan los botones
        jButtonAceptar.setBounds(180,280,110,25);
        panelPrincipal.add(jButtonAceptar);

        jButtonLimpiar.setBounds(40,280,110,25);
        panelPrincipal.add(jButtonLimpiar);

        jButtonRegresar.setBounds(320,280,120,25);
        panelPrincipal.add(jButtonRegresar);

    }

    private void instanciaComponentes() {
        this.getContentPane().setLayout(new GridLayout(1,1));
        panelPrincipal = new JPanel(null);
        this.getContentPane().add(panelPrincipal);

        Font font = new Font("Arial",Font.BOLD,15);
        this.getContentPane().setLayout(null);

        jButtonLimpiar = new JButton("LIMPIAR");
        jButtonLimpiar.setFont(font);

        jButtonAceptar = new JButton("ACEPTAR");
        jButtonAceptar.setFont(font);

        jButtonRegresar = new JButton("REGRESAR");
        jButtonRegresar.setFont(font);

        //Labels
        jLabelClaveAct = new JLabel("  Clave Actual: ");
        jLabelClaveNew = new JLabel("  Clave Nueva: ");
        jLabelConfirmPass = new JLabel("Confirmar Clave:");

        //TextFields
        jTextFieldClaveAct = new JTextField();
        jTextFieldClaveNew = new JTextField();
        jTextFieldConfirmPass = new JTextField();
    }

    public void addListeners(ActionListener ac){
        jButtonRegresar.setActionCommand("RegresarC");
        jButtonRegresar.addActionListener(ac);
        jButtonAceptar.setActionCommand("AceptarC");
        jButtonAceptar.addActionListener(ac);
        jButtonLimpiar.setActionCommand("LimpiarC");
        jButtonLimpiar.addActionListener(ac);
    }

    public void setControlador(ControladorCliente controlador) {
        controladorCliente = controlador;
    }

    public void limpiarCampos(){
        jTextFieldConfirmPass.setText("");
        jTextFieldClaveNew.setText("");
        jTextFieldClaveAct.setText("");
    }

    public void showMessage(String s){
        JOptionPane.showMessageDialog(this,s,"Cambio class",JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String s){
        JOptionPane.showMessageDialog(this,s,"Cambio Pass - ERROR",JOptionPane.ERROR_MESSAGE);
    }

    public String getClaveActual(){
        return jTextFieldClaveAct.getText();
    }

    public String getClaveNueva(){
        return jTextFieldClaveNew.getText();
    }

    public String getClaveConfirmacion(){
        return jTextFieldConfirmPass.getText();
    }

    public void hacerVisible() {
        setVisible(true);
    }

    public void hacerInvisible() {
        setVisible(false);
    }

}
