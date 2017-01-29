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

    public static String buildQuery() {
        return new OverpassQuery()
                .format(OUTPUT_FORMAT)
                .timeout(TIMEOUT)
                .filterQuery()
                .node()
                .amenity("parking")
                .tagNot("access", "private")
                .boundingBox(
                        47.48047027491862, 19.039797484874725,
                        47.51331674014172, 19.07404761761427
                )
                .end()
                .output(OutputVerbosity.BODY, OutputModificator.CENTER, OutputOrder.QT, 100)
                .build()
                ;
    }
}
