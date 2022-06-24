
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RoomManager {
    DbHelper dbHelper = new DbHelper();
    
    public ArrayList<Room> getRooms() {
        Connection conn = null;
        Statement statement = null;
        ArrayList<Room> rooms = null;
        ResultSet rs;
        try {
            conn = dbHelper.getConnection();
            statement = conn.createStatement();
            rooms = new ArrayList<Room>();
            rs = statement.executeQuery("SELECT * FROM rooms");
            while (rs.next()) {
                rooms.add(new Room(rs.getInt("Id"),
                rs.getString("Renter"),
                rs.getInt("DaysToStay"),
                rs.getString("StartDate"),
                rs.getInt("MoneyToPay")));
            }
            conn.close();
        } catch(SQLException e) {
            dbHelper.showErrorMessages(e);
        }
        return rooms;
    }
    
    
}
