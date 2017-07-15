package world.map.osm.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import world.map.osm.utils.OsmConsts;

public interface OsmService {
    @GET(OsmConsts.OSM_SERVICE_ENDPOINT_URL)
    Call<world.map.osm.query.OsmQueryResult> interpreter(@Query("data") String data);
}