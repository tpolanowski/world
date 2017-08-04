package world.map.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import world.game.Settings;

import java.util.Random;

public class MapGenerator {
    private static Texture texture;
    private static TextureRegion[][] splitTiles;

    static {
        texture = new Texture(Gdx.files.internal("tiles.png"));
        splitTiles = TextureRegion.split(texture, 64, 64);
    }

    public static TiledMap generateMap() {
        TiledMap map = new TiledMap();
        MapLayers layers = map.getLayers();

        for (int l = 0; l < 1; l++) {
            TiledMapTileLayer layer = new TiledMapTileLayer(Settings.tileSize * Settings.horizontalTiles,
                    Settings.tileSize * Settings.verticalTiles, Settings.tileSize, Settings.tileSize);
            for (int x = 0; x < Settings.verticalTiles; x++) {
                for (int y = 0; y < Settings.horizontalTiles; y++) {
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(generateTile());
                    layer.setCell(x, y, cell);
                }
            }
            layers.add(layer);
        }
        return map;
    }

    private static TiledMapTile generateTile() {
        if (Settings.generateRandomTiles) {
            return createRandomTextureRegion();
        } else {
            return createPresetTextureRegion();
        }
    }

    private static TiledMapTile createRandomTextureRegion() {
        Pixmap pixmap = new Pixmap(Settings.tileSize, Settings.tileSize, Pixmap.Format.RGB888);
        pixmap.setColor(Color.CYAN);
        pixmap.drawPixel(1, 1);
        pixmap.drawPixel(Settings.tileSize - 1, 1);
        pixmap.drawPixel(1, Settings.tileSize - 1);
        pixmap.drawPixel(Settings.tileSize - 1, Settings.tileSize - 1);
        pixmap.setColor(Color.FOREST);
        pixmap.fillCircle(50, 50, 30);
        Texture texture = new Texture(pixmap);
        TextureRegion textureRegion = new TextureRegion(texture);

        return new StaticTiledMapTile(textureRegion);
    }

    private static TiledMapTile createPresetTextureRegion() {
        Random random = new Random();

        int tx = random.nextInt(18);
        if (tx > 9) tx = 0;
        int ty = 0;

        return new StaticTiledMapTile(splitTiles[ty][tx]);
    }

}
