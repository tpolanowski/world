package world.game.graphics.weather;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import world.game.graphics.SpecialEffect;
import world.game.graphics.utils.SpecialEffectPool;

import java.util.Random;

public class WeatherEffect extends SpecialEffect {
    SpecialEffectPool particleEffectPool;
    WeatherEffectType type;
    ParticleEffect[] particleEffects;
    int effectCount;
    Random random = new Random();

    public WeatherEffect(String effectFilename, WeatherEffectType type) {
        super(effectFilename);
        this.type = type;
        initEffectSpecificConfig(type);
        initEffectPool(effectFilename);
    }

    public void draw(SpriteBatch batch, float delta) {
        for (ParticleEffect pe : particleEffects) {
            drawAt(pe, batch, delta, random.nextInt(576), random.nextInt(740) + 300);
        }
    }

    private void drawAt(ParticleEffect particleEffect, SpriteBatch batch, float delta, int x, int y) {
        particleEffect.start();
        particleEffect.setPosition(x, y);
        particleEffect.draw(batch, delta);
        if (particleEffect.isComplete()) particleEffect.reset();
    }

    private void initEffectSpecificConfig(WeatherEffectType type) {
        switch(type) {
            case RAIN:
                effectCount = random.nextInt(50) + 5;
                break;
            case FOG:
                effectCount = random.nextInt(10) + 2;
                break;
            case SUN:
                effectCount = random.nextInt(30) + 5;
                break;
            case SNOW:
                effectCount = random.nextInt(20) + 3;
                break;
        }
    }

    private void initEffectPool(String effectFilename) {
        particleEffects = new ParticleEffect[effectCount];
        particleEffectPool = new SpecialEffectPool(particleEffect, 1, 2);
        for (int i = 0; i< effectCount; i++) {
            particleEffects[i] = new ParticleEffect();
            particleEffects[i].load(Gdx.files.internal(PARTICLE_PATH + "/" + effectFilename), Gdx.files.internal(PARTICLE_PATH));
        }
    }
}
