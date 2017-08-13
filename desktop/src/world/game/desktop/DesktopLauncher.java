package world.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import world.game.Settings;
import world.game.WorldGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Settings.horizontalScreen;
        config.height = Settings.verticalScreen;
        new LwjglApplication(new WorldGame(), config);
    }
}
