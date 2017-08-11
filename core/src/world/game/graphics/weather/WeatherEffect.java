package world.game.graphics.weather;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import world.game.graphics.SpecialEffect;
import world.game.graphics.utils.SpecialEffectPool;

public class WeatherEffect extends SpecialEffect {
    SpecialEffectPool particleEffectPool;
    WeatherEffectType type;

    public WeatherEffect(String effectFilename, WeatherEffectType type) {
        super(effectFilename);
        this.type = type;
        particleEffect.start();
        particleEffect.setPosition(111, 111);
        particleEffectPool = new SpecialEffectPool(particleEffect, 1, 2);
    }

    public void draw(SpriteBatch batch, float delta) {
        drawAt(batch, delta, 100, 350);
    }

    private void drawAt(SpriteBatch batch, float delta, int x, int y) {
        particleEffect.start();
        particleEffect.setPosition(x, y);
        particleEffect.draw(batch, delta);
    }
}
