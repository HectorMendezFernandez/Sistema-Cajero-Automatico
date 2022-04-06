package CLIENTE.modeloCliente;

import CLIENTE.vistaCliente.VistaCambioPass;

public class Function {

    public static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        }catch (NumberFormatException nf){
            return false;
        }
    }

    public static String generateRandomID(String idCliente){
        int ID = (int)Math.floor(Math.random()*8999+1);
        String IDString =  String.valueOf(ID);
        String IDTransacion = IDString+idCliente;
        return IDTransacion;
    }
    public static boolean contieneNum(String s)
    {
        boolean bandera = false;
        char c;
        for (int i = 0; i < s.length(); i++)
        {
            c = s.charAt(i);
            String passValue = String.valueOf(c);
            if (passValue.matches("[0-9]")) {
                bandera = true;
            }
        }
        return bandera;
    }

    public static boolean contieneMayus(String s)
    {
        boolean bandera = false;
        char c;
        for (int i = 0; i < s.length(); i++)
        {
            c = s.charAt(i);
            String passValue = String.valueOf(c);
            if (passValue.matches("[A-Z]")) {
                bandera = true;
            }
        }
        return bandera;
    }

    public static boolean contieneMinus(String s)
    {
        boolean bandera = false;
        char c;
        for (int i = 0; i < s.length(); i++)
        {
            c = s.charAt(i);
            String passValue = String.valueOf(c);
            if (passValue.matches("[a-z]")) {
                bandera = true;
            }
        }
        return bandera;
    }

    public static boolean verificarContrasena(String p, String pN, String pC,String password, VistaCambioPass vistaCambioPass){
        if(p.equals(""))
        {
            vistaCambioPass.showErrorMessage("No ha digitado ningun caracter. Por favor digite su contrasena actual");
            System.out.println("Entro1");
            return false;
        }
        else if(!p.equals(password))
        {
            vistaCambioPass.showErrorMessage("La clave proporcionada es diferente a la actual. Por favor digite su clave actual");
            return false;
        }
        else if(pN.equals(""))
        {
            vistaCambioPass.showErrorMessage("No ha digitado ningun caracter. Por favor digite una nueva clave");
            return false;
        }
        else if(pN.equals(password))//Tambien podria ser modeloCliente.getPassword();
        {
            vistaCambioPass.showErrorMessage("La clave proporcionada es igual a la actual. Por favor digite una nueva clave");
            return false;
        }
        else if((pN.length()<8)||(Function.contieneNum(pN)==false)||Function.contieneMayus(pN)==false||Function.contieneMinus(pN)==false)
        {
            vistaCambioPass.showErrorMessage("La clave no es segura");
            return false;
        }
        else if(!pC.equals(pN))
        {
            vistaCambioPass.showErrorMessage("La confirmacion no coincide con la clave nueva");
            return false;
        }else {
            return true;
        }
    }
}
