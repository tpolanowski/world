package world.map.osm;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OsmServiceProvider {
    private static OsmService service;

    public static OsmService get() {
        if (service == null) {
            service = createService();
        }

        return service;
    }

    private static OsmService createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OsmConsts.OSM_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(OsmService.class);
    }
}