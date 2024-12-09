/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conection;

/**
 *
 * @author l_car
 */
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {
    
    
    Connection cn;
    Connection connection = null;
    
    
     String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
     String url="jdbc:sqlserver://KITO\\SQLEXPRESS:1433;databaseName=blogProyecto;user=LuisC;password=pollo";
    
    
     public Connection ConexionBD()
    {
        try{
            Class.forName(this.driver);
            cn= DriverManager.getConnection(url); 
            System.out.println("Conexion exitosa");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
            return cn;
    }
    

     
}
