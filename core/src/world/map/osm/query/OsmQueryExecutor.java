package world.map.osm.query;

import com.badlogic.gdx.Gdx;
import retrofit2.Response;
import world.map.osm.service.OsmServiceProvider;
import world.util.LogTags;

import java.io.IOException;

public class OsmQueryExecutor {
    public static OsmQueryResult runQuery(String query) throws IOException {
        Gdx.app.log(LogTags.OSM, "Querying OSM...");
        OsmQueryResult result = new OsmQueryResult();
        try {
            Response<OsmQueryResult> response = OsmServiceProvider.get().interpreter(query).execute();
            result = response.body();
            if (result == null) {
                Gdx.app.error(LogTags.OSM, "OSM query failed: " + response.raw());
            } else {
                Gdx.app.log(LogTags.OSM, "OSM query results: " + result.elements);
                Gdx.app.log(LogTags.OSM, "OSM query count: " + result.elements.size());
            }
        } catch (IOException ioe) {
            Gdx.app.error(LogTags.OSM, "OSM query error", ioe);
        }
        return result;
    }
}