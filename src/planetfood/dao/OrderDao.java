
package planetfood.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import planetfood.dbutil.DBConnection;
import planetfood.pojo.Order;
import planetfood.pojo.OrderDetails;

public class OrderDao {
    
    public static ArrayList<Order> getOrdersByDate(Date startDate, Date endDate) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select * from orders where ord_date between ? and ?");
        
        long ms1 = startDate.getTime();
        long ms2 = endDate.getTime();
        java.sql.Date sdate = new java.sql.Date(ms1);
        java.sql.Date edate = new java.sql.Date(ms2);
        ps.setDate(1,sdate);
        ps.setDate(2, edate);
        
        ResultSet rs = ps.executeQuery();
        ArrayList<Order> ordList = new ArrayList<>();
        while(rs.next()){
            Order obj = new Order();
            obj.setOrdId(rs.getString("ord_id"));
            java.sql.Date d=rs.getDate("ord_date");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            String dateStr = sdf.format(d);
            obj.setOrdDate(dateStr);
            obj.setGst(rs.getDouble("gst"));
            obj.setGstAmount(rs.getDouble("gst_amount"));
            obj.setDiscount(rs.getDouble("discount"));
            obj.setOrdAmount(rs.getDouble("ord_amount"));
            obj.setGrandTotal(rs.getDouble("grand_total"));
            obj.setUserId(rs.getString("userid"));
            ordList.add(obj);                   
        }
        return ordList;
    }
    
    public static String getNewId() throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps =conn.prepareStatement("Select count(*) from orders");
        int id = 101;
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            id = id+rs.getInt(1);
        }
        return "O"+id;
    }
    public static boolean addOrder(Order order,ArrayList<OrderDetails> orderList) throws SQLException,ParseException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into orders values(?,?,?,?,?,?,?,?)");
        ps.setString(1,order.getOrdId());
        String dateStr=order.getOrdDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        java.util.Date d1 = sdf.parse(dateStr);
        java.sql.Date d2 = new java.sql.Date(d1.getTime());
        ps.setDate(2, d2);
        ps.setDouble(3,order.getGst());
        ps.setDouble(4, order.getGstAmount());
        ps.setDouble(5,order.getDiscount());
        ps.setDouble(6,order.getGrandTotal());
        ps.setString(7,order.getUserId());
        ps.setDouble(8,order.getOrdAmount());
        int x = ps.executeUpdate();
        PreparedStatement ps1 = conn.prepareStatement("insert into order_details values(?,?,?,?)");
        int count =0,y;
        
        for(OrderDetails detail:orderList){
            ps1.setString(1, detail.getOrd_id());
            ps1.setString(2,detail.getProd_id());
            ps1.setDouble(3, detail.getQuantity());
            ps1.setDouble(4,detail.getCost());
            y= ps1.executeUpdate();
            count = count+y;
        }
        if(x>0 && count == orderList.size()){
            return true;
        }else{
            return false;
        }    
    }
    
    public static ArrayList<Order> getAllData() throws SQLException{
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        String query ="Select * from orders";
        
        ResultSet rs = st.executeQuery(query);
        ArrayList<Order> ordList = new ArrayList<>(); 
        while(rs.next()){
            Order o = new Order();
            java.sql.Date d = rs.getDate(2);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            String dateStr = sdf.format(d);
            
            o.setOrdId(rs.getString("ord_id"));
            o.setOrdDate(dateStr);
            o.setGst(rs.getDouble("gst"));
            o.setGstAmount(rs.getDouble("gst_amount"));
            o.setDiscount(rs.getDouble("discount"));
            o.setOrdAmount(rs.getDouble("ord_amount"));
            o.setGrandTotal(rs.getDouble("grand_total"));
            o.setUserId(rs.getString("userid"));
            ordList.add(o);
        }
        return ordList;
    }
    
}
