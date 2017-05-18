package world.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.SneakyThrows;
import world.map.MapCoords;
import world.map.osm.OsmQueryBuilder;
import world.map.osm.OsmQueryExecutor;
import world.map.osm.OsmQueryParameters;
import world.map.osm.OsmQueryResult;

public class World extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
    @SneakyThrows
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("misty-forest.jpg");
        OsmQueryParameters queryParams = OsmQueryParameters.builder().
				mapCoords(
				    MapCoords.builder()
							.easternLon(19.07404761761427)
							.northernLat(47.51331674014172)
							.southernLat(47.48047027491862)
							.westernLon(19.039797484874725)
							.build()
				)
                .build();
        OsmQueryResult result = OsmQueryExecutor.runQuery(OsmQueryBuilder.buildQuery(queryParams));
        if (result == null) return; // TODO handle failed query
    }

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0.4f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}
}
