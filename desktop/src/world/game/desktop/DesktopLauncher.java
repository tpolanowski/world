package world.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import world.game.WorldGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 576;
        config.height = 1040; //TODO resolution is trimmed down for test purposes, should be HD
        new LwjglApplication(new WorldGame(), config);
    }
}
