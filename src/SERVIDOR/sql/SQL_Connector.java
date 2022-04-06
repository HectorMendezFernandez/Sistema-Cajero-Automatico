package SERVIDOR.sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL_Connector {
    private static SQL_Connector instance;
    private Connection dbConn; //database conection
    private String URL = "jdbc:sqlserver://localhost:1433;database=CAJERO;"; //lo ultimo es el nombre de la base de datos

    private SQL_Connector(String user, String password){ //al ser singleton solo se instancia llamando al getIntance
        try{
            //abre el jar para utlizar su estructura externa y trabajar con ella
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            dbConn = DriverManager.getConnection(URL,user,password); //se carga con lo que tenga el driverManager del sql de microsoft y devuelve un URL con user y password
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return dbConn;
    }

    public static SQL_Connector getIntance(String user, String password) throws SQLException {
        if(instance == null){
            instance = new SQL_Connector(user, password);
        }else if(instance.getConnection().isClosed()){
            instance = new SQL_Connector(user, password);
        }
        return instance;
    }
}
