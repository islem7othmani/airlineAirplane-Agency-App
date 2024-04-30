//package database;
import java.sql.*;
public class MyConnection {
    public static Connection getConnection(String url,String username,String password){
        //chargement Driver
        String nomDriver="com.mysql.jdbc.Driver";
        try {
            Class.forName(nomDriver);
            System.out.println("Driver chargée ...");
        } catch (ClassNotFoundException e) {
            System.out.println("erreur Driver "+e.getMessage());
        }
        //connet a la base de donner
     /*   String url="jdbc:mysql://127.0.0.1/DB_tpjava";
        String username="root";
        String password="";*/
        Connection con=null;
        try {
            con= DriverManager.getConnection(url,username,password);
            System.out.println("connected");
        } catch (SQLException e) {
            System.out.println("erreur connection "+e.getMessage());
        }
        return con;
    }


}