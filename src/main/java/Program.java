import java.sql.SQLException;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        Station station = new Station(1,"a");
        Station station1 = new Station(2,"b");
        Station station2 = new Station(3,"c");
        Station station3 = new Station(4,"d");
        Station station4 = new Station(5,"e");
        Station station5 = new Station(6,"f");
        ArrayList<Station> stations = new ArrayList<>();
        stations.add(station);
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);
        stations.add(station4);
        Route route = new Route(1,"aa",stations);

        try {
            StationManager stationManager = new StationManager();
            stationManager.deleteTable();
            stationManager.createTable();
            System.out.println(stationManager.selectAll());
            //stationManager.deleteTable();

            RouteManager routeManager = new RouteManager();
            routeManager.deleteTable();
            routeManager.createTable();
            routeManager.addRoute(route);
            System.out.println(route);
            System.out.println(routeManager.selectAll());
            //routeManager.removeRouteStation(station2.getId(),route.getId());
            routeManager.updateRouteName(route.getId(),"newRouteName");
            System.out.println(routeManager.selectAll());

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
