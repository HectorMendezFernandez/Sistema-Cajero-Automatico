package SERVIDOR.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL_Executor {
    SQL_Connector dbConector; //conector
    PreparedStatement stmt;
    ResultSet rs; //result set

    public SQL_Executor(){
        try {
            dbConector = SQL_Connector.getIntance("sa","password"); //me trae la instancia
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public void prepareStatement(String[] parametros){
        try {
            stmt = dbConector.getConnection().prepareStatement(parametros[0]); //le digo que se contruya y que me traiga la coneccion y prepare el statement
            for(int i = 1; i<parametros.length; i++){
                stmt.setString(i,parametros[i]);
            }
            stmt.executeUpdate(); //INSERT, DELETE, UPDATE
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet ejecutaQuery(String sql){
        try {
            stmt = dbConector.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery(); //SELECT
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return rs;
    }
}
