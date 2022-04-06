package CLIENTE.controladorCliente;



import CLIENTE.modeloCliente.ClienteModelo;
import CLIENTE.modeloCliente.Function;
import CLIENTE.vistaCliente.VistaCambioPass;
import CLIENTE.vistaCliente.VistaLogeo;
import CLIENTE.vistaCliente.VistaPrincipal;
import CLIENTE.vistaCliente.VistaRetiro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorCliente{
    VistaLogeo vistaLogeo;
    VistaRetiro vistaRetiro;
    VistaPrincipal vistaPrincipal;
    VistaCambioPass vistaCambioPass;
    ClienteModelo modelo;

    public ControladorCliente() {
        //iinstancia de vistas
        vistaLogeo = new VistaLogeo();
        vistaPrincipal = new VistaPrincipal();
        vistaRetiro = new VistaRetiro();
        vistaCambioPass = new VistaCambioPass();

        //instancia de su modelo
        modelo = new ClienteModelo();

        //agregando sus listeners
        vistaLogeo.addListeners(new ButtonListeners());
        vistaPrincipal.addListeners(new ButtonListeners());
        vistaRetiro.addListeners(new ButtonListeners());
        vistaCambioPass.addListeners(new ButtonListeners());

        //les agrego el mismo controlador a todas
        modelo.setControlador(this);
        vistaLogeo.setControlador(this);
        vistaCambioPass.setControlador(this);
        vistaPrincipal.setControlador(this);
        vistaRetiro.setControlador(this);

        arrancar();
    }

    private void arrancar() {
        //le decimos a la vista que le muestre al usuario que se esta conectado con el serviidor
        //se conecta con el servidor
        modelo.conectaConElServidor();
        //despues de crear el socket de espera creamos los flujos
        modelo.crearFlujos();

    }

    private class ButtonListeners implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String actionC = e.getActionCommand();
            switch (actionC) {

                //---------Listeners VISTA LOGIN------------
                case "Login":
                    modelo.enviarMensaje("Login");
                    modelo.enviarMensaje(vistaLogeo.getUsuario());
                    modelo.enviarMensaje(vistaLogeo.getPassword());
                    //realizar verificacion...
                    try {
                        String verificacion = modelo.recibirMensaje(); //correcto o incorrecto
                        if (verificacion.equals("Correcto")) {
                            //Recibe al usuario y la clave para futuros procesos
                            clave = modelo.recibirMensaje();
                            usuario = modelo.recibirMensaje();
                            cedula = modelo.recibirMensaje();
                            //Muestra mensaje de bienvenida
                            vistaLogeo.showMessage("Bienvenido");
                            //esconde y muestra ventanas
                            vistaPrincipal.hacerVisible();
                            vistaLogeo.hacerInvisible();
                        } else {
                            vistaLogeo.showErrorMessage("El usuario no existe en la base de datos");
                        }
                    }catch (Exception ex){
                        vistaLogeo.showErrorMessage("Algo ha ocurrido (ERROR DESCONOCIDO)");
                    }
                    break;

                //Cierra la conexion con el cliente y sus ventanas
                case "Cancelar":
                    modelo.enviarMensaje("Salir");
                    //modelo.cerrarTodo();
                    vistaLogeo.salir();
                    break;

                //------------Listeners VISTA PRINCIPAL--------------
                case "Retiro":
                    String saldo= "";
                    //envia mensaje de retiro al servidor para su switch
                    modelo.enviarMensaje("Retiro");
                    //envia los datos del usuario al que quiero acceder
                    modelo.enviarMensaje(cedula);
                    //recibe el sald0 ese cliente
                    saldo = modelo.recibirMensaje();
                    //inserta el saldo del cliente en la casilla antes de abrir la venta
                    vistaRetiro.setSaldoActual(saldo);
                    vistaPrincipal.hacerInvisible();
                    vistaRetiro.hacerVisible();
                    break;

                case "Salir":
                      modelo.enviarMensaje("Salir");
                   // modelo.cerrarTodo();
                    vistaLogeo.salir();
                    break;

                case "CambiarPassword":
                    vistaPrincipal.hacerInvisible();
                    vistaCambioPass.hacerVisible();
                    break;

                //-------------Listeners VISTA RETIROS--------------
                case "AceptarR":
                    try {
                        if(vistaRetiro.getMontoRetirar().isEmpty()) {
                            throw new Exception("Debe de insertar un monto para poder retirar");
                        }
                     /*   if(!Function.isNumeric(vistaRetiro.getMontoRetirar())) {
                            throw new Exception("Debe de insertar un valor valido (numeros unicamente)");
                        }*/
                        if(Double.valueOf(vistaRetiro.getSaldoActual())< Double.valueOf(vistaRetiro.getMontoRetirar())){
                            throw new Exception("Saldo insuficiente");
                        }
                        //despues de validar que all este correcto envia el mensaje al servidor para que edite en saldo en la base de datos y me envie la nueva
                        modelo.enviarMensaje("AceptarR");
                        //envia la informacion del cliente para que lo busque en la base de datos y edite su informacion
                        modelo.enviarMensaje(cedula);
                        //envia el monto a retirar para que lo reste al saldo actual en la base de datos
                        modelo.enviarMensaje(vistaRetiro.getMontoRetirar());
                        //despues de realizar el proceso de actualizacion de saldo en la base de datos el servidor me devuelve ese nuevo saldo
                        //en la casilla de saldo
                        Saldo = modelo.recibirMensaje();
                        vistaRetiro.setResultado(Saldo);
                        vistaRetiro.DesabilitarRetiro();
                    }catch (Exception ex){
                        vistaRetiro.showMessage(ex.getMessage());
                    }
                    break;

                case "RegresarR":
                    vistaPrincipal.hacerVisible();
                    vistaRetiro.hacerInvisible();
                    break;

                case "LimpiarR":
                    vistaRetiro.setSaldoActual(Saldo);
                    vistaRetiro.limpiarCampos();
                    vistaRetiro.HabilitarRetiro();
                    break;

                //----------Listeners VISTA CAMBIOPASS-------
                case "AceptarC":
                    String p = vistaCambioPass.getClaveActual();
                    String pN = vistaCambioPass.getClaveNueva();
                    String pC = vistaCambioPass.getClaveConfirmacion();
                    modelo.enviarMensaje("ObtenerPassword");
                    modelo.enviarMensaje(cedula);
                    String password = modelo.recibirMensaje();
                    if(Function.verificarContrasena(p,pN,pC,password,vistaCambioPass)) {
                        try {
                            modelo.enviarMensaje("AceptarC");
                            modelo.enviarMensaje(cedula);
                            modelo.enviarMensaje(pN);
                            String resp = modelo.recibirMensaje();
                            if(resp.equals("Listo")) {
                                vistaCambioPass.showMessage("Clave modificada exitosamente");
                            }
                            else
                                vistaCambioPass.showErrorMessage("No se pudo modificar la clave");
                        } catch (Exception ex) {
                            vistaCambioPass.showErrorMessage(ex.getMessage());
                        }
                    }
                        break;

                case "RegresarC":
                    vistaPrincipal.hacerVisible();
                    vistaCambioPass.hacerInvisible();
                    vistaCambioPass.limpiarCampos();
                    break;

                case "LimpiarC":
                    vistaCambioPass.limpiarCampos();
                    break;
                case "CheckBox":
                    if(vistaLogeo.getCheckBox().isSelected()){
                        vistaLogeo.getjPasswordField().setEchoChar((char)0);
                    }else{
                        vistaLogeo.getjPasswordField().setEchoChar('•');
                    }
                    break;
            }
        }
    }

    private String usuario = "";
    private String clave = "";
    private String cedula= "";
    private String Saldo = "";
}
