package world.map.osm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OsmService {
    @GET("/api/interpreter")
    Call<OsmQueryResult> interpreter(@Query("data") String data);
}