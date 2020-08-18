/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetfood.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import planetfood.dbutil.DBConnection;
import planetfood.pojo.Employee;

/**
 *
 * @author golu
 */
public class EmployeeDao {
    public static boolean addEmployee(Employee e) throws SQLException{
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("insert into employees values(?,?,?,?)");
        
        ps.setString(1, e.getEmpId());
        ps.setString(2, e.getEname());
        ps.setString(3, e.getEmpType());
        ps.setDouble(4, e.getSal());
        
        int x = ps.executeUpdate();
        
        if(x>0){
            return true;
        }else{
            return false;
        }
    }
    
    public static String getNewID() throws SQLException{
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        String query = "select count(*) from employees";
        ResultSet rs = st.executeQuery(query);
        
        int id = 101;
        if(rs.next()){
            id = id + rs.getInt(1);
        }
        return "E"+ id;
    }
    public static Employee searchEmpById(String empid) throws SQLException{
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from employees where empid=?");
        ps.setString(1,empid);
        ResultSet rs = ps.executeQuery();
        Employee e = new Employee();
        if(rs.next()){
            e.setEmpId(rs.getString(1));
            e.setEname(rs.getString(2));
            e.setEmpType(rs.getString(3));
            e.setSal(rs.getDouble(4));
            return e;
        }else {
            return null;
        }
        
    }
    public static boolean updateEmp(Employee e) throws SQLException{
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("Update employees set ename=?,job=?,sal=? where empid=?");
        ps.setString(1, e.getEname());
        ps.setString(2, e.getEmpType());
        ps.setDouble(3, e.getSal());
        ps.setString(4, e.getEmpId());
        
        int x = ps.executeUpdate();
        if(x>0){
            return true;
        }else{
            return false;
        }
    }
    
    public static HashMap<String,Employee> getEmployeeById() throws SQLException{
        Connection con = DBConnection.getConnection();
        String query = "Select * from employees";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        HashMap<String, Employee> empMap = new HashMap<>();
        while(rs.next()){
            Employee e = new Employee();
            e.setEmpId(rs.getString(1));
            e.setEname(rs.getString(2));
            e.setEmpType(rs.getString(3));
            e.setSal(rs.getDouble(4));
            empMap.put(rs.getString(1), e);
        }
        return empMap;
    }
    
    public static boolean deleteEmp(String empid) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("delete from employees where empid=?");
        ps.setString(1,empid);
        
        int x = ps.executeUpdate();
        if(x>0){
            return true;
        }else{
            return false;
        }
    }
}
