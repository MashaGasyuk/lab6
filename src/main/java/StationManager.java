import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class StationManager {
    final String url = "jdbc:mysql://localhost:3306/lab5M";
    final String user = "root";
    final String pass = "78u78u9U!";

    private Connection connection;

    public StationManager() {
        try{
            connection = DriverManager.getConnection(url,user,pass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void close() throws SQLException {
        connection.close();
    }

    public void createTable() throws SQLException {
        Statement statement =connection.createStatement();
        statement.executeUpdate("CREATE TABLE  stations(idStation INT(15) PRIMARY KEY, nameStation VARCHAR (30));");
    }

    public void deleteTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE stations;");
    }

    public void addStation(Station station) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO stations VALUES(?,?);");
        statement.setInt(1,station.getId());
        statement.setString(2,station.getName());
        statement.execute();
        System.out.println("Added 1 station");
    }

    public void removeStation(Station station) throws  SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM stations WHERE idStation =?;");
        statement.setInt(1,station.getId());
        statement.executeUpdate();
        System.out.println("1 station removed");
    }

    public void clearTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM stations WHERE TRUE;");
        System.out.println("Table \"stations\" cleared");
    }

    public void updateName(int id, String newName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE stations SET nameStation = ? WHERE idStation = ?;");
        statement.setString(1,newName);
        statement.setInt(2,id);
        statement.executeUpdate();
    }

    public Collection<Station> selectAll() throws SQLException {
        Collection<Station> stations = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM stations;");
        while (resultSet.next()){
            stations.add(new Station(resultSet.getInt("idStation"),resultSet.getString("nameStation")));
        }
        return stations;
    }
    public Station selectById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM stations WHERE idStation = ?;");
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            return new Station(resultSet.getInt("idStation"),resultSet.getString("nameStation"));
        }
        return null;
    }
}
