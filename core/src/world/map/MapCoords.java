package world.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@ToString
public class MapCoords {
    @Getter private double southernLat;
    @Getter private double westernLon;
    @Getter private double northernLat;
    @Getter private double easternLon;
}
