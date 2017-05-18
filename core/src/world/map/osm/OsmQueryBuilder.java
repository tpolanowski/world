package world.map.osm;

import hu.supercluster.overpasser.library.output.OutputFormat;
import hu.supercluster.overpasser.library.output.OutputModificator;
import hu.supercluster.overpasser.library.output.OutputOrder;
import hu.supercluster.overpasser.library.output.OutputVerbosity;
import hu.supercluster.overpasser.library.query.OverpassQuery;

import static hu.supercluster.overpasser.library.output.OutputFormat.JSON;

public class OsmQueryBuilder {
    private static final int TIMEOUT = 30;
    private static final OutputFormat OUTPUT_FORMAT = JSON;

    public static String buildQuery(OsmQueryParameters params) {
        return new OverpassQuery()
                .format(OUTPUT_FORMAT)
                .timeout(TIMEOUT)
                .filterQuery()
                .node()
                .amenity("parking")
                .boundingBox(
                        params.getMapCoords().getSouthernLat(),
                        params.getMapCoords().getWesternLon(),
                        params.getMapCoords().getNorthernLat(),
                        params.getMapCoords().getEasternLon()
                )
                .end()
                .output(OutputVerbosity.BODY, OutputModificator.CENTER, OutputOrder.QT, params.getOutputLimit())
                .build();
    }
}
