package world.game.gameplay;

import world.game.Settings;

public class Tiles {
    private Coord[][] coords;

    public Tiles () {
        coords = new Coord[Settings.horizontalTiles][Settings.verticalTiles];

        int initX = 0;
        int initY = Settings.tileSize*2+10;
        int fineTune = Settings.tileSize/4-2;

        for (int i = 0; i < Settings.horizontalTiles; i++) {

            float baseX = initX + i*Settings.tileSize + fineTune-4;
            float baseY = initY + fineTune;
            for (int j = 0; j < Settings.verticalTiles; j++) {
                if (j != 0) {
                    coords[i][j] = new Coord(baseX, baseY +j*Settings.tileSize , i, j);
                }
                else {
                    coords[i][j] = new Coord(baseX, baseY, i, j);
                }
            }
        }

        for(Coord[] coordOuter : coords) {
            for(Coord coordInner : coordOuter) {
                System.out.println(coordInner);
            }
        }
    }

    public Coord getCoord(int x, int y) {
        return coords[x][y];
    }

    public Coord[][] getCoords() {
        return coords;
    }
}
