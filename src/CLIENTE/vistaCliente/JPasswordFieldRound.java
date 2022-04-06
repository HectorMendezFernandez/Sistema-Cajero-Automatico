package CLIENTE.vistaCliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JPasswordFieldRound extends JPasswordField {

    private final Dimension d = new Dimension(250, 28);
    private  final BorderLineRound border = new BorderLineRound(Color.lightGray.darker(),true);
    private final JCheckBox check = new JCheckBox();

    public JPasswordFieldRound() {
        setOpaque(true);
        setBorder(border);
        setSize(d);
        setPreferredSize(d);
        setHorizontalAlignment(CENTER);   //letras en el centro
        setFont(new Font("Century Gothic",0,12));
        setEchoChar('•');
    }
}








/*  check.setIcon(new ImageIcon(getClass().getResource("/images/view2.png")));
       check.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(check.isSelected()){
                    check.setIcon(new ImageIcon(getClass().getResource("/images/view2.png")));
                }else{
                    check.setIcon(new ImageIcon(getClass().getResource("/images/hidden2.png")));
                }
                check.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(check.isSelected()){
                    check.setIcon(new ImageIcon(getClass().getResource("/images/view2.png")));
                }else{
                    check.setIcon(new ImageIcon(getClass().getResource("/images/hidden2.png")));
                }
                check.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        check.addActionListener((ActionEvent e)->{
            if(check.isSelected()){
                check.setIcon(new ImageIcon(getClass().getResource("/images/view2.png")));
            }else{
                check.setIcon(new ImageIcon(getClass().getResource("/images/hidden2.png")));
            }

            if(check.isSelected()){
                setEchoChar((char) 0);
                requestFocus();
            }else{
                setEchoChar('*');
            }
        });*/
/*   addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txtFocusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtFocusLost(e);
                setEchoChar('*');
                check.setIcon(new ImageIcon((getClass().getResource("/images/view2.png"))));
                check.setSelected(false);
            }
        });*/
/*
setLayout(new BorderLayout());
        add(check,BorderLayout.EAST);
        setFocusable(false);*/
