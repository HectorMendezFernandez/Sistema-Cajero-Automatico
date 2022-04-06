package CLIENTE.vistaCliente;

import CLIENTE.controladorCliente.ControladorCliente;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class VistaRetiro extends JFrame{
    ControladorCliente controladorCliente;
    JButton jButtonAceptar;
    JButton jButtonLimpiar;
    JButton jButtonRegresar;
    JLabel jLabelSaldo;
    JLabel jLabelRetiro;
    JLabel jLabelResultado;
    JTextField jTextFieldSaldo, jTextFieldRetiro, jTextFieldResultado;
    JPanel panelPrincipal;

    public VistaRetiro(){
        setTitle("Ventana de retiro BANAMER");
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
        jLabelSaldo.setBounds(15,40,150,20);
        jLabelSaldo.setFont(new Font("Agency FB",Font.BOLD,15));
        jLabelSaldo.setForeground(Color.white);
        panelPrincipal.add(jLabelSaldo);

        jLabelRetiro.setBounds(15,110,150,20);
        jLabelRetiro.setFont(new Font("Agency FB",Font.BOLD,15));
        jLabelRetiro.setForeground(Color.white);
        panelPrincipal.add(jLabelRetiro);

        jLabelResultado.setBounds(15,180,150,20);
        jLabelResultado.setFont(new Font("Agency FB",Font.BOLD,15));
        jLabelResultado.setForeground(Color.white);
        panelPrincipal.add(jLabelResultado);

        //se agregan los campos de texto
        jTextFieldSaldo.setBounds(150,40,230,25);
        jTextFieldSaldo.setEditable(false);
        panelPrincipal.add(jTextFieldSaldo);

        jTextFieldRetiro.setBounds(150,110,230,25);
        panelPrincipal.add(jTextFieldRetiro);

        jTextFieldResultado.setBounds(150,180,230,25);
        panelPrincipal.add(jTextFieldResultado);

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
        jLabelSaldo = new JLabel("Saldo de Cuenta: ");
        jLabelRetiro = new JLabel("Monto a Retirar: ");
        jLabelResultado = new JLabel(" Nuevo Saldo:");

        //TextFields
        jTextFieldSaldo = new JTextField();
        jTextFieldRetiro = new JTextField();
        jTextFieldResultado = new JTextField();
        jTextFieldResultado.setEditable(false);
    }

    public void addListeners(ActionListener ac){
        jButtonRegresar.setActionCommand("RegresarR");
        jButtonRegresar.addActionListener(ac);
        jButtonAceptar.setActionCommand("AceptarR");
        jButtonAceptar.addActionListener(ac);
        jButtonLimpiar.setActionCommand("LimpiarR");
        jButtonLimpiar.addActionListener(ac);
    }

    public void setControlador(ControladorCliente controlador) {
        controladorCliente = controlador;
    }

    public void limpiarCampos(){
        jTextFieldResultado.setText("");
        jTextFieldRetiro.setText("");
    }

    public void showMessage(String s){
        JOptionPane.showMessageDialog(this,s,"Retiro",JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String s){
        JOptionPane.showMessageDialog(this,s,"Retiro - ERROR",JOptionPane.ERROR_MESSAGE);
    }

    public String getSaldoActual(){
        return jTextFieldSaldo.getText();
    }

    public String getMontoRetirar(){
        return jTextFieldRetiro.getText();
    }

    public String getResultado(){
        return jTextFieldResultado.getText();
    }

    public void hacerVisible() {
        setVisible(true);
    }

    public void hacerInvisible() {
        setVisible(false);
    }

    public void setSaldoActual(String saldo){
        jTextFieldSaldo.setText(saldo);
    }

    public void setResultado(String resultado){
        jTextFieldResultado.setText(resultado);
    }

    public void DesabilitarRetiro() {
        jTextFieldRetiro.setEditable(false);
        jTextFieldResultado.setEditable(false);
        jButtonAceptar.setEnabled(false);
    }
    public void HabilitarRetiro() {
        jTextFieldRetiro.setEditable(true);
        jTextFieldResultado.setEditable(true);
        jButtonAceptar.setEnabled(true);
    }
}
