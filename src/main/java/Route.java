import java.util.ArrayList;
import java.util.Objects;

public class Route implements Comparable<Route> {
    private int id;
    private String name;
    private ArrayList<Station> stations = new ArrayList<>();

    public Route(int id, String name, ArrayList<Station> stations) {
        this.id = id;
        this.name = name;
        this.stations = stations;
    }
    public void addStation(Station station){
        stations.add(station);
    }
    public void removeStation(Station station){
        stations.remove(station);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id && Objects.equals(name, route.name) && Objects.equals(stations, route.stations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stations);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    @Override
    public int compareTo(Route o) {
        if(id>o.id){
            return 1;
        }else if(id<o.id){
            return -1;
        }else
            return 0;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stations=" + stations +
                '}';
    }
}
