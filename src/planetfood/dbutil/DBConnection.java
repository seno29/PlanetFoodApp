package planetfood.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBConnection {
    private static Connection conn;
    static{
        try{
        Class.forName("oracle.jdbc.OracleDriver");
            String url = "jdbc:oracle:thin:@//dell:1521/XE";
            String username = "planetfood";
            String password = "student";
            conn = DriverManager.getConnection(url, username, password);
    }catch(Exception e){
        e.printStackTrace();
        JOptionPane.showMessageDialog(null,"DB Error in DBConnection","Error!",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static Connection getConnection(){
        return conn;
    }
    
    public static void closeConnection(){
        try{
        conn.close();
        System.out.println("Connection closed!");
        }catch(SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"DB Error in closing connection in DBConnection","Error!",JOptionPane.ERROR_MESSAGE);
        }
    }
}
