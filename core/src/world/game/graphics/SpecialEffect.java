package world.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class SpecialEffect {
    private static final String PARTICLE_PATH = "particle";
    protected ParticleEffect particleEffect;

    public SpecialEffect(String effectFilename) {
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal(PARTICLE_PATH + "/" + effectFilename), Gdx.files.internal(PARTICLE_PATH));
    }

    public void start() {
        particleEffect.start();
    }

    public abstract void draw(SpriteBatch batch, float delta);

}
