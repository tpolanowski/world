package world.game;

import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import world.game.gameplay.*;
import world.game.gameplay.Character;
import world.game.graphics.weather.WeatherEffectLoader;
import world.map.graphics.MapGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class PveScreen implements Screen, InputProcessor{
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

    Tiles tiles;
    ArrayList<KnightActor> knights;
    ArrayList<LichActor> liches;
    ArrayList<SquareActor> squares;
    Group squareGroup;
    ArrayList<Character> movableCharacters;

    ShapeRenderer shapeRenderer;
    Vector3 lastTouchDown = new Vector3();

    boolean scrollEnabled = false;
    float initZoomHeight;
    float initZoomWidth;

    Vector3 initPosition = new Vector3();

    KnightActor movedKnight;
    SquareActor chosenSquare;

    boolean moveReady = true;
    boolean startNewTurn = true;
    int turn = 0;
    int moves = 0;

    int debugClicks = 0;


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

        light = new DirectionalLight(rayHandler, 100, new Color(3, 12, 33, 1f), -70f);

        //PointLight pointLight = new PointLight(rayHandler, 100, new Color(40, 30, 6, 0.7f), 1200, 300, 500 );
//        ConeLight coneLight = new ConeLight(rayHandler, 100,new Color(20, 12, 10, 0.3f), 1200f, 300, 500, -90f, 180f );



        // Actors
        tiles = new Tiles();
        knights = new ArrayList<KnightActor>(5);
        liches = new ArrayList<LichActor>(5);
        squares = new ArrayList<SquareActor>(Settings.horizontalTiles * Settings.verticalTiles);
        squareGroup = new Group();

// A test
//        for (int i = 0; i < Settings.horizontalTiles; i++) {
//            for (int j = 0; j < Settings.verticalTiles; j++) {
//            liches.add(new LichActor("Knight3",i, j,27,47,86));
//            }
//        }

        knights.add(new KnightActor("Knight3",1, 0,27,47,86));
        knights.add(new KnightActor("Knight2",3, 1,25,35,65));
        knights.add(new KnightActor("Knight4",4,0,24,39,64));
        knights.add(new KnightActor("Knight1",6, 2,23,43,57));
        knights.add(new KnightActor("Knight5",8,1,19,40,49));

        liches.add(new LichActor("Lich3",0, 12,34,54,107));
        liches.add(new LichActor("Lich4",3,13,33,50, 87));
        liches.add(new LichActor("Lich2",4, 12,32,48, 71));
        liches.add(new LichActor("Lich1",6, 11,31,45, 65));
        liches.add(new LichActor("Lich5",8,13,30,45, 59));

//        for(int i = 0; i < 24; i++) {
//            SquareActor squareActor = new SquareActor();
//            squareActor.setVisible(false);
//            squares.add(squareActor);
//            squareGroup.addActor(squareActor);
//
//        }

        initActors();

        movableCharacters = new ArrayList<Character>(knights.size() + liches.size());
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
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        batch.begin();
        WeatherEffectLoader.drawFog(batch, delta);
        WeatherEffectLoader.drawRain(batch, delta);
        WeatherEffectLoader.drawSnow(batch, delta);
//        WeatherEffectLoader.drawSun(batch, delta);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        font.draw(batch, message, 100, 100);
        batch.end();


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

    /* Initialize actors' positions */
    public void initActors() {
        for (KnightActor knightActor : knights) {
            knightActor.addAction(moveTo(tiles.getCoord(knightActor.x,knightActor.y).getX(),tiles.getCoord(knightActor.x,knightActor.y).getY()));
            knightActor.stand();
            tiles.getCoord(knightActor.x,knightActor.y).setOccupied(true);
            stage.addActor(knightActor);
        }
        for (LichActor lichActor : liches) {
            lichActor.addAction(moveTo(tiles.getCoord(lichActor.x, lichActor.y).getX(), tiles.getCoord(lichActor.x, lichActor.y).getY()));
            lichActor.stand();
            tiles.getCoord(lichActor.x,lichActor.y).setOccupied(true);
            stage.addActor(lichActor);
        }
        stage.addActor(squareGroup);
    }

    /* Init turn, sort characters */
    public void prepareNewTurn() {
        moves = 0;
        for (KnightActor knightActor : knights) {
            movableCharacters.add(knightActor);
        }
        for (LichActor lichActor : liches) {
            movableCharacters.add(lichActor);
        }
        Collections.sort(movableCharacters, new Comparator<Character>() {
            @Override
            public int compare(Character character1, Character character2) {
                if (character1.getSpeed() > character2.getSpeed()) {
                    return -1;
                } else if (character1.getSpeed() < character2.getSpeed()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

    /* Liches moved by AI */
    public void moveLiches() {
        int moves = 0;
        Random random = new Random();
        for(final LichActor lichActor : liches) {
            lichActor.stand();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    lichActor.fly();
                }

            },moves*2.5f);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    lichActor.stand();
                }

            },moves*2.5f+2);

            // AI
            int destX;
            int destY;
            KnightActor target = lookForTarget(lichActor, 5);
            if (target != null) {
                if(!tiles.getCoord(target.x,target.y-1).isOccupied()) {
                    destX = target.x;
                    destY = target.y-1;
                }
                else if(!tiles.getCoord(target.x,target.y+1).isOccupied()) {
                    destX = target.x;
                    destY = target.y+1;
                }
                else if(!tiles.getCoord(target.x-1,target.y).isOccupied()) {
                    destX = target.x-1;
                    destY = target.y;
                }
                else {
                    destX = target.x+1;
                    destY = target.y;
                }
            }
            else {
                int rand1 = random.nextInt(1)-1;
                destX = lichActor.x + 4;
                destY = lichActor.y + rand1;
            }

            tiles.getCoord(lichActor.x,lichActor.y).setOccupied(false);
            tiles.getCoord(destX,destY).setOccupied(true);
            lichActor.addAction(sequence(
                    delay(moves * 2.5f)
                    , moveTo(tiles.getCoord(destX, destY).getX(), tiles.getCoord(destX, destY).getY(), 2)
            ));
            lichActor.x = destX;
            lichActor.y = destY;
            moves++;

            // Attack if possible
            if(target != null) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        lichActor.attack();
                    }

                },moves*2.5f+2);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        lichActor.stand();
                    }

                },moves*2.5f+5);
                int damage = lichActor.strength + random.nextInt(20);
                target.setHealth(target.getHealth() - damage);
                System.out.println(lichActor.getName() + " deals " + damage + " to " + target.getName());
                checkForDeadCharacters();
            }
        }
    }


    /* Find nearest target in the given range */
    public KnightActor lookForTarget(LichActor lichActor, int range) {
        int x = lichActor.x;
        int y = lichActor.y;
        for(KnightActor knightActor : knights) {
            if (Math.abs(x-knightActor.x) < range && Math.abs(y-knightActor.y) < range ) {
                return knightActor;
            }
        }
        return null;
    }

    /* Find nearest target in the given range */
    public LichActor lookForTarget(KnightActor knightActor, int range) {
        int x = knightActor.x;
        int y = knightActor.y;
        for(LichActor lichActor : liches) {
            if (Math.abs(x-lichActor.x) < range && Math.abs(y-lichActor.y) < range ) {
                return lichActor;
            }
        }
        return null;
    }

    public void checkForDeadCharacters() {
        for (LichActor lichActor : liches) {
            lichActor.checkIfAlive();
            if (!lichActor.alive) {
                System.out.println("Found dead character: " + lichActor.getName());
                lichActor.setVisible(false);
            }
        }
        for (KnightActor knightActor : knights) {
            knightActor.checkIfAlive();
            if (!knightActor.alive) {
                System.out.println("Found dead character: " + knightActor.getName());
                knightActor.setVisible(false);
            }
        }
    }

    /* Turn engine main function */
    public void turn() {
        if(startNewTurn) {
            startNewTurn = false;
            moves = 0;
            prepareNewTurn();
            moveLiches();
        }
        else if(moveReady){
            moveReady = false;
            movedKnight = knights.get(moves);
            System.out.println("Moving "+ movedKnight.getName() + ", moves=" + moves);
            displayRange(movedKnight);
        }
    }

    /* Knight moved by player */
    private void moveKnight() {

        movedKnight.stand();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                movedKnight.walk();
            }

        },2.5f);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                movedKnight.stand();
                moveReady = true;
                if(moves < 4) {
                    moves++;
                }
                else {
                    turn++;
                    startNewTurn = true;
                }
            }

        },2.5f+2);

        int destX = chosenSquare.getxNo();
        int destY = chosenSquare.getyNo();
        tiles.getCoord(movedKnight.x,movedKnight.y).setOccupied(false);
        tiles.getCoord(destX,destY).setOccupied(true);
        movedKnight.addAction(sequence(
                delay(2.5f)
                ,moveTo(tiles.getCoord(destX, destY).getX(), tiles.getCoord(destX, destY).getY(), 2)
        ));
        movedKnight.x = destX;
        movedKnight.y = destY;

        LichActor target = lookForTarget(movedKnight, 2);
        Random random = new Random();

        // Attack if possible
        if(target != null) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    movedKnight.attack();
                }

            },moves*2.5f+2);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    movedKnight.stand();
                }

            },moves*2.5f+5);
            int damage = movedKnight.strength + random.nextInt(20);
            target.setHealth(target.getHealth() - damage);
            System.out.println(movedKnight.getName() + " deals " + damage + " to " + target.getName());
            checkForDeadCharacters();
        }
    }

    // displays squares for move decision, left to right, top to down
    private void displayRange (KnightActor knightActor) {
        int x = tiles.getCoord(knightActor.x,knightActor.y).getNoX();
        int y = tiles.getCoord(knightActor.x,knightActor.y).getNoY();

        for(int i = 0; i < squares.size(); i++) {
            squares.get(i).setName("square" + i);
            switch (i) {
                case 0:
                    displaySquare(squares.get(i), x-3, y);
                    break;
                case 1:
                    displaySquare(squares.get(i), x-2, y+1);
                    break;
                case 2:
                    displaySquare(squares.get(i), x-2, y);
                    break;
                case 3:
                    displaySquare(squares.get(i), x-2, y-1);
                    break;
                case 4:
                    displaySquare(squares.get(i), x-1, y+2);
                    break;
                case 5:
                    displaySquare(squares.get(i), x-1, y+1);
                    break;
                case 6:
                    displaySquare(squares.get(i), x-1, y);
                    break;
                case 7:
                    displaySquare(squares.get(i), x-1, y-1);
                    break;
                case 8:
                    displaySquare(squares.get(i), x-1, y-2);
                    break;
                case 9:
                    displaySquare(squares.get(i), x, y+3);
                    break;
                case 10:
                    displaySquare(squares.get(i), x, y+2);
                    break;
                case 11:
                    displaySquare(squares.get(i), x, y+1);
                    break;
                case 12:
                    displaySquare(squares.get(i), x, y-1);
                    break;
                case 13:
                    displaySquare(squares.get(i), x, y-2);
                    break;
                case 14:
                    displaySquare(squares.get(i), x, y-3);
                    break;
                case 15:
                    displaySquare(squares.get(i), x+1, y+2);
                    break;
                case 16:
                    displaySquare(squares.get(i), x+1, y+1);
                    break;
                case 17:
                    displaySquare(squares.get(i), x+1, y);
                    break;
                case 18:
                    displaySquare(squares.get(i), x+1, y-1);
                    break;
                case 19:
                    displaySquare(squares.get(i), x+1, y-2);
                    break;
                case 20:
                    displaySquare(squares.get(i), x+2, y+1);
                    break;
                case 21:
                    displaySquare(squares.get(i), x+2, y);
                    break;
                case 22:
                    displaySquare(squares.get(i), x+2, y-1);
                    break;
                case 23:
                    displaySquare(squares.get(i), x+3, y);
                    break;
                default:
                    break;
            }
        }

    }

    private void hideRange () {
        for (SquareActor squareActor : squares) {
            squareActor.setVisible(false);
        }
    }

    private void displaySquare (SquareActor squareActor, int x, int y) {
        if (isRangeAvailable(x, y)) {
            squareActor.setX(tiles.getCoord(x, y).getX()+15);
            squareActor.setY(tiles.getCoord(x, y).getY()+5);
            squareActor.setxNo(x);
            squareActor.setyNo(y);
            squareActor.setVisible(true);
        }
        else {
            squareActor.setVisible(false);
        }
    }

    private boolean isRangeAvailable (int x, int y) {
        return x > 0 && x < 20 && y > 0 && y < 20 && !tiles.getCoord(x, y).isOccupied();
    }
    /**
     * Called when a key was pressed
     *
     * @param keycode one of the constants in {@link com.badlogic.gdx.Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link com.badlogic.gdx.Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        // Debug mode controls
        if(scrollEnabled) {
            if (keycode == Input.Keys.LEFT)
                camera.translate(-64, 0);
            if (keycode == Input.Keys.RIGHT)
                camera.translate(64, 0);
            if (keycode == Input.Keys.UP)
                camera.translate(0, 64);
            if (keycode == Input.Keys.DOWN)
                camera.translate(0, -64);
            if (keycode == Input.Keys.A)
                camera.translate(-64, 0);
            if (keycode == Input.Keys.D)
                camera.translate(64, 0);
            if (keycode == Input.Keys.W)
                camera.translate(0, 64);
            if (keycode == Input.Keys.S)
                camera.translate(0, -64);
        }
        if(keycode == Input.Keys.NUM_3) {
            if(scrollEnabled) {
                stage.getCamera().position.set(initPosition);
                stage.getCamera().viewportWidth  = initZoomWidth;
                stage.getCamera().viewportHeight = initZoomHeight;
            }
            scrollEnabled = !scrollEnabled;
        }
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be  on iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float xMapping = 0.71f; //TODO find better mapping
        float yMapping = 0.45f;
        System.out.println("Click at: " + screenX + ", " + screenY);
        System.out.println("Mapping : " + screenX*xMapping+20 + ", " + screenY*yMapping+20);
//		for(SquareActor squareActor : squares) {
//			System.out.println(squareActor.getName() + ": " + squareActor.getX() + ", " + squareActor.getY());
//		}
        SquareActor sa = squares.get(0);
        System.out.println(sa.getName() + ": " + sa.getX() + ", " + sa.getY() + ", " + sa.getWidth());

        Actor hitActor = stage.hit(screenX*xMapping+20, screenY*xMapping+20, false);
        if(hitActor != null) {
            System.out.println("HIT "+ hitActor.getName());
            if(hitActor instanceof SquareActor) {
                chosenSquare = (SquareActor) hitActor;
                hideRange();
                moveKnight();
            }
        }

        // TODO  until mapping is fixed, after third click make a move
        debugClicks++;
        if(debugClicks > 2 && turn < 2) {
            debugClicks = 0;
            chosenSquare = squares.get(2);
            hideRange();
            moveKnight();
        }
        return false;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be  on iOS.
     *
     * @param screenX x
     * @param screenY y
     * @param pointer the pointer for the event.
     * @param button  the button   @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX x
     * @param screenY y
     * @param pointer the pointer for the event.  @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(scrollEnabled)
            moveCamera(screenX-1280/2, screenY-720/2);
        return false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX x
     * @param screenY y
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(int amount) {
        if(scrollEnabled) {
            stage.getCamera().viewportWidth = stage.getCamera().viewportWidth + 20*amount;
            stage.getCamera().viewportHeight = stage.getCamera().viewportHeight + 20*amount;
        }
        return false;
    }

    private void moveCamera(int touchX, int touchY) {
        Vector3 newPosition = getNewCameraPosition(touchX, touchY);
        stage.getCamera().translate( newPosition.sub( stage.getCamera().position ) );

        lastTouchDown.set( touchX, touchY, 0);
    }

    private Vector3 getNewCameraPosition(int x, int y) {
        Vector3 newPosition = lastTouchDown;
        newPosition.sub(x,y,0);
        newPosition.y = -newPosition.y;
        newPosition.add(stage.getCamera().position);

        return newPosition;
    }
//	private boolean cameraOutOfLimit( Vector3 position ) {
//		int x_left_limit = 1280 / 2;
//		int x_right_limit = 1000 - 1280 / 2;
//		int y_bottom_limit = 720 / 2;
//		int y_top_limit = 1000 - 720 / 2;
//
//		if( position.x < x_left_limit || position.x > x_right_limit )
//			return true;
//		else if( position.y < y_bottom_limit || position.y > y_top_limit )
//			return true;
//		else
//			return false;
//	}
}
