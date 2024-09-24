package Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    static String url = "jdbc:postgresql://localhost:5432/DataSyncPro";
    static String user = "postgres";
    static String pass = "vishnu";
    public static Connection getConnection() {

        Connection con;
        {
            try {
                Class.forName("org.postgresql.Driver");
                con = DriverManager.getConnection(url,user,pass);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return con;
    }
}
