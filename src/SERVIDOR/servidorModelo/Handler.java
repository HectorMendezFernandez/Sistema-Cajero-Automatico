package SERVIDOR.servidorModelo;


import CLIENTE.controladorCliente.ControladorCliente;
import CLIENTE.modeloCliente.Function;
import SERVIDOR.sql.SQL_Executor;

import javax.swing.*;
import java.awt.desktop.SystemSleepEvent;
import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Handler extends Thread{

    Socket socket; //socket para abrir los buffers
    BufferedReader bufferedReader; //lee
    BufferedWriter bufferedWriter; ///escribe para enviar (para enviar al cliente)
    SQL_Executor sqlExecutor;
    ControladorCliente controladorCliente;

    public Handler(Socket socket){
        this.socket = socket;
        crearFlujos();
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                String user, password, cedula;
                String mensaje = recibirMensaje();
                switch (mensaje) {
                    //verifica que el usuario y contrasena esten correctas, si es asi manda un mensaje de correcto al cliente
                    // debe de guardar el usuario y contrasena en algunas variables para realizar futuras transacciones (retiro, cambioClave)
                    case "Login":
                        user = recibirMensaje();
                        password = recibirMensaje();
                        if (verificacionExistenciaLogin(password, user) == true) {
                            enviarMensaje("Correcto"); //abre ventana principal con mensaje de bienvenida\
                            //manda el usuario y clave para que el controlador los guarde y la copntrasena para futuras busquedas
                            enviarMensaje(password);
                            enviarMensaje(user);
                            cedula = cedulaClienteSQL(user, password);
                            enviarMensaje(cedula);
                        } else {
                            enviarMensaje("Incorrecto"); //manda mensaje ("No existe ese usuario en la base de datos
                        }
                        break;

                    //se sale del programa (cierra el servidor y el cliente)
                    case "Cancelar":
                        String cancel = recibirMensaje();
                        System.exit(0);
                        break;

                    //cuando entro al boton retiro accedo al usuario en la base de datos y extraigo su saldo para mostrarlo
                    //en la casilla de monto en la vista
                    case "Retiro":
                        cedula = recibirMensaje();
                        String saldo = accederASaldo(cedula);
                        enviarMensaje(saldo); //envio el saldo en string
                        break;

                    //-Retiro (recibe el monto a retirar y verifica que cuente con ese monto para poder retirarlo
                    //cuandolo retira manda el mensaje al controladorCliente para que se actualice su nuevo monto
                    //-Debe de realizar una factura de su transaccion (Manda el mensaje 'Retiro', la cedula del cliente y
                    //el monto retirado para realizar un registro en la base de datos)
                    case "AceptarR":
                        String tipoTransacion = "RETIRO";
                        String monto;
                        cedula = recibirMensaje();
                        System.out.println("Cedula recibida" + cedula);
                        //recibe los datos del cliente
                        //Recibe el monto a retirar lo actualiza en la base de datos
                        monto = recibirMensaje();
                        System.out.println("Monto recibido: "+monto);
                        String newSaldo = recibeNuevoSaldo(cedula, monto);
                        System.out.println("Nuevo saldo: "+newSaldo);
                        //realiza una factura o registro de la transacion realizada
                        realizarFacturaTransacion(cedula, monto, tipoTransacion);
                        enviarMensaje(newSaldo);
                        break;

                    //Cambio clave (recibe la clave que guardo anteriormente para verificar que la actual sea correcta)
                    //posterior a eso y de la verificacion de la contrasena nueva (cumpla con las normas) entonces accedemos
                    //a la base de datos de ese usuario y actualizamos el espacio de contrasena
                    case "ObtenerPassword":
                        cedula = recibirMensaje();
                        password = accederAPassword(cedula);
                        enviarMensaje(password);
                        break;

                    case "AceptarC":
                        cedula = recibirMensaje();
                        String Newpassword = recibirMensaje();
                        String confirmacion = recibeNuevoPassword(cedula, Newpassword);
                        enviarMensaje(confirmacion);
                        break;
                    case "Salir":
                        cerrarTodo();
                        break;
                }
            }
        }catch (Exception ex){
            cerrarTodo();
            System.out.println("Un cliente ha salido del sistema...");
        }
    }

    private void cerrarTodo() {
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }

            if(bufferedWriter != null){
                bufferedWriter.close();
            }

            if(socket != null){
                socket.close();
            }
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }

    private void realizarFacturaTransacion(String cedula, String monto, String tipoTransacion) {
        ResultSet rs = null;
        try {
            sqlExecutor = new SQL_Executor();
            //accedo a todos los clientes de la tabla CLIENTE
            rs = sqlExecutor.ejecutaQuery("SELECT * FROM CLIENTE");
            //voy recorriendo toda la tabla de clientes para verficiar que exista en la base de datos segun los parametros
            while (rs.next()) {
                //accede a la columna de CEDULA y verifica que exista
                if (rs.getString("CEDULA").equals(cedula)) {
                    break; //me salgo del while
                }
            }
            String tipoTrans;
            String accionQuery[] = new String[5];
            accionQuery[0] = "INSERT INTO TRANSACCIONES (ID_TRANSACCION, ID_CLIENTE, MONTO_TRANSAC, TIPO_ID) VALUES\n" +
                    "(?,?,?,?);";
            accionQuery[1] = Function.generateRandomID(cedula); //manda el ID de la trasancionn
            accionQuery[2] = cedula; //MANDA LA CEDULA 'CEDULA = ?'
            accionQuery[3] = monto; //Manda el monto de la 'MONTO_TRANS = ?'
            if(tipoTransacion == "RETIRO"){
                tipoTrans = "1";  //retiro
            }else {
                tipoTrans = "2"; //deposito
            }
            accionQuery[4] = String.valueOf(1); //Manda el tipo d transacion (1)Retiro (2)Deposito ' TIPO ID= ?'
            sqlExecutor.prepareStatement(accionQuery);
        }catch (SQLException throwables){
            showErrorMessage(throwables.getMessage());
        }
    }

    private String cedulaClienteSQL(String user, String password) {
        ResultSet rs = null;
        try {
            sqlExecutor = new SQL_Executor();
            //accedo a todos los clientes de la tabla CLIENTE
            rs = sqlExecutor.ejecutaQuery("SELECT * FROM CLIENTE");
            //voy recorriendo toda la tabla de clientes para verficiar que exista en la base de datos segun los parametros
            while (rs.next()){
                //accede a la columna de USUARIO y de CLAVE para verificar que ambos existan
                if(rs.getString("USUARIO").equals(user) && rs.getString("CLAVE").equals(password)){
                    return rs.getString("CEDULA");
                }
            }
        }catch (SQLException throwables){
            showErrorMessage(throwables.getMessage());
        }
        return "";
    }

    private String recibeNuevoSaldo(String cedula, String monto) {
        ResultSet rs = null;
        try {
            String saldoActual ="";
            String saldoNuevo = "";
            Double saldoNuevoDouble = 0.0;
            sqlExecutor = new SQL_Executor();
            //se accede a los clientes de la base de datos para buscar al que necesitamos y acceder a su saldo
            rs = sqlExecutor.ejecutaQuery("SELECT * FROM CLIENTE");
            while (rs.next()){
                //accede a la columna de USUARIO y de CLAVE para verificar que ambos existan
                System.out.println(rs.getString("CEDULA"));
                if(rs.getString("CEDULA").equals(cedula)){
                    System.out.println(rs.getString("NOMBRE"));
                    saldoActual = rs.getString("SALDO");
                    break; //me salgo del while
                }
            }
            saldoNuevoDouble = Double.parseDouble(saldoActual) - Double.parseDouble(monto); //realiza la resta y actualiza el valor
            String accionQuery[] = new String[3];
            accionQuery[0] = "UPDATE CLIENTE SET SALDO = ? WHERE CEDULA = ?;";
            accionQuery[1] = String.valueOf(saldoNuevoDouble); //manda el nuevo saldo  'SALDO = ?'
            accionQuery[2] = cedula; //MANDA LA CEDULA 'CEDULA = ?'
            sqlExecutor.prepareStatement(accionQuery);

            return String.valueOf(saldoNuevoDouble); //retorna el saldo nuevo para mandarlo al cliente
          //  accionQuery
        }catch (SQLException throwables){
           showErrorMessage(throwables.getMessage());
        }
        return "";
    }

    public String accederASaldo(String cedula) {
        ResultSet rs = null;
        String saldo = "0";
        try {
            sqlExecutor = new SQL_Executor();
            //se accede a los clientes de la base de datos para buscar al que necesitamos y acceder a su saldo
            rs = sqlExecutor.ejecutaQuery("SELECT * FROM CLIENTE");
            while (rs.next()){
                //accede a la columna de USUARIO y de CLAVE para verificar que ambos existan
                if(rs.getString("CEDULA").equals(cedula)){
                   saldo = rs.getString("SALDO");
                    return saldo; //retorno el saldo y lo mando al cliente
                }
            }
        }catch (SQLException throwables){
            showErrorMessage(throwables.getMessage());
        }
        return saldo;
    }

    //Me recibe el usuario y la contrasena del login y lo busca en la base de datos (tiene que concordar su nombre de usuario y contrasena)
    //si existe entonces retorna true, de lo contrario retorna false
    private boolean verificacionExistenciaLogin(String password, String user) {
        ResultSet rs = null;
        try {
            sqlExecutor = new SQL_Executor();
            //accedo a todos los clientes de la tabla CLIENTE
            rs = sqlExecutor.ejecutaQuery("SELECT * FROM CLIENTE");
            //voy recorriendo toda la tabla de clientes para verficiar que exista en la base de datos segun los parametros
            while (rs.next()){
                //accede a la columna de USUARIO y de CLAVE para verificar que ambos existan
                if(rs.getString("USUARIO").equals(user) && rs.getString("CLAVE").equals(password)){
                    return true;
                }
            }
            return false;
        }catch (SQLException throwables){
            showErrorMessage(throwables.getMessage());
        }
        return false;
    }

    private String recibeNuevoPassword(String cedula, String passwordNuevo) {
        ResultSet rs = null;
        try {
            String passwordActual ="";
            sqlExecutor = new SQL_Executor();
            //se accede a los clientes de la base de datos para buscar al que necesitamos y acceder a su saldo
            rs = sqlExecutor.ejecutaQuery("SELECT * FROM CLIENTE");
            while (rs.next()){
                //accede a la columna de USUARIO y de CLAVE para verificar que ambos existan
                if(rs.getString("CEDULA").equals(cedula)){
                    //passwordActual = rs.getString("CLAVE");
                    String accionQuery[] = new String[3];
                    accionQuery[0] = "UPDATE CLIENTE SET CLAVE = ? WHERE CEDULA = ?;";
                    accionQuery[1] = passwordNuevo; //manda la nueva clave  'CLAVE = ?'
                    accionQuery[2] = cedula; //MANDA LA CEDULA 'CEDULA = ?'
                    sqlExecutor.prepareStatement(accionQuery);
                    return "Listo";
                    //break; //me salgo del while
                }
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return "";
    }

    public String accederAPassword(String cedula) {
        ResultSet rs = null;
        String password="";
        try {
            sqlExecutor = new SQL_Executor();
            //se accede a los clientes de la base de datos para buscar al que necesitamos y acceder a su password
            rs = sqlExecutor.ejecutaQuery("SELECT * FROM CLIENTE");
            while (rs.next()){
                //accede a la columna de CEDULA y CLAVE para verificar que ambos existan
                if(rs.getString("CEDULA").equals(cedula)){
                    password = rs.getString("CLAVE");
                    return password; //retorno el password y lo mando al cliente
                }
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return password;
    }

    public void showMessage(String s){
        JOptionPane.showMessageDialog(null,s,"Retiro",JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String s){
        JOptionPane.showMessageDialog(null,s,"Retiro - ERROR",JOptionPane.ERROR_MESSAGE);
    }



    //Cuando ya se conecta la comunicacion con el cliente se crean los flujos para enviar y recibir
    public void crearFlujos(){
        try{
            //----Se recibe informacion----//

            //para crear el input le decimos al socket que me de su input stream
            InputStream inputStream = socket.getInputStream();
            //con el inputStream se crea el lector del flujo de datos de entrada
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            // espera la información para ser capturada y mostrada
            bufferedReader = new BufferedReader(inputStreamReader);

            //----Se envia informacion----//

            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            // espera la información para escribirse y enviarse
            bufferedWriter = new BufferedWriter(outputStreamWriter);
        }
        catch (IOException ex){
            System.out.println("ENTRO 2");
            Logger.getLogger(ServidorModel.class.getName()).log(Level.SEVERE, null,ex);
        }
    }

    //lee una linea del buffer reader y retorna el mensaje
    public String recibirMensaje(){
        try {
            //lee una linea del buffer reader y retorna el mensaje
            String mensaje  =  bufferedReader.readLine();
            return mensaje;
        }catch (IOException ex){
            cerrarTodo();
           // Logger.getLogger(ServidorModel.class.getName()).log(Level.SEVERE, null,ex);
        }
        return "";
    }

    //ENVIA MENSAJES AL CLIENTE
    public void enviarMensaje(String mensaje){
        try {
            //le decimos al bufferWritter que escriba el mensaje
            bufferedWriter.write(mensaje);
            //se prepara el buffer reader para que quede vacio para el siguiente mensaje que se quiere enviar
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException ex){
            System.out.println("entro 2");
            cerrarTodo();
           // Logger.getLogger(ServidorModel.class.getName()).log(Level.SEVERE, null,ex);
        }
    }

}