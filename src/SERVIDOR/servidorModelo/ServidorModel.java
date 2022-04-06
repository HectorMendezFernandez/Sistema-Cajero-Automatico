package SERVIDOR.servidorModelo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorModel {
    final int PUERTO = 1234;
    ServerSocket serverSocket;  //levanta la informacion (recibe y envia)

    public ServidorModel(){
        abrirPuerto();
        esperarCliente();
    }

    //se abre el puerto para despues esperar la comunicaion con el cliente
    public void abrirPuerto(){
        try{
            serverSocket = new ServerSocket(PUERTO);
        }
        catch (IOException ex){
            Logger.getLogger(ServidorModel.class.getName()).log(Level.SEVERE, null,ex);
        }
    }

    //espera la comunicacion con el cliente
    public void esperarCliente(){
        Socket socket;
        try {
            System.out.println("Esperando el ingreso de un cliente....");
            socket = serverSocket.accept(); //aqui recibe o espera la senal del cliente para arrancar la accion
            Handler handler = new Handler(socket) {
            }; //recibe a un nuevo cliente
            Thread hilo = new Thread(handler);
            hilo.start();
        }catch (IOException ex){
            Logger.getLogger(ServidorModel.class.getName()).log(Level.SEVERE, null,ex);
        }
    }


    public void salir(){
        System.out.println("Saliendo del servidor...");
        System.exit(0);
    }
}
