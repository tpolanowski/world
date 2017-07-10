package world.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lombok.SneakyThrows;
import world.util.LogTags;

public class MainMenuScreen implements Screen {
    private final WorldGame game;
    private Stage stage;
    private Texture imgTexture;
    private SpriteBatch batch;


    @SneakyThrows
    public MainMenuScreen(WorldGame game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        imgTexture = new Texture("misty-forest2.jpg");

        addMenu(game);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgTexture, -70, 0);
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
        imgTexture.dispose();
        batch.dispose();
    }

    private void addMenu(WorldGame game) {
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.defaults().width(150);
        table.defaults().minHeight(40);
        table.defaults().pad(10);
        stage.addActor(table);
        addButtons(game, table);
    }

    private void addButtons(final WorldGame game, Table table) {
        addPveButton(game, table);
        addPvpButton(game, table);
        addSettingsButton(game, table);
        addExitButton(game, table);
    }

    private void addPveButton(final WorldGame game, Table table) {
        final TextButton pveButton = new TextButton("PvE", game.skin, "round");
        table.add(pveButton).row();
        pveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug(LogTags.APP, "Changing screen to PvE");
                game.setScreen(new PveScreen(game));
            }
        });
    }

    private void addPvpButton(final WorldGame game, Table table) {
        final TextButton pvpButton = new TextButton("PvP", game.skin, "round");
        table.add(pvpButton).row();
        pvpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug(LogTags.APP, "Changing screen to PvP");
                game.setScreen(new PvpScreen(game));
            }
        });
    }

    private void addSettingsButton(final WorldGame game, Table table) {
        final TextButton settingsButton = new TextButton("Settings", game.skin, "round");
        table.add(settingsButton).row();
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug(LogTags.APP, "Changing screen to settings");
                game.setScreen(new SettingsScreen(game));
            }
        });
    }

    private void addExitButton(final WorldGame game, Table table) {
        final TextButton exitButton = new TextButton("Exit", game.skin, "round");
        table.add(exitButton).row();
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(LogTags.APP, "Quitting game...");
                Gdx.app.exit();
            }
        });
    }
}
