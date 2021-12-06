

import java.util.Comparator;

public class RouteSizeStationsComparator implements Comparator<Route> {
    @Override
    public int compare(Route o1, Route o2) {
        if(o1.getStations().size()> o2.getStations().size()){
            return 1;
        }else if(o1.getStations().size()< o2.getStations().size()){
            return  -1;
        }
        else{
            return 0;
        }
    }
}

