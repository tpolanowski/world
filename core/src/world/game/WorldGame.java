package world.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class WorldGame extends Game {
	Skin skin;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG); // TODO change to error
        skin = new Skin(Gdx.files.internal("shade/skin/uiskin.json"));
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());
	    this.setScreen(new MainMenuScreen(this));
    }

	@Override
	public void render() {
        super.render();
	}
	
	@Override
	public void dispose() {
		skin.dispose();
	}

	@Override
	public void resize (int width, int height) {

	}
}
