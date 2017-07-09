package world.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lombok.SneakyThrows;
import world.map.MapCoords;
import world.map.osm.OsmQueryBuilder;
import world.map.osm.OsmQueryExecutor;
import world.map.osm.OsmQueryParameters;
import world.map.osm.OsmQueryResult;
import world.util.LogTags;

import java.io.IOException;

public class MainMenuScreen implements Screen {
    final WorldGame game;

    @SneakyThrows
    public MainMenuScreen(WorldGame game) {
        this.game = game;

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        game.skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        game.skin.add("default", new BitmapFont());

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
//		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
//		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
//		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
//		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
//		textButtonStyle.font = skin.getFont("default");
//		skin.add("default", textButtonStyle);

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        game.stage.addActor(table);

        addButtons(game, table);

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        final TextButton button = new TextButton("Click me!", game.skin);
        table.add(button);

        // Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
        // Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
        // ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
        // revert the checked state.
        button.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Clicked! Is checked: " + button.isChecked());

                // TODO extract
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
                    OsmQueryResult result = OsmQueryExecutor.runQuery(OsmQueryBuilder.buildQueryForIndustrial(queryParams));
                } catch (IOException ioe) {
                    Gdx.app.log(LogTags.OSM, "OSM connection error");
                }


                button.setText("OSM query firedz!!");
            }
        });

        // Add an image actor. Have to set the size, else it would be the size of the drawable (which is the 1x1 texture).
//		table.add(new Image(skin.newDrawable("white", Color.RED))).size(64);


        game.batch = new SpriteBatch();
        game.img = new Texture("misty-forest.jpg");

    }

    private void addButtons(final WorldGame game, Table table) {
        final TextButton pveButton = new TextButton("PvE", game.skin);
        table.add(pveButton);
        table.row();
        final TextButton pvpButton = new TextButton("PvP", game.skin);
        table.add(pvpButton);
        table.row();
        final TextButton settingsButton = new TextButton("Settings", game.skin);
        table.add(settingsButton);
        table.row();
        final TextButton exitButton = new TextButton("Exit", game.skin);
        table.add(exitButton);
        table.row();

        pveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PvEScreen(game));
                System.out.println("screen should be pve");
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        game.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
