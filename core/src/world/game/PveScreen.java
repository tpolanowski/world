package world.game;

import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import world.game.graphics.weather.WeatherEffectLoader;
import world.map.graphics.MapGenerator;

public class PveScreen implements Screen {
    final WorldGame game;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private String message = "OSM data not loaded yet.";
    private TiledMap map;
    private TiledMapRenderer renderer;
    private OrthographicCamera camera;

    private DirectionalLight light;
    private RayHandler rayHandler;

    public PveScreen(WorldGame game) {
        this.game = game;
        stage = new Stage();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("shade/raw/font-label.fnt"), false);
        //message = OsmDataGatherer.gatherOsmData();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 1030, 1040);
        camera.translate(0, -150);
        camera.update();

        map = MapGenerator.generateMap();
        renderer = new OrthogonalTiledMapRenderer(map);


        // Create a new Box2D World, this is required.
        World world = new World(new Vector2(), true);
        RayHandler.useDiffuseLight(true);

        // Setup the new RayHandler, it will use the same camera as the main game
        rayHandler = new RayHandler(world);

//        light = new DirectionalLight(rayHandler, 100, new Color(3, 12, 33, 0.4f), -70f);

        PointLight pointLight = new PointLight(rayHandler, 100, new Color(40, 30, 6, 0.7f), 1200, 300, 500 );
//        ConeLight coneLight = new ConeLight(rayHandler, 100,new Color(20, 12, 10, 0.3f), 1200f, 300, 500, -90f, 180f );
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
//        WeatherEffectLoader.drawFog(batch, delta);
        WeatherEffectLoader.drawRain(batch, delta);
//        WeatherEffectLoader.drawSnow(batch, delta);
//        WeatherEffectLoader.drawSun(batch, delta);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        font.draw(batch, message, 100, 100);
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
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
        rayHandler.dispose();
        light.dispose();

    }

}
