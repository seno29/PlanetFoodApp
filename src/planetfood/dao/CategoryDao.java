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
import java.util.ArrayList;
import java.util.HashMap;
import planetfood.dbutil.DBConnection;
import planetfood.pojo.Category;

/**
 *
 * @author golu
 */
public class CategoryDao {
    public static HashMap<String,String> getAllCategoryId()throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st= conn.createStatement();
        ResultSet rs=st.executeQuery("Select cat_name,cat_id from categories");
        HashMap<String,String> categories=new HashMap<>();
        while(rs.next())
        {
            String catName=rs.getString(1);
            String catId=rs.getString(2);
            categories.put(catName,catId);
        }
        return categories;
    }
    public static String getNewID()throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Select count(*) from categories");
        int id=101;
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
            id=id+rs.getInt(1);
        }
        return "C"+id;
    }
    public static boolean addCategory(Category c) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into categories values(?,?)");
        ps.setString(1, c.getCat_id());
        ps.setString(2, c.getCat_name());
        int x=ps.executeUpdate();
        if(x>0){
            return true;
        }
        else{
            return false;
        }
    }
    public static ArrayList<Category> getAllData() throws SQLException{
        Connection conn = DBConnection.getConnection();
        String query = "select * from Categories";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        ArrayList<Category> catList = new ArrayList<>(); 
        
        while(rs.next()){
            Category c = new Category();
            c.setCat_id(rs.getString("cat_id"));
            c.setCat_name(rs.getString("cat_name"));
            catList.add(c);
        }
        return catList;
    }
    public static boolean updateCategory(Category c) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Update categories set cat_name=? where cat_id=?");
        ps.setString(1, c.getCat_name());
        ps.setString(2, c.getCat_id());
        
        int x = ps.executeUpdate();
        if(x>0){
            return true;
        }else{
            return false;
        }
    }
}
