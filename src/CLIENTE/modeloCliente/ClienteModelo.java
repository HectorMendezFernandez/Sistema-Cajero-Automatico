package CLIENTE.modeloCliente;

import CLIENTE.controladorCliente.ControladorCliente;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClienteModelo{ //thread

    ControladorCliente controlador;
    final int PUERTO = 1234;
    final String HOST = "localhost";
    Socket socket;   //socket para abrir los buffers
    BufferedReader bufferedReader; //lee
    BufferedWriter bufferedWriter; ///escribe para enviar (para enviar al cliente)

    public void cerrarTodo(){
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

    public void conectaConElServidor(){
        try {
            //Se crea un socket que se conecta al puerto del server y es localhost
            socket = new Socket(HOST, PUERTO);
        }catch (IOException ex){
            Logger.getLogger(ClienteModelo.class.getName()).log(Level.SEVERE, null,ex);
        }
    }

    public void setControlador(ControladorCliente controladorCliente){
        this.controlador = controladorCliente;
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
            Logger.getLogger(ClienteModelo.class.getName()).log(Level.SEVERE, null,ex);
        }
    }

    //lee una linea del buffer reader y retorna el mensaje
    public String recibirMensaje(){
        try {
            //lee una linea del buffer reader y retorna el mensaje
            String mensaje  =  bufferedReader.readLine();
            return mensaje;
        }catch (IOException ex){
            Logger.getLogger(ClienteModelo.class.getName()).log(Level.SEVERE, null,ex);
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
            cerrarTodo();
            Logger.getLogger(ClienteModelo.class.getName()).log(Level.SEVERE, null,ex);
        }
    }
}

