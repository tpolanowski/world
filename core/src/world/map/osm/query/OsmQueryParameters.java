package world.map.osm.query;

import lombok.*;
import world.map.MapCoords;

@Builder
@AllArgsConstructor
@ToString
public class OsmQueryParameters {
    @Getter
    @Setter
    private MapCoords mapCoords;
    @Getter
    private static final int outputLimit = 100;
}
