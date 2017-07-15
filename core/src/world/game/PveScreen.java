package world.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Stage;
import world.map.MapCoords;
import world.map.osm.*;
import world.util.LogTags;

import java.io.IOException;

public class PveScreen implements Screen {
    final WorldGame game;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private String message = "OSM data not loaded yet.";
    private TiledMap map;
    private Texture tiles;
    private TiledMapRenderer renderer;
    private OrthographicCamera camera;

    public PveScreen(WorldGame game) {
        this.game = game;
        stage = new Stage();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("shade/raw/font-label.fnt"), false);
        //message = OsmDataGatherer.gatherOsmData();



        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 10020, 10200);
        camera.update();




        map = new TiledMap();
        tiles = new Texture(Gdx.files.internal("tiles.png"));
        TextureRegion[][] splitTiles = TextureRegion.split(tiles, 32, 32);
        MapLayers layers = map.getLayers();

        for (int l = 0; l < 20; l++) {
            TiledMapTileLayer layer = new TiledMapTileLayer(150, 100, 32, 32);
            for (int x = 0; x < 150; x++) {
                for (int y = 0; y < 100; y++) {
                    int ty = (int)(Math.random() * splitTiles.length);
                    int tx = (int)(Math.random() * splitTiles[ty].length);
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(new StaticTiledMapTile(splitTiles[ty][tx]));
                    layer.setCell(x, y, cell);
                }
            }
            layers.add(layer);
        }

        renderer = new OrthogonalTiledMapRenderer(map);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
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
        batch.dispose();

    }

}
