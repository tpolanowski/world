package world.map.osm;

import com.badlogic.gdx.Gdx;
import world.map.MapCoords;
import world.map.osm.query.OsmQueryBuilder;
import world.map.osm.query.OsmQueryExecutor;
import world.map.osm.query.OsmQueryParameters;
import world.util.LogTags;

import java.io.IOException;

public class OsmDataGatherer {

    public static String gatherOsmData() { // TODO change return from string to meaningul object
        String message;

        world.map.osm.query.OsmQueryParameters queryParams = OsmQueryParameters.builder().
                mapCoords(
                        MapCoords.builder()
                                .easternLon(19.919951)
                                .northernLat(50.064847)
                                .southernLat(50.060673)
                                .westernLon(19.912956)
                                .build()
                )
                .build();
        try {
            world.map.osm.query.OsmQueryResult resultNature = world.map.osm.query.OsmQueryExecutor.runQuery(world.map.osm.query.OsmQueryBuilder.buildQueryForNature(queryParams));
            world.map.osm.query.OsmQueryResult resultWater = world.map.osm.query.OsmQueryExecutor.runQuery(world.map.osm.query.OsmQueryBuilder.buildQueryForWater(queryParams));
            world.map.osm.query.OsmQueryResult resultIndustrial = OsmQueryExecutor.runQuery(OsmQueryBuilder.buildQueryForIndustrial(queryParams));
            message = "Nature elements: " + resultNature.elements.size() + "elements" +
                    "\n Water elements: " + resultWater.elements.size() + "elements" +
                    "\n Industrial elements: " + resultIndustrial.elements.size() + "elements";

        } catch (IOException ioe) {
            Gdx.app.log(LogTags.OSM, "OSM connection error");
            message = "OSM connection error :(";
        }

        return message;
    }
}
