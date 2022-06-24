import java.sql.*;

public class DbHelper {
    private String databaseURL = "jdbc:sqlite:rooms.db";
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseURL);
    }
    
    public void showErrorMessages(SQLException e) {
        System.out.println("Error: " + e.getMessage());
        System.out.println("Error Code: " + e.getErrorCode());
    }
    
}
