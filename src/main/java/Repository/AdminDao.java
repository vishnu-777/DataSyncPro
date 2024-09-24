package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {
    public String getAdminLoginDetails(String username){
        Connection con = Connect.getConnection();
        String query = "SELECT password from login where username = ?";
        String pass = "";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,username);
            ResultSet rs =ps.executeQuery();
            if(rs.next()){
                pass = rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pass;
    }
}
