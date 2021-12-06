import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class RouteManager {
    final String url = "jdbc:mysql://localhost:3306/lab5M";
    final String user = "root";
    final String pass = "78u78u9U!";

    private Connection connection;

    public RouteManager() {
        try{
            connection = DriverManager.getConnection(url,user,pass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE  routes(idRoute INT(15) PRIMARY KEY, nameRoute VARCHAR (30));");
        statement.executeUpdate("CREATE TABLE routesPath(idRoute INT(15) NOT NULL,idStation INT(15) NOT NULL);");
    }
    public void deleteTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE routes;");
        statement.executeUpdate("DROP TABLE routesPath;");
    }
    public void addRoute(Route route) throws SQLException {
        PreparedStatement routes = connection.prepareStatement("INSERT INTO routes VALUES(?,?);");

        routes.setInt(1,route.getId());
        routes.setString(2,route.getName());
        routes.executeUpdate();
        for (var s: route.getStations()) {
            addRouteStation(route.getId(),s);
        }
    }
    public void removeRoute(Route route) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM routes WHERE idRoute = ?;");
        statement.setInt(1,route.getId());
        statement.executeUpdate();
        for(var s:route.getStations()){
            removeRouteStation(route.getId(), s.getId());
        }
    }
    public void addRouteStation(int idRoute,Station station) throws SQLException {
        PreparedStatement routespath = connection.prepareStatement("INSERT INTO routespath VALUES(?,?);");
        routespath.setInt(1,idRoute);
        routespath.setInt(2,station.getId());
        routespath.executeUpdate();
    }
    public void removeRouteStation(int idStation,int idRoute) throws SQLException{
        PreparedStatement statement =
                connection.prepareStatement("DELETE FROM routespath WHERE idRoute = ? AND idStation = ?;");
        statement.setInt(1,idRoute);
        statement.setInt(2,idStation);
        statement.executeUpdate();
    }
    public void updateRouteName(int idRoute,String newName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE routes SET nameRoute = ? WHERE idRoute = ?;");
        statement.setString(1,newName);
        statement.setInt(2,idRoute);
        statement.executeUpdate();
    }
    public Collection<Route> selectAll() throws SQLException {
        Collection<Route> routes = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet routeSet = statement.executeQuery("SELECT * FROM routes;");
        while (routeSet.next()){
            ArrayList<Station> stations = new ArrayList<>();
            StationManager manager = new StationManager();

            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM routespath WHERE idRoute = ?;");
            preparedStatement.setInt(1,routeSet.getInt("idRoute"));
            ResultSet stationSet = preparedStatement.executeQuery();
            while (stationSet.next()){
                stations.add(manager.selectById(stationSet.getInt("idStation")));
            }

            routes.add(
                    new Route(
                            routeSet.getInt("idRoute"),
                            routeSet.getString("nameRoute"),
                            stations));
        }
        return routes;
    }

    public void sortByCode(Collection<Route> routes){
        routes.stream().sorted(new RouteCodeComparator());
    }
    public void sortByStationCount(Collection<Route> routes){
        routes.stream().sorted(new RouteSizeStationsComparator());
    }
    public void sortByCodeAndCount(Collection<Route> routes){
        routes.stream().sorted(new RouteCodeComparator()).sorted(new RouteSizeStationsComparator());
    }



}
