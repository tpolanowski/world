package world.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.SneakyThrows;
import world.map.osm.OsmQueryBuilder;
import world.map.osm.OsmQueryExecutor;
import world.map.osm.OsmQueryResult;

public class World extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
    @SneakyThrows
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("misty-forest.jpg");
        System.out.println("i am program");
        OsmQueryResult result = OsmQueryExecutor.runQuery(OsmQueryBuilder.buildQuery());
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
