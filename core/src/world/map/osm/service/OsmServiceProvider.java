package world.map.osm.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import world.map.osm.utils.OsmConsts;

import java.util.concurrent.TimeUnit;

public class OsmServiceProvider {
    private static OsmService service;

    public static OsmService get() {
        if (service == null) {
            service = createService();
        }

        return service;
    }

    private static OsmService createService() {
        final OkHttpClient okHttpClient = createCustomHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OsmConsts.OSM_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(OsmService.class);
    }

    private static OkHttpClient createCustomHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}