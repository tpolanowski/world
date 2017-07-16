package world.map.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import world.game.Settings;

public class TileGenerator {
    public static TiledMapTile createTextureRegion() {
        Pixmap pixmap = new Pixmap(Settings.tileSize, Settings.tileSize, Pixmap.Format.RGB888);
        pixmap.setColor(Color.CYAN);
        pixmap.drawPixel(1,1);
        pixmap.drawPixel(Settings.tileSize-1,1);
        pixmap.drawPixel(1,Settings.tileSize-1);
        pixmap.drawPixel(Settings.tileSize-1,Settings.tileSize-1);
        pixmap.setColor(Color.FOREST);
        pixmap.fillCircle(50,50,30);
        Texture texture = new Texture(pixmap);
        TextureRegion textureRegion = new TextureRegion(texture);

        return new StaticTiledMapTile(textureRegion);
    }
}
