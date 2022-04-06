package CLIENTE.vistaCliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class JTextFieldRound extends JTextField {

    private final Dimension d = new Dimension(250, 28);
    private  final BorderLineRound border = new BorderLineRound(Color.lightGray.darker(),true);

    public JTextFieldRound(){
        setOpaque(true);
        setBorder(border);
        setSize(d);
        setPreferredSize(d);
        setHorizontalAlignment(CENTER);   //letras en el centro
        setFont(new Font("Century Gothic",0,12));
    }
}

/*
    addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            txtFocusGained(e);
        }

        @Override
        public void focusLost(FocusEvent e) {
            txtFocusLost(e);
        }

           private void txtFocusLost(FocusEvent e) {
        setBorder(border);
    }

    private void txtFocusGained(FocusEvent e) {
        setBorder(new BorderLineRound(Color.black.darker(), true));
    }
    });*/

