package world.map;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<List<MapSquare>> map;

    public Map(int size) {
        initMap(size);
    }

    private void initMap(int size) {
        map = new ArrayList<List<MapSquare>>(size);
        for (List<MapSquare> list : map) {
            list = new ArrayList<MapSquare>(size);
        }
    }
}
