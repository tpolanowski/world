package world.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import world.map.MapCoords;
import world.map.osm.OsmQueryBuilder;
import world.map.osm.OsmQueryExecutor;
import world.map.osm.OsmQueryParameters;
import world.map.osm.OsmQueryResult;
import world.util.LogTags;

import java.io.IOException;

public class PveScreen implements Screen {
    final WorldGame game;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private String message = "Not loaded yet.";

    public PveScreen(WorldGame game) {
        this.game = game;
        stage = new Stage();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("shade/raw/font-label.fnt"), false);
        gatherOsmData();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, message, 100,100);
        batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void gatherOsmData() {
        OsmQueryParameters queryParams = OsmQueryParameters.builder().
                mapCoords(
                        MapCoords.builder()
                                .easternLon(19.919951)
                                .northernLat(50.064847)
                                .southernLat(50.060673)
                                .westernLon(19.912956)
                                .build()
                )
                .build();
        try {
            OsmQueryResult resultNature = OsmQueryExecutor.runQuery(OsmQueryBuilder.buildQueryForNature(queryParams));
            OsmQueryResult resultWater = OsmQueryExecutor.runQuery(OsmQueryBuilder.buildQueryForWater(queryParams));
            OsmQueryResult resultIndustrial = OsmQueryExecutor.runQuery(OsmQueryBuilder.buildQueryForIndustrial(queryParams));
            message = "Nature elements: " + resultNature.elements.size() + "elements" +
                    "\n Water elements: " + resultWater.elements.size() + "elements" +
                    "\n Industrial elements: " + resultIndustrial.elements.size() + "elements";

        } catch (IOException ioe) {
            Gdx.app.log(LogTags.OSM, "OSM connection error");
            message = "OSM connection error :(";
        }
    }
}
