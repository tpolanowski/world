package world.map.osm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OsmService {
    @GET(OsmConsts.OSM_SERVICE_ENDPOINT_URL)
    Call<OsmQueryResult> interpreter(@Query("data") String data);
}