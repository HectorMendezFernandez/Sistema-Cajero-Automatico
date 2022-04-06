package CLIENTE.principalCliente;

import CLIENTE.controladorCliente.ControladorCliente;
import javax.swing.*;

public class PrincipalCliente {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ControladorCliente();
            }
        });
    }
}
