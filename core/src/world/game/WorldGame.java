package world.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class WorldGame extends Game {
	SpriteBatch batch;
	Texture img;
	Stage stage;
	Skin skin;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG); // TODO change to error
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("shade/skin/uiskin.json"));
	    this.setScreen(new MainMenuScreen(this));
    }

	@Override
	public void render() {
        super.render();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		batch.dispose();
		img.dispose();
	}

	@Override
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}
}
